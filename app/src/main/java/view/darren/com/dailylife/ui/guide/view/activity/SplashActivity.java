package view.darren.com.dailylife.ui.guide.view.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.ns.yc.yccountdownviewlib.CountDownView;
import com.ycbjie.library.base.mvp.BaseActivity;
import com.ycbjie.library.constant.Constant;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import view.darren.com.dailylife.R;

public class SplashActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = "SplashActivity";

    private static final int RC_LOCATION_CONTACTS_PERM = 124;
    private static final String[] LOCATION_AND_CONTACTS = {
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(null);

        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentView() {
        return R.layout.base_activity_guide;
    }

    @Override
    public void initView() {
        LogUtils.d(TAG,"initView");
        CountDownView countDownView = findViewById(R.id.cdv_time);
        countDownView.setVisibility(View.GONE);
        initPermissions();

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发到EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode,
                permissions, grantResults, SplashActivity.this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        LogUtils.d("权限", "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //某些权限已被拒绝
        LogUtils.d("权限", "onPermissionsDenied:" + requestCode + ":" + perms.size());
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(SplashActivity.this, perms)) {
            AppSettingsDialog.Builder builder = new AppSettingsDialog.Builder(SplashActivity.this);
            builder.setTitle("允许权限")
                    .setRationale("没有该权限，此应用程序部分功能可能无法正常工作。打开应用设置界面以修改应用权限")
                    .setPositiveButton("去设置")
                    .setNegativeButton("取消")
                    .setRequestCode(124)
                    .build()
                    .show();
        }
    }


    private void initPermissions() {
        startPermissionsTask();
    }

    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    private void startPermissionsTask() {
        if(hasPermissions()){
            LogUtils.d(TAG,"has permissions");
            //具备权限 直接进行操作
            if (SPUtils.getInstance(Constant.SP_NAME).getBoolean(
                    Constant.KEY_FIRST_SPLASH, true)) {
                ActivityUtils.startActivity(SplashPagerActivity.class);
            } else {
                ActivityUtils.startActivity(GuideActivity.class);
            }
            finish();
        } else {
            LogUtils.d(TAG,"has not permissions");
            //权限拒绝 申请权限
            //第二个参数是被拒绝后再次申请该权限的解释
            //第三个参数是请求码
            //第四个参数是要申请的权限

            EasyPermissions.requestPermissions(this,
                    this.getResources().getString(R.string.easy_permissions),
                    RC_LOCATION_CONTACTS_PERM, LOCATION_AND_CONTACTS);
        }
    }

    private boolean hasPermissions() {
        return EasyPermissions.hasPermissions(this,LOCATION_AND_CONTACTS);
    }

}
