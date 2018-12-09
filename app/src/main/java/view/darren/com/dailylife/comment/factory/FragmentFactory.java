package view.darren.com.dailylife.comment.factory;

import android.app.Fragment;
import view.darren.com.dailylife.ui.data.view.fragment.DataFragment;
import view.darren.com.dailylife.ui.find.view.FindFragment;
import view.darren.com.dailylife.ui.home.view.HomeFragment;
import view.darren.com.dailylife.ui.me.view.MeFragment;

public class FragmentFactory {

    private static FragmentFactory mInstance;
    private HomeFragment mHomeFragment;
    private FindFragment mFindFragment;
    private DataFragment mDataFragment;
    private MeFragment mMeFragment;

    public static FragmentFactory getInstance() {
        if(mInstance == null){
            synchronized (Fragment.class){
                if(mInstance == null){
                    mInstance = new FragmentFactory();
                }
            }
        }
        return mInstance;
    }

    public FindFragment getFindFragment() {
        if(mFindFragment == null){
            synchronized (FragmentFactory.class){
                if(mFindFragment == null){
                    mFindFragment = new FindFragment();
                }
            }
        }

        return mFindFragment;
    }

    public HomeFragment getHomeFragment() {
        if(mHomeFragment == null){
            synchronized (FragmentFactory.class){
                mHomeFragment = new HomeFragment();
            }
        }
        return mHomeFragment;
    }

    public DataFragment getDataFragment() {
        if(mDataFragment == null){
            synchronized (FragmentFactory.class){
                if(mDataFragment == null){
                    mDataFragment = new DataFragment();
                }
            }
        }
        return mDataFragment;
    }

    public MeFragment getMeFragment() {
        if(mMeFragment == null){
            synchronized (FragmentFactory.class){
                if(mMeFragment == null){
                    mMeFragment = new MeFragment();
                }
            }
        }
        return mMeFragment;
    }
}
