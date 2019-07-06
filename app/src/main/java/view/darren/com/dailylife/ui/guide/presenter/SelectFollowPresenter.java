package view.darren.com.dailylife.ui.guide.presenter;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import view.darren.com.dailylife.R;
import view.darren.com.dailylife.model.bean.SelectPoint;
import view.darren.com.dailylife.ui.guide.contract.SelectFollowContract;

public class SelectFollowPresenter implements SelectFollowContract.Presenter {

    private SelectFollowContract.View mView;
    private List<SelectPoint> list = new ArrayList<>();

    public SelectFollowPresenter(SelectFollowContract.View androidView) {
        this.mView = androidView;
    }

    @Override
    public void addData(Activity activity) {
        String[] titles = activity.getResources().getStringArray(R.array.select_follow);
        for(int i=0; i<titles.length; i++){
            SelectPoint selectPoint = new SelectPoint();
            selectPoint.setName(titles[i]);

            list.add(selectPoint);
        }

        mView.refreshData(list);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
