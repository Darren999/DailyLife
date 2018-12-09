package view.darren.com.dailylife.ui.data.presenter;

import android.content.res.TypedArray;
import rx.subscriptions.CompositeSubscription;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.base.mvp.BasePresenter;
import view.darren.com.dailylife.model.bean.ImageIconBean;
import view.darren.com.dailylife.ui.data.contract.DataFragmentContract;
import view.darren.com.dailylife.ui.guide.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class DataFragmentPresenter implements DataFragmentContract.Presenter {

    private DataFragmentContract.View mView;
    private CompositeSubscription mSubscriptions;
    private MainActivity activity;

    public DataFragmentPresenter(DataFragmentContract.View view){
        this.mView = view;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public List<ImageIconBean> getVpData() {
        List<ImageIconBean> listData = new ArrayList<>();
        TypedArray proPic = activity.getResources().obtainTypedArray(R.array.data_pro_pic);
        String[] proName = activity.getResources().getStringArray(R.array.data_pro_title);

        for(int i=0; i<proPic.length(); i++){
            int proId = proPic.getResourceId(i,R.drawable.ic_investment);
            listData.add(new ImageIconBean(proName[i],proId,i));
        }

        proPic.recycle();
        return listData;
    }

    @Override
    public void initGridViewData() {
        TypedArray typedArray = activity.getResources().obtainTypedArray(R.array.data_tool_pro_pic);
        String[] titles = activity.getResources().getStringArray(R.array.data_tool_pro_title);
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=0; i<typedArray.length(); i++)
        {
            list.add(typedArray.getResourceId(i,R.drawable.ic_investment));
        }

        typedArray.recycle();
        mView.setGridView(titles, list);
    }

    @Override
    public void initRecycleViewData() {
        TypedArray typedArray = activity.getResources().obtainTypedArray(R.array.data_narrow_Image);
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=0; i<typedArray.length(); i++){
            list.add(typedArray.getResourceId(i,R.drawable.ic_investment));
        }
        typedArray.recycle();
        mView.setRecycleView(list);
    }

    @Override
    public void bindActivity(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        if(mSubscriptions != null) {
            mSubscriptions.unsubscribe();
        }

        if(activity != null){
            activity = null;
        }
    }
}
