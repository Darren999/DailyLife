package view.darren.com.dailylife.ui.main.presenter;

import view.darren.com.dailylife.ui.main.contract.MainContract;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    MainPresenter(MainContract.View androidView){
        this.mView = androidView;
    }
    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
