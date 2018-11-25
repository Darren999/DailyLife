package view.darren.com.dailylife.ui.guide.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telecom.Call;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import butterknife.Bind;
import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.ns.yc.yccountdownviewlib.CountDownView;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.base.mvp.BaseActivity;
import view.darren.com.dailylife.ui.guide.contract.GuideContract;
import view.darren.com.dailylife.ui.guide.presenter.GuidePresenter;
import com.squareup.picasso.Callback;
import view.darren.com.dailylife.utils.image.ImageUtils;


public class GuideActivity extends BaseActivity<GuidePresenter> implements GuideContract.View,
        View.OnClickListener {

    private final String TAG = "GuideActivity";
    @Bind(R.id.iv_splash_ad)
    ImageView ivSplashAd;
    @Bind(R.id.cdv_time)
    CountDownView cdvTime;

    private GuideContract.Presenter presenter = new GuidePresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YCAppBar.translucentStatusBar(this,true);
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
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        LogUtils.i(TAG,"initView");
        presenter.startGuideImage();
        initCountDownView();
    }


    @Override
    public void initListener() {
        cdvTime.setOnClickListener(this);
    }

    private void initCountDownView() {
        cdvTime.setTime(5);
        cdvTime.start();
        cdvTime.setOnLoadingFinishListener(new CountDownView.OnLoadingFinishListener() {
            @Override
            public void finish() {
                toMainActivity();
            }
        });
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

    private void toMainActivity(){
        ActivityUtils.startActivity(MainActivity.class);
        finish();
    }





    @Override
    public void initData() {

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
        LogUtils.i(TAG,"logo:"+logo);
        ImageUtils.loadImgByPicasso(this,logo,R.drawable.bg_cloud_night,ivSplashAd,callback);
    }
}
