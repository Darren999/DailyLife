package view.darren.com.dailylife.ui.guide.view.activity;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.yc.cn.ycbannerlib.banner.BannerView;
import com.yc.cn.ycbannerlib.banner.adapter.AbsDynamicPagerAdapter;
import com.ycbjie.library.base.mvp.BaseActivity;
import com.ycbjie.library.constant.Constant;

import java.util.ArrayList;
import java.util.List;

import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;
import view.darren.com.dailylife.R;


public class SplashPagerActivity extends BaseActivity {

    private static final String TAG = "SplashPagerActivity";
    private BannerView bannerView;
    private Button btn_go;
    private List<Integer> imageId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_splash_pager;
    }

    @Override
    public void initView() {
        LogUtils.d(TAG,"initView");
        StateAppBar.translucentStatusBar(this,true);
        bannerView = (BannerView)findViewById(R.id.bannerView);
        btn_go = (Button)findViewById(R.id.btn_go);

        initGetImage();
        initBanner();
    }


    private void initGetImage() {
        TypedArray images = this.getResources().obtainTypedArray(R.array.splash_image);
        LogUtils.d(TAG,"length = " + images.length());
        for(int i=0; i<images.length(); i++){
            imageId.add(images.getResourceId(i,R.drawable.bg_small_kites_min));
        }

        images.recycle();
    }

    private void initBanner() {
        bannerView.setPlayDelay(0);
        bannerView.setHintGravity(1);
        bannerView.setHintPadding(SizeUtils.dp2px(10), 0,
                SizeUtils.dp2px(10), SizeUtils.dp2px(30));
        bannerView.setAdapter(new ImageNormalAdapter());


    }


    @Override
    public void initListener() {
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(SelectFollowActivity.class);
                finish();
//                SPUtils.getInstance(Constant.SP_NAME).put(Constant.KEY_FIRST_SPLASH,false);
            }
        });

        bannerView.setOnPageListener(new BannerView.OnPageListener(){
            @Override
            public void onPageChange(int position) {
                if (position >= 0 && position == imageId.size() - 1) {
                    btn_go.setVisibility(View.VISIBLE);
                } else {
                    btn_go.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private class ImageNormalAdapter extends AbsDynamicPagerAdapter {

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            view.setImageResource(imageId.get(position));
            return view;
        }

        @Override
        public int getCount() {
            return imageId == null ? 0 : imageId.size();
        }
    }
}
