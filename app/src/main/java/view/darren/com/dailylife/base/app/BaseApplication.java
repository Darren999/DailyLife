package view.darren.com.dailylife.base.app;

import android.app.Application;
import android.content.Context;

import cn.ycbjie.ycthreadpoollib.PoolThread;

public class BaseApplication extends Application{
    private static BaseApplication instance;
    private PoolThread executor;

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
        initThreadPool();
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
