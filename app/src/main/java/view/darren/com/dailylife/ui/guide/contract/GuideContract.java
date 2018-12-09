package view.darren.com.dailylife.ui.guide.contract;

import view.darren.com.dailylife.base.mvp.BasePresenter;
import view.darren.com.dailylife.base.mvp.BaseView;

public interface GuideContract {
    interface View extends BaseView {
        void showGuideLogo(String logo);
    }

    interface Presenter extends BasePresenter{
        void cacheHomeNewsData();
        void cacheFindNewsData();
        void cacheFindBottomNewsData();
        void cacheHomePile();
        void startGuideImage();
    }
}
