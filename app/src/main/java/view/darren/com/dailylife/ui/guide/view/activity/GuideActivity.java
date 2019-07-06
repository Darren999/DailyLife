package view.darren.com.dailylife.ui.guide.view.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.ns.yc.yccountdownviewlib.CountDownView;
import com.ycbjie.library.base.mvp.BaseActivity;
import com.ycbjie.library.utils.image.ImageUtils;
import com.squareup.picasso.Callback;

import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.ui.guide.contract.GuideContract;
import view.darren.com.dailylife.ui.guide.presenter.GuidePresenter;
import view.darren.com.dailylife.ui.main.view.MainActivity;


public class GuideActivity extends BaseActivity<GuidePresenter> implements GuideContract.View,
        View.OnClickListener {
    private static final String TAG = "GuideActivity";
    private ImageView ivSplashAd;
    private CountDownView cdvTime;

    private GuideContract.Presenter presenter = new GuidePresenter(this);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cdvTime != null && cdvTime.isShown()){
            cdvTime.stop();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public int getContentView() {
        return R.layout.base_activity_guide;
    }

    @Override
    public void initView() {
        LogUtils.d(TAG,"initView");
        StateAppBar.translucentStatusBar(this,true);
        ivSplashAd = findViewById(R.id.iv_splash_ad);
        cdvTime = findViewById(R.id.cdv_time);

        presenter.startGuideImage();
        initCountDownView();
    }

    @Override
    public void initListener() {
        cdvTime.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }


    private void initCountDownView() {
        cdvTime.setTime(5);
        cdvTime.start();
        cdvTime.setOnLoadingFinishListener(this::toMainActivity);

    }

    private void toMainActivity() {
        ActivityUtils.startActivity(MainActivity.class,
                R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        //finish();
    }

    @Override
    public void showGuideLogo(String logo) {

        Callback callback = new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                ivSplashAd.setBackgroundResource(R.drawable.bg_cloud_night);
            }
        };
        ImageUtils.loadImgByPicasso(this,logo,R.drawable.bg_cloud_night,ivSplashAd,callback);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cdv_time:
                cdvTime.stop();
                finish();
                break;
            default:
                break;
        }
    }
}
