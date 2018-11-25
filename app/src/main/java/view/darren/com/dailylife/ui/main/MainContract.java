package view.darren.com.dailylife.ui.main;

import com.blankj.utilcode.util.ActivityUtils;
import com.flyco.tablayout.listener.CustomTabEntity;
import view.darren.com.dailylife.base.mvp.BasePresenter;
import view.darren.com.dailylife.base.mvp.BaseView;

import java.util.ArrayList;

public interface MainContract {

    interface View extends BaseView {

    }

    public interface Presenter extends BasePresenter{
        ArrayList<CustomTabEntity> getTabEntity();
        void getUpdate();
        void locationPermisionsTask();
    }
}
