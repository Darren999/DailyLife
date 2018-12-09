package view.darren.com.dailylife.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class BasePagerAdapter extends FragmentPagerAdapter {
    private List<?> mFragment;
    private List<String> mTitleList;

    public BasePagerAdapter(FragmentManager fm, List<?> mFragment) {
        super(fm);
        this.mFragment = mFragment;
    }

    public BasePagerAdapter(FragmentManager fm, List<?> mFragment, List<String> mTitleList) {
        super(fm);
        this.mFragment = mFragment;
        this.mTitleList = mTitleList;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment)mFragment.get(position);
    }

    @Override
    public int getCount() {
        return (mFragment==null) ? 0 : mFragment.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mTitleList != null){
            return  mTitleList.get(position);
        }else {
            return "";
        }
    }
}
