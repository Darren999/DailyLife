package view.darren.com.dailylife.ui.guide.presenter;

import android.app.Activity;
import android.content.Context;
import com.blankj.utilcode.util.LogUtils;
import io.realm.Realm;
import io.realm.RealmResults;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import rx.subscriptions.CompositeSubscription;
import view.darren.com.dailylife.api.ConstantImageApi;
import view.darren.com.dailylife.base.app.BaseApplication;
import view.darren.com.dailylife.comment.Constant;
import view.darren.com.dailylife.db.cache.CacheFindBottomNews;
import view.darren.com.dailylife.db.cache.CacheFindNews;
import view.darren.com.dailylife.db.cache.CacheHomeNews;
import view.darren.com.dailylife.db.cache.CacheHomePile;
import view.darren.com.dailylife.model.bean.HomeBlogEntity;
import view.darren.com.dailylife.model.bean.ItemEntity;
import view.darren.com.dailylife.ui.guide.contract.GuideContract;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GuidePresenter implements GuideContract.Presenter {
    private final String TAG = "GuidePresenter";
    private GuideContract.View mView;
    private CompositeSubscription mSubscriptions;
    private Realm realm;
    private Activity activity;

    public GuidePresenter(GuideContract.View view) {
        mView = view;
        activity = (Activity) view;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void cacheHomeNewsData() {
        List<HomeBlogEntity> blog = new ArrayList<>();

        try {
            InputStream in = activity.getAssets().open("ycBlog.config");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            String jsonStr = new String(buffer,"UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            if(null != jsonArray){
                LogUtils.d(TAG,"len:"+jsonArray.length());
//                for(int j=0; j<3; j++)
                {
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject itemJson = jsonArray.getJSONObject(i);
                        HomeBlogEntity blogEntity = new HomeBlogEntity(itemJson);
                        blog.add(blogEntity);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            cacheHomeNews(blog);
        }
    }

    @Override
    public void cacheFindNewsData() {
        List<HomeBlogEntity> findNews = new ArrayList<>();

        try {
            InputStream in = activity.getAssets().open("findNews.config");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);

            String jsonStr = new String(buffer,"UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.optJSONArray("result");
//            for(int j=0; j<3; j++)
            {
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject itemJson = jsonArray.getJSONObject(i);
                    HomeBlogEntity homeBlogEntity = new HomeBlogEntity(itemJson);
                    findNews.add(homeBlogEntity);
                    Constant.findNews.add(homeBlogEntity);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            cacheFindNews(findNews);
        }
    }

    @Override
    public void cacheFindBottomNewsData() {
        List<HomeBlogEntity> findBottomNews = new ArrayList<>();

        try {
            InputStream in = activity.getAssets().open("findBottomNews.config");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);

            String jsonStr = new String(buffer,"UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            if(jsonArray != null) {
//                for (int j = 0; j < 3; j++)
                {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject itemJson = jsonArray.getJSONObject(i);
                        HomeBlogEntity homeBlogEntity = new HomeBlogEntity(itemJson);
                        findBottomNews.add(homeBlogEntity);
                        Constant.findBottomNews.add(homeBlogEntity);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            cacheFindBottomNews(findBottomNews);
        }

    }

    @Override
    public void cacheHomePile() {
        ArrayList<ItemEntity> dataList = new ArrayList<>();

        try {
            InputStream in = activity.getAssets().open("preset.config");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);

            String jsonStr = new String(buffer,"UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            if(jsonArray != null){
//                for(int j=0; j<3; j++)
                {
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject itemJson = jsonArray.getJSONObject(i);
                        ItemEntity itemEntity = new ItemEntity(itemJson);
                        dataList.add(itemEntity);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            cacheHomePileData(dataList);
        }
    }

    @Override
    public void startGuideImage() {
        int i = new Random().nextInt(ConstantImageApi.SPALSH_URLS.length);
        String url = ConstantImageApi.SPALSH_URLS[i];
        mView.showGuideLogo(url);
    }

    @Override
    public void subscribe() {
        LogUtils.e(TAG + "------" + "subscribe");
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.unsubscribe();
    }


    private void cacheHomeNews(List<HomeBlogEntity> blog) {
        initRealm();
        RealmResults<CacheHomeNews> cacheHomeNewses;
        if(realm.where(CacheHomeNews.class).findAll() != null){
            cacheHomeNewses = realm.where(CacheHomeNews.class).findAll();
        }else{
            return ;
        }

        realm.beginTransaction();
        cacheHomeNewses.deleteAllFromRealm();
        realm.commitTransaction();

        realm.beginTransaction();
        for(int i=0; i<blog.size(); i++){
            CacheHomeNews homeNews = realm.createObject(CacheHomeNews.class);
            homeNews.setUrl(blog.get(i).getUrl());
            homeNews.setAuthor(blog.get(i).getAuthor());
            homeNews.setImageUrl(blog.get(i).getImageUrl());
            homeNews.setLogo(blog.get(i).getLogo());
            homeNews.setTitle(blog.get(i).getTitle());
            homeNews.setSummary(blog.get(i).getSummary());
            homeNews.setTime(blog.get(i).getTime());
        }
        realm.commitTransaction();
    }

    private void cacheFindNews(List<HomeBlogEntity> findNews) {
        initRealm();
        RealmResults<CacheFindNews> cacheFindNewses;
        if(realm.where(CacheFindNews.class).findAll() != null){
            cacheFindNewses = realm.where(CacheFindNews.class).findAll();
        }else{
            return ;
        }

        realm.beginTransaction();
        cacheFindNewses.deleteAllFromRealm();
        realm.commitTransaction();

        realm.beginTransaction();
        for(int i=0; i<findNews.size(); i++){
            CacheFindNews news = realm.createObject(CacheFindNews.class);
            news.setUrl(findNews.get(i).getUrl());
            news.setAuthor(findNews.get(i).getAuthor());
            news.setImageUrl(findNews.get(i).getImageUrl());
            news.setLogo(findNews.get(i).getLogo());
            news.setTitle(findNews.get(i).getTitle());
            news.setSummary(findNews.get(i).getSummary());
            news.setTime(findNews.get(i).getTime());
        }
        realm.commitTransaction();
    }

    private void cacheFindBottomNews(List<HomeBlogEntity> findBottomNews) {
        initRealm();
        RealmResults<CacheFindBottomNews> cacheFindBottomNews;
        if(realm.where(CacheFindNews.class).findAll() != null){
            cacheFindBottomNews = realm.where(CacheFindBottomNews.class).findAll();
        }else{
            return ;
        }

        realm.beginTransaction();
        cacheFindBottomNews.deleteAllFromRealm();
        realm.commitTransaction();

        realm.beginTransaction();
        for(int i=0; i<findBottomNews.size(); i++){
            CacheFindBottomNews news = realm.createObject(CacheFindBottomNews.class);
            news.setUrl(findBottomNews.get(i).getUrl());
            news.setAuthor(findBottomNews.get(i).getAuthor());
            news.setImageUrl(findBottomNews.get(i).getImageUrl());
            news.setLogo(findBottomNews.get(i).getLogo());
            news.setTitle(findBottomNews.get(i).getTitle());
            news.setSummary(findBottomNews.get(i).getSummary());
            news.setTime(findBottomNews.get(i).getTime());
        }
        realm.commitTransaction();
    }

    private void cacheHomePileData(ArrayList<ItemEntity> dataList) {
        initRealm();
        RealmResults<CacheHomePile> cacheHomePiles;
        if(realm.where(CacheHomePile.class).findAll() != null){
            cacheHomePiles = realm.where(CacheHomePile.class).findAll();
        }else{
            return;
        }

        realm.beginTransaction();
        cacheHomePiles.deleteAllFromRealm();
        realm.commitTransaction();

        realm.beginTransaction();
        for(int i=0; i<dataList.size(); i++){
            CacheHomePile cacheHomePile  = new CacheHomePile();
            cacheHomePile.setTime(dataList.get(i).getTime());
            cacheHomePile.setAddress(dataList.get(i).getAddress());
            cacheHomePile.setCountry(dataList.get(i).getCountry());
            cacheHomePile.setDescription(dataList.get(i).getDescription());
            cacheHomePile.setCoverImageUrl(dataList.get(i).getCoverImageUrl());
            cacheHomePile.setTemperature(dataList.get(i).getTemperature());
            cacheHomePile.setMapImageUrl(dataList.get(i).getMapImageUrl());
        }
        realm.commitTransaction();

    }

        private void initRealm() {
        if(realm == null){
            realm = BaseApplication.getInstance().getRealmHelper();
        }
    }

}