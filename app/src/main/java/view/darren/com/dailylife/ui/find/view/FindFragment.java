package view.darren.com.dailylife.ui.find.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import butterknife.Bind;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.blankj.utilcode.util.ActivityUtils;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;
import com.yc.cn.ycbannerlib.BannerView;
import com.yc.cn.ycbannerlib.banner.util.SizeUtil;
import org.jsoup.Connection;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.base.adapter.BaseBannerPagerAdapter;
import view.darren.com.dailylife.base.adapter.BaseDelegateAdapter;
import view.darren.com.dailylife.base.mvp.BaseFragment;
import view.darren.com.dailylife.ui.find.contract.FindFragmentContract;
import view.darren.com.dailylife.ui.find.presenter.FindFragmentPresenter;
import view.darren.com.dailylife.ui.guide.view.activity.MainActivity;

import java.util.LinkedList;
import java.util.List;

public class FindFragment extends BaseFragment<FindFragmentPresenter> implements FindFragmentContract.View {

    private final String TAG = "FindFragment";
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;


    private MainActivity activity;
    private FindFragmentContract.Presenter presenter = new FindFragmentPresenter(this);
    private List<DelegateAdapter.Adapter> mAdapters;
    private BannerView mBanner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
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
        if(mBanner != null){
            mBanner.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mBanner != null){
            mBanner.resume();
        }
    }

    @Override
    public int getContentView() {
        return R.layout.base_recycler_view;
    }

    @Override
    public void initView() {
        mAdapters =  new LinkedList<>();
        initRecyclerView();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    private void initRecyclerView() {
        DelegateAdapter delegateAdapter = presenter.initRecyclerView(recyclerView);
        BaseDelegateAdapter bannerAdapter = presenter.initBannerAdapter();
        mAdapters.add(bannerAdapter);

        BaseDelegateAdapter menuAdapter = presenter.initGvMenu();
        mAdapters.add(menuAdapter);

        BaseDelegateAdapter marqueeAdapter = presenter.initMarqueeView();
        mAdapters.add(marqueeAdapter);

        BaseDelegateAdapter titleAdapter = presenter.initTitle("豆瓣分享");
        mAdapters.add(titleAdapter);

        BaseDelegateAdapter gridAdapter =presenter.initList3();
        mAdapters.add(gridAdapter);

        titleAdapter = presenter.initTitle("猜你喜欢");
        mAdapters.add(titleAdapter);

        gridAdapter = presenter.initList1();
        mAdapters.add(gridAdapter);

        titleAdapter = presenter.initTitle("热门新闻");
        mAdapters.add(titleAdapter);
        BaseDelegateAdapter linearAdapter =presenter.initList2();
        mAdapters.add(linearAdapter);

        titleAdapter = presenter.initTitle("为您精选");
        mAdapters.add(titleAdapter);
        BaseDelegateAdapter plugAdapter = presenter.initList4();
        mAdapters.add(plugAdapter);

        titleAdapter = presenter.initTitle("优质新闻");
        mAdapters.add(titleAdapter);
        linearAdapter = presenter.initList5();
        mAdapters.add(linearAdapter);

        delegateAdapter.setAdapters(mAdapters);
        recyclerView.requestLayout();
    }

    @Override
    public void setBanner(BannerView mBanner, List<Object> arrayList) {
        this.mBanner = mBanner;
        mBanner.setHintGravity(2);
        mBanner.setAnimationDuration(2000);
        mBanner.setPlayDelay(1000);
        mBanner.setHintPadding(0,0,0, SizeUtil.dip2px(activity,10));
        mBanner.setAdapter(new BaseBannerPagerAdapter(activity,arrayList));
    }

    @Override
    public void setOnclick(int position) {
        ToastUtil.showToast(activity,"position:"+position);
        switch (position){
            case 0:
                break;
            default:
                break;
        }
    }

    @Override
    public void setMarqueeClick(int position) {

    }

    @Override
    public void setGridClick(int position) {

    }

    @Override
    public void setGridClickThird(int position) {

    }

    @Override
    public void setGridClickFour(int position) {

    }

    @Override
    public void setNewsList2Click(int position, String url) {

    }

    @Override
    public void setNewsList5Click(int position, String url) {

    }


}
