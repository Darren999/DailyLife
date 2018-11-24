package view.darren.com.dailylife.base.app;

import android.app.Application;
import android.content.Context;

import cn.ycbjie.ycthreadpoollib.PoolThread;
import com.blankj.utilcode.util.Utils;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import view.darren.com.dailylife.comment.Constant;

import java.io.File;

public class BaseApplication extends Application{
    private static BaseApplication instance;
    private PoolThread executor;
    private Realm realm;

    public static synchronized BaseApplication getInstance() {
        if(null == instance){
            instance = new BaseApplication();
        }
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this);
        initRealm();
        initThreadPool();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if(realm != null){
            realm.close();;
            realm = null;
        }

        if(executor != null){
            executor.close();
            executor = null;
        }
    }

    private void initRealm() {
        File file;
        file = new File(Constant.ExternalStorageDirectory,Constant.DATABASE_FILE_PATH_FOLDER);
        file.mkdirs();


        Realm.init(instance);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .name(Constant.REALM_NAME)
                .schemaVersion(Constant.REALM_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfiguration);
    }

    public Realm getRealmHelper() {
        return realm;
    }

    private void initThreadPool() {
        executor = PoolThread.ThreadBuilder
                .createFixed(6)
                .setPriority(Thread.MAX_PRIORITY)
                .build();
    }

    public PoolThread getExecutor() {
        return executor;
    }


}
