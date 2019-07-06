package view.darren.com.dailylife.ui.guide.contract;

import android.app.Activity;

import com.ycbjie.library.base.mvp.BasePresenter;
import com.ycbjie.library.base.mvp.BaseView;

import java.util.List;

import view.darren.com.dailylife.model.bean.SelectPoint;

public interface SelectFollowContract {

    interface View extends BaseView{
        void refreshData(List<SelectPoint> list);
        void toMainActivity();
    }

    interface Presenter extends BasePresenter{
        void addData(Activity activity);
    }
}
