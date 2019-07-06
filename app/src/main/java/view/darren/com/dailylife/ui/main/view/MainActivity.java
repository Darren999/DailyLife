package view.darren.com.dailylife.ui.main.view;

import android.view.View;

import com.ycbjie.library.base.mvp.BaseActivity;

import view.darren.com.dailylife.R;
import view.darren.com.dailylife.ui.main.contract.MainContract;
import view.darren.com.dailylife.ui.main.presenter.MainPresenter;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, View.OnClickListener {


    @Override
    public int getContentView() {
        return R.layout.activity_main;
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

    @Override
    public void onClick(View view) {

    }
}
