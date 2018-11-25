package view.darren.com.dailylife.ui.guide.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.res.TypedArray;
import android.widget.Toast;
import com.flyco.tablayout.listener.CustomTabEntity;
import pub.devrel.easypermissions.EasyPermissions;
import rx.subscriptions.CompositeSubscription;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.model.TabEntity;
import view.darren.com.dailylife.ui.main.MainContract;

import java.util.ArrayList;

public class MainPresenter implements MainContract.Presenter{

    private  MainContract.View mView;
    private Activity activity;
    private CompositeSubscription mSubscriptions;

    private static final int RC_LOCATION_CONTACTS_PERM = 124;
    private static final String[] LOCATION_AND_CONTACTS = {
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    public MainPresenter(MainContract.View view){
        mView = view;
        activity = (Activity)view;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscriptions.unsubscribe();
        if(activity != null){
            activity = null;
        }
    }

    @Override
    public ArrayList<CustomTabEntity> getTabEntity() {
        ArrayList<CustomTabEntity> mTabEntitles = new ArrayList<>();
        TypedArray mIconUnSelectIds = activity.getResources().obtainTypedArray(R.array.main_tab_un_select);
        TypedArray mIconSelectIds = activity.getResources().obtainTypedArray(R.array.main_tab_select);
        String[] mainTitles = activity.getResources().getStringArray(R.array.main_title);

        for(int i = 0; i < mainTitles.length; i++){
            int unSelectId = mIconUnSelectIds.getResourceId(i,R.drawable.tab_home_select);
            int selectId = mIconSelectIds.getResourceId(i,R.drawable.tab_home_unselect);
            TabEntity tabEntity = new TabEntity(mainTitles[i],selectId,unSelectId);
            mTabEntitles.add(tabEntity);
        }

        mIconSelectIds.recycle();
        mIconUnSelectIds.recycle();
        return mTabEntitles;
    }

    @Override
    public void getUpdate() {

    }

    @Override
    public void locationPermisionsTask() {
        startPermissionsTask();
    }

    private void startPermissionsTask() {
        if(hasPermissions()){

        }else{
            EasyPermissions.requestPermissions(activity,
                    activity.getResources().getString(R.string.easy_permissions),
                    RC_LOCATION_CONTACTS_PERM, LOCATION_AND_CONTACTS);
        }
    }

    private boolean hasPermissions() {
        return EasyPermissions.hasPermissions(activity, LOCATION_AND_CONTACTS);
    }

}
