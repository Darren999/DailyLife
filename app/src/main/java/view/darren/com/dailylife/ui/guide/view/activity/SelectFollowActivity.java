package view.darren.com.dailylife.ui.guide.view.activity;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.yc.cn.ycrecycleviewlib.select.SelectRecyclerView;
import com.ycbjie.library.base.mvp.BaseActivity;
import com.ycbjie.library.inter.listener.OnListItemClickListener;

import org.yczbj.ycrefreshviewlib.item.SpaceViewItemLine;

import java.util.ArrayList;
import java.util.List;

import view.darren.com.dailylife.R;
import view.darren.com.dailylife.model.bean.SelectPoint;
import view.darren.com.dailylife.ui.guide.contract.SelectFollowContract;
import view.darren.com.dailylife.ui.guide.presenter.SelectFollowPresenter;
import view.darren.com.dailylife.ui.guide.view.adapter.SelectFollowAdapter;

public class SelectFollowActivity extends BaseActivity<SelectFollowPresenter>
    implements SelectFollowContract.View, View.OnClickListener {

    FrameLayout llTitleMenu;
    TextView toolbarTitle;
    TextView tvTitleRight;
    SelectRecyclerView selectView;
    TextView tvClean;
    TextView tvStart;



    private List<SelectPoint> lists = new ArrayList<>();
    private SelectFollowAdapter adapter;

    private SelectFollowContract.Presenter presenter = new SelectFollowPresenter(this);

    @Override
    public int getContentView() {
        return R.layout.activity_select_follow;
    }

    @Override
    public void initView() {

        llTitleMenu = findViewById(R.id.ll_title_menu);
        toolbarTitle = findViewById(R.id.toolbar_title);
        tvTitleRight = findViewById(R.id.tv_title_right);
        selectView = findViewById(R.id.select_view);

        tvClean = findViewById(R.id.tv_clean);
        tvStart = findViewById(R.id.tv_start);

        initToolBar();
        initRecycleView();
    }

    private void initToolBar() {
        toolbarTitle.setText("关注点");
        tvTitleRight.setVisibility(View.VISIBLE);
        tvTitleRight.setText("跳过");
        tvClean.setText("清除选择");
        tvStart.setText("现在开启");
    }

    private void initRecycleView() {
        selectView.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new SelectFollowAdapter(this,lists);
        selectView.setAdapter(adapter);

        //下划线
        SpaceViewItemLine itemDecoration = new SpaceViewItemLine(SizeUtils.dp2px(5));
        itemDecoration.setPaddingEdgeSide(false);
        itemDecoration.setPaddingStart(false);
        itemDecoration.setPaddingHeaderFooter(false);
        selectView.addItemDecoration(itemDecoration);
    }

    @Override
    public void initListener() {
        llTitleMenu.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
        tvClean.setOnClickListener(this);
        tvStart.setOnClickListener(this);
        adapter.setmItemClickListener(new OnListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if((adapter.data != null) && adapter.data.size()>0){
                    adapter.toggleSelected(position);
                }
            }
        });
    }

    @Override
    public void initData() {
        presenter.addData(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.tv_clean:
                if(adapter != null && adapter.data != null){
                    adapter.clearSelected();
                }
                break;
            case R.id.ll_title_menu:
            case R.id.tv_title_right:
            case R.id.tv_start:
                toMainActivity();
                break;
            default:
                break;
        }
    }


    @Override
    public void refreshData(List<SelectPoint> list) {
        lists.clear();
        lists.addAll(list);


        adapter.notifyDataSetChanged();
    }

    @Override
    public void toMainActivity() {

    }
}
