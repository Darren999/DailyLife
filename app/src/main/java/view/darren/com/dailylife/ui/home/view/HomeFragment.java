package view.darren.com.dailylife.ui.home.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.ns.yc.yccardviewlib.CardViewLayout;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;
import com.yc.cn.ycbannerlib.BannerView;
import com.yc.cn.ycbannerlib.banner.util.SizeUtil;
import com.yc.cn.ycbannerlib.marquee.MarqueeView;
import com.yc.cn.ycbannerlib.marquee.OnItemClickListener;
import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.base.adapter.BaseBannerPagerAdapter;
import view.darren.com.dailylife.base.adapter.HomeBlogAdapter;
import view.darren.com.dailylife.base.mvp.BaseFragment;
import view.darren.com.dailylife.model.bean.HomeBlogEntity;
import view.darren.com.dailylife.ui.find.contract.FindFragmentContract;
import view.darren.com.dailylife.ui.guide.view.activity.MainActivity;
import view.darren.com.dailylife.ui.home.contract.HomeFragmentContract;
import view.darren.com.dailylife.ui.home.presenter.HomeFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment<HomeFragmentPresenter> implements HomeFragmentContract.View {

    private final String TAG = "HomeFragment";
    @Bind(R.id.recyclerView)
    YCRefreshView recyclerView;
    private HomeFragmentContract.Presenter presenter = new HomeFragmentPresenter(this);
    private ArrayList<Bitmap> bitmaps;
    private MainActivity activity;
    private BannerView banner;
    private MarqueeView marqueeView;
    private HomeBlogAdapter adapter;
    private View headerView;
    private CardViewLayout cardViewLayout;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtils.d(TAG,"what:"+msg.what);
            switch (msg.what){
                case 1:
                    if(cardViewLayout != null){
                        cardViewLayout.setVisibility(View.VISIBLE);
                    }
                    updateGalleryView();
                    break;
                case 2:
                    if(cardViewLayout != null){
                        cardViewLayout.setVisibility(View.GONE);
                    }
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public int getContentView() {
        return R.layout.base_easy_recycle;
    }

    @Override
    public void initView() {
        initRecycleView();
    }

    @Override
    public void initListener() {
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                ToastUtil.showToast(activity,"position:"+position);
                if (position > 0 && adapter.getAllData().size() > position) {

                }
            }
        });
    }

    @Override
    public void initData() {
        presenter.getHomeNewsData();
        presenter.getGalleryData();

    }

    @Override
    public void setNewsData(List<HomeBlogEntity> list) {
        if(list != null && list.size() > 0 ){
            adapter.clear();
            adapter.addAll(list);
            adapter.notifyDataSetChanged();
            recyclerView.scrollTo(0,0);
            recyclerView.setRefreshing(false);
        }

    }

    @Override
    public void downloadBitmapSuccess(ArrayList<Bitmap> bitmapList) {
        if(bitmapList!=null && bitmapList.size()>0){
            bitmaps = bitmapList;
            handler.sendEmptyMessageAtTime(1,500);
        }else{
            handler.sendEmptyMessageAtTime(2,500);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
        presenter.bindActivity(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(activity != null){
            activity = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(banner != null){
            banner.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(banner !=  null){
            banner.resume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(handler != null){
            handler.removeCallbacks(null);
            handler = null;
        }
    }

    private void initRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        RecycleViewItemLine itemLine = new RecycleViewItemLine(activity, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1), Color.parseColor("#f5f7f5"));
        recyclerView.addItemDecoration(itemLine);
        adapter = new HomeBlogAdapter(activity);
        initAddHeader();
        recyclerView.setAdapter(adapter);
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if(NetworkUtils.isConnected()){
                    if(adapter.getAllData().size() > 0) {
                        presenter.getHomeNewsData();
                    }else{
                        recyclerView.setRefreshing(false);
                    }
                }else{
                    recyclerView.setRefreshing(true);
                    ToastUtil.showToast(activity,"网络不可用");
                }
            }
        });
    }

    private void initAddHeader() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView(){

            @Override
            public View onCreateView(ViewGroup parent) {
                headerView = View.inflate(activity,R.layout.head_home_main,null);
                return headerView;
            }

            @Override
            public void onBindView(View header) {
                banner = (BannerView) header.findViewById(R.id.banner);
                TextView tvHomeFirst = (TextView) header.findViewById(R.id.tv_home_first);
                TextView tvHomeSecond = (TextView) header.findViewById(R.id.tv_home_second);
                TextView tvHomeThird = (TextView) header.findViewById(R.id.tv_home_third);
                TextView tvHomeFour = (TextView) header.findViewById(R.id.tv_home_four);
                marqueeView = (MarqueeView) header.findViewById(R.id.marqueeView);
                cardViewLayout = (CardViewLayout) header.findViewById(R.id.cardView);
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = view.getId();
                        ToastUtil.showToast(activity,"id:touched"+id);
                        switch (id){
                            case R.id.tv_home_first:
                                break;
                            case R.id.tv_home_second:
                                break;
                            case R.id.tv_home_third:
                                break;
                            case R.id.tv_home_four:
                                break;
                            default:
                                break;
                        }
                    }
                };

                tvHomeFirst.setOnClickListener(listener);
                tvHomeSecond.setOnClickListener(listener);
                tvHomeThird.setOnClickListener(listener);
                tvHomeFour.setOnClickListener(listener);
                initBanner();
                initMarqueeView();
            }
        });
    }

    private void initBanner() {
        if(headerView != null && banner != null){
            List<Object> lists = presenter.getBannerData();
            banner.setHintGravity(1);
            banner.setAnimationDuration(1000);
            banner.setPlayDelay(2000);
            banner.setHintPadding(0,0,0, SizeUtil.dip2px(activity,10));
            banner.setAdapter(new BaseBannerPagerAdapter(activity,lists));
        }

    }
    private void initMarqueeView() {
        if(marqueeView == null){
            return;
        }

        List<CharSequence> list = presenter.getMarqueeTitle();
        marqueeView.startWithList(list);
        marqueeView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                ToastUtil.showToast(activity,"postion:"+position);
                switch (position){
                    case 0:
                        break;
                    default:
                        break;
                }
            }
        });
    }



    private void updateGalleryView() {
        if(cardViewLayout==null || bitmaps==null || bitmaps.size()==0){
            LogUtils.e(TAG,"content empty!!!");
            return;
        }

        cardViewLayout.setAdapter(new CardViewLayout.Adapter() {
            class ViewHolder {
                ImageView imageView;
            }
            @Override
            public int getLayoutId() {
                return R.layout.item_card_layout;
            }

            @Override
            public void bindView(View view, int index) {
                ViewHolder viewHolder = (ViewHolder)view.getTag();
                if(viewHolder == null){
                    viewHolder = new ViewHolder();
                    viewHolder.imageView = (ImageView)view.findViewById(R.id.imageView);
                    view.setTag(viewHolder);
                }

                viewHolder.imageView.setImageBitmap(bitmaps.get(index));
            }

            @Override
            public int getItemCount() {
                return bitmaps.size();
            }

            @Override
            public void onItemClick(View view, int position) {
                super.onItemClick(view, position);
                ToastUtil.showToast(activity,"position:"+position);
            }
        });
    }

}