package view.darren.com.dailylife.ui.find.contract;

import android.support.v7.widget.RecyclerView;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.yc.cn.ycbannerlib.BannerView;
import view.darren.com.dailylife.base.adapter.BaseDelegateAdapter;
import view.darren.com.dailylife.base.mvp.BasePresenter;
import view.darren.com.dailylife.base.mvp.BaseView;
import view.darren.com.dailylife.ui.guide.view.activity.MainActivity;

import java.util.List;

public interface FindFragmentContract  {

    interface View extends BaseView{
        void setBanner(BannerView mBanner, List<Object> arrayList);
        void setOnclick(int position);
        void setMarqueeClick(int position);
        void setGridClick(int position);
        void setGridClickThird(int position);
        void setGridClickFour(int position);
        void setNewsList2Click(int position, String url);
        void setNewsList5Click(int position , String url);
    }

    interface Presenter extends BasePresenter{
        DelegateAdapter initRecyclerView(RecyclerView recyclerView);
        BaseDelegateAdapter initBannerAdapter();
        BaseDelegateAdapter initGvMenu();
        BaseDelegateAdapter initMarqueeView();
        BaseDelegateAdapter initTitle(String title);
        BaseDelegateAdapter initList1();
        BaseDelegateAdapter initList2();
        BaseDelegateAdapter initList3();
        BaseDelegateAdapter initList4();
        BaseDelegateAdapter initList5();
        void bindActivity(MainActivity activity);
    }

}
