package view.darren.com.dailylife.ui.guide.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.*;
import butterknife.Bind;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ns.yc.ycutilslib.activityManager.AppManager;
import com.ns.yc.ycutilslib.viewPager.NoSlidingViewPager;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.base.adapter.BaseDelegateAdapter;
import view.darren.com.dailylife.base.adapter.BasePagerAdapter;
import view.darren.com.dailylife.base.mvp.BaseActivity;
import view.darren.com.dailylife.comment.factory.FragmentFactory;
import view.darren.com.dailylife.inter.listener.PerfectClickListener;
import view.darren.com.dailylife.ui.guide.presenter.MainPresenter;
import view.darren.com.dailylife.ui.main.MainContract;
import view.darren.com.dailylife.utils.image.ImageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity<MainPresenter> implements View.OnClickListener
    , EasyPermissions.PermissionCallbacks,MainContract.View{
    private final String TAG = "MainActivity";

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

    private ImageView ivAvatar;
    private LinearLayout llNavScanDownload;
    private LinearLayout llNavFeedback;
    private LinearLayout llNavAbout;
    private LinearLayout llNavLogin;
    private LinearLayout llNavVideo;
    private LinearLayout llNavHomepage;
    private View view;
    private long time;
    private  int exitCount = 0;


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
        initDrawerLayoutStatus();
        initBar();
        initTabLayout();
        initViewPager();
        initNav();
        initPermissions();
    }

    private void initNav() {
        view = navView.inflateHeaderView(R.layout.nav_header_main);
        ivAvatar = (ImageView)view.findViewById(R.id.iv_avatar);
        TextView tvUserName = (TextView)view.findViewById(R.id.tv_username);
        llNavHomepage = (LinearLayout) view.findViewById(R.id.ll_nav_homepage);
        llNavScanDownload = (LinearLayout) view.findViewById(R.id.ll_nav_scan_download);
        llNavFeedback = (LinearLayout) view.findViewById(R.id.ll_nav_feedback);
        llNavAbout = (LinearLayout) view.findViewById(R.id.ll_nav_about);
        llNavLogin = (LinearLayout) view.findViewById(R.id.ll_nav_login);
        llNavVideo = (LinearLayout) view.findViewById(R.id.ll_nav_video);
        ImageUtils.loadImgByPicassoWithCircle(this, R.drawable.ic_person_logo, ivAvatar);
        tvUserName.setText("行者");
    }

    private void initViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(FragmentFactory.getInstance().getHomeFragment());
        fragments.add(FragmentFactory.getInstance().getFindFragment());
        fragments.add(FragmentFactory.getInstance().getDataFragment());
        fragments.add(FragmentFactory.getInstance().getMeFragment());
        BasePagerAdapter adapter = new BasePagerAdapter(getSupportFragmentManager(),fragments);
        vpHome.setAdapter(adapter);

        LogUtils.d(TAG,"initViewPager");
        vpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtils.d(TAG,"onPageSelected position:"+position);
                if(position >= 0 ){
                    ctlTable.setCurrentTab(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        vpHome.setCurrentItem(0);
        vpHome.setOffscreenPageLimit(4);

    }

    private void initTabLayout() {
        ArrayList<CustomTabEntity>  mTabEntities = presenter.getTabEntity();
        ctlTable.setTabData(mTabEntities);
        tvTitle.setText("新闻首页");
        ctlTable.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpHome.setCurrentItem(position);
                switch (position){
                    case 0:
                        tvTitle.setVisibility(View.VISIBLE);
                        tvTitle.setText("新闻首页");
                        break;
                    case 1:
                        tvTitle.setVisibility(View.VISIBLE);
                        tvTitle.setText("数据中心");
                        break;
                    case 2:
                        tvTitle.setVisibility(View.VISIBLE);
                        tvTitle.setText("生活应用");
                        break;
                    case 3:
                        tvTitle.setVisibility(View.VISIBLE);
                        tvTitle.setText("更多内容");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void initListener() {
        flTitleMenu.setOnClickListener(this);
        navView.setOnClickListener(this);
        if(view != null){
            ivAvatar.setOnClickListener(listener);
            llNavHomepage.setOnClickListener(listener);
            llNavScanDownload.setOnClickListener(listener);
            llNavFeedback.setOnClickListener(listener);
            llNavLogin.setOnClickListener(listener);
            llNavVideo.setOnClickListener(listener);
            setting.setOnClickListener(listener);
            quit.setOnClickListener(listener);
        }
    }

    private PerfectClickListener listener = new PerfectClickListener() {

        @Override
        protected void onNoDoubleClick(final View v) {
            drawerLayout.closeDrawer(GravityCompat.START);
            drawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    switch (v.getId()){
                        case R.id.iv_avatar:
                            break;
                        case R.id.ll_nav_homepage:
                            break;
                        case R.id.ll_nav_scan_download:
                            break;
                        case R.id.ll_nav_feedback:
                            break;
                        case R.id.ll_nav_about:
                            break;
                        case R.id.ll_nav_login:
                            break;
                        case R.id.setting:
                            break;
                        case R.id.quit:
                            AppManager.getAppManager().appExit(false);
                            break;
                        default:
                            break;
                    }
                }
            }, 0);
        }
    };

    @Override
    public void onBackPressed() {
        if(drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtils.d(TAG,"onKeyDown:"+keyCode);
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START);
            }else{
//                if(System.currentTimeMillis() - time > 1000) {
//                    ToastUtil.showToast(this, "再按一次返回桌面");
//                    time = System.currentTimeMillis();
//                }else{
//                    moveTaskToBack(true);
//                }
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        LogUtils.d(TAG,"clear flag");
                        exitCount = 0;
                    }
                },1000);

                exitCount++;
                if(exitCount < 2) {
                    ToastUtil.showToast(this, "再按一次返回桌面");
                }else {
                    moveTaskToBack(false);

                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void initData() {
        presenter.getUpdate();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fl_title_menu:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
    }

    private void initDrawerLayoutStatus() {

    }

    private void initBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void initPermissions() {
        presenter.locationPermisionsTask();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,MainActivity.this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        LogUtils.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        LogUtils.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        if(EasyPermissions.somePermissionPermanentlyDenied(MainActivity.this,perms)){
            AppSettingsDialog.Builder builder = new AppSettingsDialog.Builder(MainActivity.this);
            builder.setTitle("允许权限")
                    .setRationale("没有该权限，此应用程序部分功能可能无法正常工作。打开应用设置界面以修改应用权限")
                    .setPositiveButton("去设置")
                    .setNegativeButton("取消")
                    .setRequestCode(124)
                    .build()
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            // 当用户从应用设置界面返回的时候，可以做一些事情，比如弹出一个土司。
            LogUtils.d(TAG, "onPermissionsDenied:" + requestCode + ":");
        }
    }
}
