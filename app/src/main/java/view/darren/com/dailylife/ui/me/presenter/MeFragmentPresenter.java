package view.darren.com.dailylife.ui.me.presenter;

import rx.subscriptions.CompositeSubscription;
import view.darren.com.dailylife.ui.me.contract.MeFragmentContract;

public class MeFragmentPresenter implements MeFragmentContract.Presenter {
    private MeFragmentContract.View mView;
    private CompositeSubscription mSubscriptions;

    public MeFragmentPresenter(MeFragmentContract.View view){
        this.mView = view;
        mSubscriptions = new CompositeSubscription();
    }
    @Override
    public void getRedHotMessageData() {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscriptions.unsubscribe();
    }
}
