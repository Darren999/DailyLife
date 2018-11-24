package view.darren.com.dailylife.ui.guide.view.activity;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.yc.cn.ycrecycleviewlib.select.SelectRecyclerView;
import org.yczbj.ycrefreshviewlib.item.SpaceViewItemLine;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.base.mvp.BaseActivity;
import view.darren.com.dailylife.comment.Constant;
import view.darren.com.dailylife.inter.listener.OnListItemClickListener;
import view.darren.com.dailylife.model.SelectPoint;
import view.darren.com.dailylife.ui.guide.contract.SelectFollowContract;
import view.darren.com.dailylife.ui.guide.presenter.SelectFollowPresenter;
import view.darren.com.dailylife.ui.guide.view.adapter.SelectFollowAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectFollowActivity extends BaseActivity<SelectFollowPresenter>
    implements SelectFollowContract.View,View.OnClickListener
{
    @Bind(R.id.ll_title_menu)
    FrameLayout llTitleMenu;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;
    @Bind(R.id.select_view)
    SelectRecyclerView selectView;
    @Bind(R.id.tv_clean)
    TextView tvClean;
    @Bind(R.id.tv_start)
    TextView tvStart;

    private SelectFollowPresenter presenter = new SelectFollowPresenter(this);
    private SelectFollowAdapter adapter;
    private List<SelectPoint> lists = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.activity_select_follow;
    }

    @Override
    public void initView() {
        initToolBar();
        initRecycleView();
    }

    private void initToolBar() {
        toolbarTitle.setText("关注点");
        tvTitleRight.setVisibility(View.VISIBLE);
        tvTitleRight.setText("跳过");
    }

    @Override
    public void initListener() {
        llTitleMenu.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
        tvClean.setOnClickListener(this);
        tvStart.setOnClickListener(this);
        adapter.setOnItemClickListener(new OnListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(adapter.data != null && adapter.data.size()>0){
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
            case R.id.ll_title_menu:
            case R.id.tv_title_right:
                toMainActivity();
                break;
            case R.id.tv_clean:
                if(adapter != null && adapter.data != null){
                    adapter.clearSelected();
                }
                break;
            case R.id.tv_start:
                if(adapter != null && adapter.data != null){
                    if(adapter.getSelectedIndices().length > 0){
                        presenter.addSelectToRealm(adapter.getSelectedIndices());
                    }
                }
                toMainActivity();
                break;
        }
    }

    private void initRecycleView() {
        selectView.setLayoutManager(new GridLayoutManager(this,4));
        adapter = new SelectFollowAdapter(this,lists);
        selectView.setAdapter(adapter);

        SpaceViewItemLine itemDecoration = new SpaceViewItemLine(SizeUtils.dp2px(5));
        itemDecoration.setPaddingEdgeSide(false);
        itemDecoration.setPaddingStart(false);
        itemDecoration.setPaddingHeaderFooter(false);
        selectView.addItemDecoration(itemDecoration);
    }

    @Override
    public void refreshData(List<SelectPoint> list) {
        lists.clear();
        lists.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void toMainActivity() {
        ActivityUtils.startActivity(MainActivity.class,R.anim.screen_zoom_in,R.anim.screen_zoom_out);
        SPUtils.getInstance(Constant.SP_NAME).put(Constant.KEY_FIRST_SPLASH,false);
        finish();
    }
}
