package view.darren.com.dailylife.ui.home.presenter;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.util.Pools;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import cn.ycbjie.ycthreadpoollib.PoolThread;
import com.blankj.utilcode.util.LogUtils;
import com.lzy.imagepicker.util.BitmapUtil;
import io.realm.Realm;
import io.realm.RealmResults;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import rx.subscriptions.CompositeSubscription;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.base.app.BaseApplication;
import view.darren.com.dailylife.db.cache.CacheHomeNews;
import view.darren.com.dailylife.model.bean.HomeBlogEntity;
import view.darren.com.dailylife.ui.guide.view.activity.MainActivity;
import view.darren.com.dailylife.ui.home.contract.HomeFragmentContract;
import view.darren.com.dailylife.ui.home.model.GalleryBean;
import view.darren.com.dailylife.utils.bitmap.BitmapUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class HomeFragmentPresenter implements HomeFragmentContract.Presenter{
    private final String TAG = "HomeFragmentPresenter";
    private HomeFragmentContract.View mHomeView;
    @NonNull
    private CompositeSubscription mSubscriptions;
    private Realm realm;
    private MainActivity activity;

    public HomeFragmentPresenter(HomeFragmentContract.View homeView) {
        this.mHomeView = homeView;
        mSubscriptions = new CompositeSubscription();
    }
        @Override
    public void getHomeNewsData() {
        initRealm();
        RealmResults<CacheHomeNews> cacheHomeNews;
        if(realm.where(CacheHomeNews.class).findAll() != null){
            cacheHomeNews = realm.where(CacheHomeNews.class).findAll();
        }else{
            return;
        }

        List<HomeBlogEntity> homeBlogEntities = new ArrayList<>();
        for (int i=0; i<cacheHomeNews.size(); i++){
            HomeBlogEntity homeBlogEntity = new HomeBlogEntity();
            homeBlogEntity.setAuthor(cacheHomeNews.get(i).getAuthor());
            homeBlogEntity.setTitle(cacheHomeNews.get(i).getTitle());
            homeBlogEntity.setImageUrl(cacheHomeNews.get(i).getImageUrl());
            homeBlogEntity.setTime(cacheHomeNews.get(i).getTime());
            homeBlogEntity.setLogo(cacheHomeNews.get(i).getLogo());
            homeBlogEntity.setSummary(cacheHomeNews.get(i).getSummary());

            homeBlogEntities.add(homeBlogEntity);
        }

        mHomeView.setNewsData(homeBlogEntities);

    }

    @Override
    public List<CharSequence> getMarqueeTitle() {
        List<CharSequence> list = new ArrayList<>();
        String[] title = activity.getResources().getStringArray(R.array.main_marquee_title);
        SpannableString string1 = new SpannableString(title[0]);
        string1.setSpan(new ForegroundColorSpan(Color.BLACK),2,title[0].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString string2 = new SpannableString(title[1]);
        string2.setSpan(new ForegroundColorSpan(Color.BLACK),2,title[1].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString string3 = new SpannableString(title[2]);
        string3.setSpan(new URLSpan("http://www.ximalaya.com/zhubo/71989305/"),2,title[2].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        list.add(string1);
        list.add(string2);
        list.add(string3);

        return list;
    }

    @Override
    public List<Object> getBannerData() {
        List<Object> list = new ArrayList<>();
        TypedArray typedArray = activity.getResources().obtainTypedArray(R.array.banner_image);

        for(int i=0; i<typedArray.length(); i++){
            list.add(typedArray.getResourceId(i,R.drawable.ic_investment));
        }

        typedArray.recycle();
        return list;
    }

    @Override
    public void bindActivity(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void getGalleryData() {
        final List<GalleryBean> gallery = new ArrayList<>();
        try {
            InputStream in = activity.getAssets().open("ycGallery.config");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);

            String jsonStr = new String(buffer,"UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            LogUtils.d(TAG,"getGalleryData length:"+jsonArray.length());
            if(jsonArray != null) {
//                for (int j = 0; j < 3; j++)
                {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject itemJson = jsonArray.getJSONObject(i);
                        GalleryBean galleryBean = new GalleryBean(itemJson);
                        gallery.add(galleryBean);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            PoolThread executor = BaseApplication.getInstance().getExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    ArrayList<Bitmap> bitmapList = new ArrayList<>();
                    for(GalleryBean galleryBean : gallery){
                        Bitmap bitmap = BitmapUtils.returnBitMap(galleryBean.getImageUrl());
                        bitmapList.add(bitmap);
                    }

                    mHomeView.downloadBitmapSuccess(bitmapList);
                }
            });
        }
    }

    @Override
    public void subscribe() {
        initRealm();
    }


    @Override
    public void unSubscribe() {
        mSubscriptions.unsubscribe();
        if(activity != null){
            activity = null;
        }
    }

    private void initRealm() {
        if(realm == null){
            realm = BaseApplication.getInstance().getRealmHelper();
        }
    }

}
