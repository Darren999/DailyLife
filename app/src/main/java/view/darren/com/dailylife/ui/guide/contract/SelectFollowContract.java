package view.darren.com.dailylife.ui.guide.contract;

import android.app.Activity;
import view.darren.com.dailylife.base.mvp.BasePresenter;
import view.darren.com.dailylife.base.mvp.BaseView;
import view.darren.com.dailylife.model.SelectPoint;

import java.util.List;

public interface SelectFollowContract {
    interface View extends BaseView{
        void refreshData(List<SelectPoint> list);
        void toMainActivity();
    }

    interface Presenter extends BasePresenter{
        void addData(Activity activity);
        void addSelectToRealm(Integer[] selectedIndies);
    }
}
