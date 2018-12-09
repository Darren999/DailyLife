package view.darren.com.dailylife.ui.me.contract;

import view.darren.com.dailylife.base.mvp.BasePresenter;
import view.darren.com.dailylife.base.mvp.BaseView;

public interface MeFragmentContract {
    public interface View extends BaseView{

    }

    public interface Presenter extends BasePresenter{
        void getRedHotMessageData();
    }
}
