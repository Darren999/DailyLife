package view.darren.com.dailylife.ui.data.view.fragment;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.*;
import butterknife.Bind;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;
import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.item.SpaceViewItemLine;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.base.adapter.BaseViewPagerRollAdapter;
import view.darren.com.dailylife.base.mvp.BaseFragment;
import view.darren.com.dailylife.model.bean.ImageIconBean;
import view.darren.com.dailylife.ui.data.contract.DataFragmentContract;
import view.darren.com.dailylife.ui.data.presenter.DataFragmentPresenter;
import view.darren.com.dailylife.ui.data.view.adapter.DataToolAdapter;
import view.darren.com.dailylife.ui.data.view.adapter.NarrowImageAdapter;
import view.darren.com.dailylife.ui.data.view.adapter.ViewPagerGridAdapter;
import view.darren.com.dailylife.ui.guide.view.activity.MainActivity;
import view.darren.com.dailylife.weight.MyGridView;

import java.util.ArrayList;
import java.util.List;

public class DataFragment extends BaseFragment<DataFragmentContract.Presenter> implements
        DataFragmentContract.View, View.OnClickListener{

    @Bind(R.id.gridView)
    MyGridView myGridView;
    @Bind(R.id.vp_pager)
    ViewPager vpPager;
    @Bind(R.id.ll_points)
    LinearLayout llPoints;
    @Bind(R.id.tv_note_edit)
    TextView tvNoteEdit;
    @Bind(R.id.tv_news_zhi_hu)
    TextView tvNewsZhiHu;
    @Bind(R.id.recyclerView)
    YCRefreshView recyclerView;


    private MainActivity activity;
    private NarrowImageAdapter adapter;
    private DataFragmentContract.Presenter presenter = new DataFragmentPresenter(this);


    @Override
    public int getContentView() {
        return R.layout.fragment_data;
    }

    @Override
    public void initView() {
        initVpData();
    }

    @Override
    public void initListener() {
        tvNoteEdit.setOnClickListener(this);
        tvNewsZhiHu.setOnClickListener(this);
    }

    @Override
    public void initData() {
        presenter.initGridViewData();
        presenter.initRecycleViewData();
    }

    @Override
    public void setGridView(String[] toolName, ArrayList<Integer> logoList) {
        DataToolAdapter adapter = new DataToolAdapter(activity,toolName,logoList);
        myGridView.setAdapter(adapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ToastUtil.showToast(activity,"setGridView:"+position);
                switch (position){
                    case 0:
                    case 1:
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void setRecycleView(final ArrayList<Integer> list) {
        adapter = new NarrowImageAdapter(activity);

        recyclerView.setAdapter(adapter);
        recyclerView.setHorizontalScrollBarEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.addItemDecoration(new SpaceViewItemLine(15));
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                adapter.addAll(list);
            }
        });

        adapter.addAll(list);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtil.showToast(activity,"setRecycleView:"+position);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_note_edit:
                ToastUtil.showToast(activity,"tv_note_edit");
                break;
            case R.id.tv_news_zhi_hu:
                ToastUtil.showToast(activity,"tv_news_zhi_hu");
                break;
            default:
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity)context;
        presenter.bindActivity(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(activity != null){
            activity = null;
        }
    }



    private void initVpData() {
        List<ImageIconBean> listData = presenter.getVpData();
        //每页显示的最大的数量
        final int mPageSize = 8;
        //总的页数向上取整
        final int totalPage = (int) Math.ceil(listData.size() * 1.0 / mPageSize);
        List<View> viewPagerList = new ArrayList<>();

        for (int i = 0; i < totalPage; i++) {
            final GridView gridView = (GridView) View.inflate(activity, R.layout.item_vp_grid_view, null);
            gridView.setAdapter(new ViewPagerGridAdapter(activity, listData, i, mPageSize));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Object obj = gridView.getItemAtPosition(position);
                    if (obj != null && obj instanceof ImageIconBean) {
                        int pos = ((ImageIconBean) obj).getId();
                        ToastUtil.showToast(activity, "AdapterView.OnItemClickListener:" + position);
                        switch (pos) {
                            case 0:
                            case 1:
                                break;
                            default:
                                break;
                        }
                    }
                }
            });

            viewPagerList.add(gridView);
        }

        vpPager.setAdapter(new BaseViewPagerRollAdapter(viewPagerList));

        final ImageView[] ivPoints = new ImageView[totalPage];
        for (int i = 0; i < totalPage; i++) {
            ivPoints[i] = new ImageView(activity);
            if (i == 0) {
                ivPoints[i].setImageResource(R.drawable.ic_page_focuese);
            } else {
                ivPoints[i].setImageResource(R.drawable.ic_page_unfocused);
            }

            ivPoints[i].setPadding(8, 8, 8, 8);
            llPoints.addView(ivPoints[i]);
        }

        vpPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < totalPage; i++) {
                    if (i == position) {
                        ivPoints[i].setImageResource(R.drawable.ic_page_focuese);
                    } else {
                        ivPoints[i].setImageResource(R.drawable.ic_page_unfocused);
                    }
                }
            }
        });
    }
}
