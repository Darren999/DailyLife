package view.darren.com.dailylife.ui.guide.view.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.blankj.utilcode.util.ActivityUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ns.yc.ycutilslib.viewPager.NoSlidingViewPager;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;
import pub.devrel.easypermissions.EasyPermissions;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.base.mvp.BaseActivity;
import view.darren.com.dailylife.model.TabEntity;
import view.darren.com.dailylife.ui.guide.presenter.MainPresenter;
import view.darren.com.dailylife.ui.main.MainContract;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainPresenter> implements View.OnClickListener
    , EasyPermissions.PermissionCallbacks,MainContract.View{

    @Bind(R.id.fl_title_menu)
    FrameLayout flTitleMenu;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.vp_home)
    NoSlidingViewPager vpHome;
    @Bind(R.id.ctl_table)
    CommonTabLayout ctlTable;
    @Bind(R.id.ll_main)
    LinearLayout llMain;
    @Bind(R.id.setting)
    TextView setting;
    @Bind(R.id.quit)
    TextView quit;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private MainContract.Presenter presenter = new MainPresenter(this);
    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_capture:
                ToastUtil.showToast(this,"二维码扫描");
                break;
            case R.id.action_about_us:
                ActivityUtils.startActivity(AboutMeActivity.class);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initView() {
        initBar();
        initTabLayout();
    }

    private void initBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void initTabLayout() {
        ArrayList<CustomTabEntity>  mTabEntities = presenter.getTabEntity();
        ctlTable.setTabData(mTabEntities);
        tvTitle.setText("新闻首页");
        ctlTable.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
