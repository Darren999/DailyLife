package view.darren.com.dailylife.ui.home.contract;

import android.graphics.Bitmap;
import view.darren.com.dailylife.base.mvp.BasePresenter;
import view.darren.com.dailylife.base.mvp.BaseView;
import view.darren.com.dailylife.model.bean.HomeBlogEntity;
import view.darren.com.dailylife.ui.guide.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public interface HomeFragmentContract {
    interface View extends BaseView{
        void setNewsData(List<HomeBlogEntity> list);
        void downloadBitmapSuccess(ArrayList<Bitmap> bitmapList);
    }

    interface Presenter extends BasePresenter{
        void getHomeNewsData();
        List<CharSequence> getMarqueeTitle();
        List<Object> getBannerData();
        void bindActivity(MainActivity activity);
        void getGalleryData();
    }
}
