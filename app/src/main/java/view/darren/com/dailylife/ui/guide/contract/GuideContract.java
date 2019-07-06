package view.darren.com.dailylife.ui.guide.contract;

import com.ycbjie.library.base.mvp.BasePresenter;
import com.ycbjie.library.base.mvp.BaseView;

public interface GuideContract {
    interface View extends BaseView {
        void showGuideLogo(String logo);
    }

    interface Presenter extends BasePresenter {
        void cacheFindNewsData();
        void cacheFindBottomNewsData();
        void cacheHomePileData();
        void startGuideImage();
    }
}
