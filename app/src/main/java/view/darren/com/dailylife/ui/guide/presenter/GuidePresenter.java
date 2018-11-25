package view.darren.com.dailylife.ui.guide.presenter;

import android.app.Activity;
import com.blankj.utilcode.util.LogUtils;
import io.realm.Realm;
import rx.subscriptions.CompositeSubscription;
import view.darren.com.dailylife.api.ConstantImageApi;
import view.darren.com.dailylife.ui.guide.contract.GuideContract;

import java.util.Random;

public class GuidePresenter implements GuideContract.Presenter{
    private final String TAG = "GuidePresenter";
    private GuideContract.View mView;
    private CompositeSubscription mSubscriptions;
    private Realm realm;
    private Activity activity;

    public GuidePresenter(GuideContract.View view){
        mView = view;
        activity = (Activity)view;
        mSubscriptions = new CompositeSubscription();
    }
    @Override
    public void cacheHomeNewsData() {

    }

    @Override
    public void cacheFindNewsData() {

    }

    @Override
    public void cacheFindBottomNewsData() {

    }

    @Override
    public void cacheHomePileData() {

    }

    @Override
    public void startGuideImage() {
        int i  = new Random().nextInt(ConstantImageApi.SPALSH_URLS.length);
        String url = ConstantImageApi.SPALSH_URLS[i];
        mView.showGuideLogo(url);
    }

    @Override
    public void subscribe()  {
        LogUtils.e(TAG+"------"+"subscribe");
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.unsubscribe();
    }
}
