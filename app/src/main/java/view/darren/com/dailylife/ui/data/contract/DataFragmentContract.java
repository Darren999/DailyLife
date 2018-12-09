package view.darren.com.dailylife.ui.data.contract;

import view.darren.com.dailylife.base.mvp.BasePresenter;
import view.darren.com.dailylife.base.mvp.BaseView;
import view.darren.com.dailylife.model.bean.ImageIconBean;
import view.darren.com.dailylife.ui.guide.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public interface DataFragmentContract {
    interface View extends BaseView{
        void setGridView(String[] toolName, ArrayList<Integer> logoList);
        void setRecycleView(ArrayList<Integer> list);
    }

    interface Presenter extends BasePresenter{
        List<ImageIconBean> getVpData();
        void initGridViewData();
        void initRecycleViewData();
        void bindActivity(MainActivity activity);
    }
}
