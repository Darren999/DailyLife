package view.darren.com.dailylife.ui.guide.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import butterknife.Bind;
import com.yc.cn.ycbannerlib.BannerView;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.base.mvp.BaseActivity;

public class SplashPagerActivity extends BaseActivity {

    @Bind(R.id.banner)
    BannerView banner;

    @Bind(R.id.btn_go)
    Button btnGo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_splash_pager;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
