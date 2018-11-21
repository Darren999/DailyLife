package view.darren.com.dailylife.ui.guide.view.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;
import butterknife.Bind;
import com.yc.cn.ycbannerlib.BannerView;
import com.yc.cn.ycbannerlib.banner.adapter.AbsDynamicPagerAdapter;
import com.yc.cn.ycbannerlib.banner.inter.OnPageListener;
import com.yc.cn.ycbannerlib.banner.util.SizeUtil;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.base.mvp.BaseActivity;
import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;
import com.blankj.utilcode.util.SPUtils;
import view.darren.com.dailylife.comment.Constant;


public class SplashPagerActivity extends BaseActivity {

    @Bind(R.id.banner)
    BannerView banner;

    @Bind(R.id.btn_go)
    Button btnGo;

    private List<Integer> imageId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YCAppBar.translucentStatusBar(this,true);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_splash_pager;
    }

    @Override
    public void initView() {
        if(SPUtils.getInstance(Constant.SP_NAME).getBoolean(Constant.KEY_FIRST_SPLASH,true)){
            initGetImage();
            initBanner();
        }else {
            startActivity(GuideActivity.class);
            finish();
        }
    }

    private void initGetImage() {
        imageId = new ArrayList<>();
        TypedArray images = this.getResources().obtainTypedArray(R.array.splash_image);
        for(int i = 0; i < 4; i++){
            int image = images.getResourceId(i,R.drawable.bg_small_kites_min);
            imageId.add(image);
        }
        images.recycle();

    }

    private void initBanner(){
        banner.setPlayDelay(0);
        banner.setHintGravity(1);
        banner.setHintPadding(SizeUtil.dip2px(this,10),0,
                SizeUtil.dip2px(this,10),SizeUtil.dip2px(this,30));
        banner.setOnPageListener(new OnPageListener(){
            @Override
            public void onPageChange(int position) {
                if(position>=0 && position==imageId.size()-1){
                    btnGo.setVisibility(View.VISIBLE);
                }else {
                    btnGo.setVisibility(View.GONE);
                }
            }
        });

        banner.setAdapter(new ImageNormalAdapter());
    }
        @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                return true;
                default:
                    break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class ImageNormalAdapter extends AbsDynamicPagerAdapter{

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            view.setImageResource(imageId.get(position));
            return view;
        }

        @Override
        public int getCount() {
            return (imageId==null) ? 0 : imageId.size();
        }
    }
}
