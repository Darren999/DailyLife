package view.darren.com.dailylife.ui.guide.presenter;

import android.text.TextUtils;

import com.ycbjie.library.api.ConstantImageApi;
import com.ycbjie.library.base.config.AppConfig;

import java.util.Random;

import view.darren.com.dailylife.ui.guide.contract.GuideContract;

public class GuidePresenter implements GuideContract.Presenter {
    private GuideContract.View mView;

    public GuidePresenter(GuideContract.View androidView) {
        this.mView = androidView;
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
        if(AppConfig.INSTANCE.isShowGirlImg()){
            String bannerUrl = AppConfig.INSTANCE.getBannerUrl();
            if(TextUtils.isEmpty(bannerUrl)){
                int i = new Random().nextInt(ConstantImageApi.SPALSH_URLS.length);
                String splashUrl = ConstantImageApi.SPALSH_URLS[i];
                mView.showGuideLogo(splashUrl);
            } else {
                mView.showGuideLogo(bannerUrl);
            }
        } else {
            int i = new Random().nextInt(ConstantImageApi.SPALSH_URLS.length);
            String splashUrl = ConstantImageApi.SPALSH_URLS[i];
            mView.showGuideLogo(splashUrl);
        }
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
