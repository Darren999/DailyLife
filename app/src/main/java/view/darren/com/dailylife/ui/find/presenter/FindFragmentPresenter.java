package view.darren.com.dailylife.ui.find.presenter;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.blankj.utilcode.util.SizeUtils;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;
import com.yc.cn.ycbannerlib.BannerView;
import com.yc.cn.ycbannerlib.marquee.MarqueeView;
import com.yc.cn.ycbannerlib.marquee.OnItemClickListener;
import com.yc.cn.ycbaseadapterlib.BaseViewHolder;
import me.yokeyword.indexablerv.database.HeaderFooterDataObservable;
import rx.subscriptions.CompositeSubscription;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.base.adapter.BaseDelegateAdapter;
import view.darren.com.dailylife.comment.Constant;
import view.darren.com.dailylife.model.bean.HomeBlogEntity;
import view.darren.com.dailylife.ui.find.contract.FindFragmentContract;
import view.darren.com.dailylife.ui.guide.view.activity.MainActivity;
import view.darren.com.dailylife.utils.image.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class FindFragmentPresenter implements FindFragmentContract.Presenter {

    private FindFragmentContract.View mView;
    private CompositeSubscription mSubscriptions;
    private MainActivity activity;

    public FindFragmentPresenter(FindFragmentContract.View androidView) {
        this.mView = androidView;
        //this.activity = (MainActivity) androidView;       //有时会报空指针
        mSubscriptions = new CompositeSubscription();
    }

    private CompositeSubscription mSubScriptions;

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubScriptions.unsubscribe();
        if (activity != null) {
            activity = null;
        }
    }

    @Override
    public DelegateAdapter initRecyclerView(RecyclerView recyclerView) {
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(0, 20);
        recyclerView.setRecycledViewPool(viewPool);

        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setAdapter(delegateAdapter);

        return delegateAdapter;
    }

    @Override
    public BaseDelegateAdapter initBannerAdapter() {
        final List<Object> arrayList = new ArrayList<>();
        arrayList.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
        arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");

        return new BaseDelegateAdapter(activity, new LinearLayoutHelper(), R.layout.base_match_banner, 1, Constant.viewType.typeBanner) {
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                BannerView bannerView = holder.getView(R.id.banner);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                layoutParams.height = SizeUtils.dp2px(120);
                bannerView.setLayoutParams(layoutParams);
                mView.setBanner(bannerView, arrayList);
            }
        };
    }

    @Override
    public BaseDelegateAdapter initGvMenu() {
        final TypedArray proPic = activity.getResources().obtainTypedArray(R.array.find_gv_image);
        final String[] proName = activity.getResources().getStringArray(R.array.find_gv_title);

        final List<Integer> images = new ArrayList<>();
        for (int i = 0; i < proName.length; i++) {
            images.add(proPic.getResourceId(i, R.drawable.ic_data_picture));
        }
        proPic.recycle();

        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setPadding(0, 16, 0, 16);
        gridLayoutHelper.setVGap(16);
        gridLayoutHelper.setHGap(0);
        gridLayoutHelper.setBgColor(Color.WHITE);
        return new BaseDelegateAdapter(activity, gridLayoutHelper, R.layout.item_vp_grid_iv, 8, Constant.viewType.typeGv) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_new_seed_title, proName[position]);
                holder.setImageResource(R.id.iv_new_seed_ic, images.get(position));
                holder.getView(R.id.ll_new_seed_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mView.setOnclick(position);
                    }
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initMarqueeView() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(activity, linearLayoutHelper, R.layout.view_vlayout_marquee, 1, Constant.viewType.typeMarquee) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                MarqueeView marqueeView = holder.getView(R.id.marqueeView);

                List<String> info = new ArrayList<>();
                info.add("1.坚持读书，写作，源于内心的动力！");
                info.add("2.欢迎订阅喜马拉雅听书！");
                info.add("3.欢迎关注我的GitHub！");

                marqueeView.startWithList(info);
                marqueeView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, TextView textView) {
                        mView.setMarqueeClick(position);
                    }
                });

            }
        };
    }

    @Override
    public BaseDelegateAdapter initTitle(final String title) {
        return new BaseDelegateAdapter(activity, new LinearLayoutHelper(), R.layout.base_view_title, 1, Constant.viewType.typeTitle) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, title);
            }
        };
    }

    @Override
    public BaseDelegateAdapter initList1() {
        final TypedArray listImage = activity.getResources().obtainTypedArray(R.array.find_list1_image);
        final String[] listTitle = activity.getResources().getStringArray(R.array.find_list1_title);
        final List<Integer> images = new ArrayList<>();

        for (int i = 0; i < listImage.length(); i++) {
            images.add(listImage.getResourceId(i, R.drawable.ic_investment));
        }
        listImage.recycle();

        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setMargin(0, 0, 0, 0);
        gridLayoutHelper.setPadding(0, 20, 0, 10);
        gridLayoutHelper.setVGap(10);
        gridLayoutHelper.setHGap(10);
        gridLayoutHelper.setWeights(new float[]{25, 25, 25, 25});
        gridLayoutHelper.setBgColor(Color.WHITE);

        return new BaseDelegateAdapter(activity, gridLayoutHelper, R.layout.view_vlayout_grid, 8, Constant.viewType.typeGvSecond) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, listTitle[position]);
                ImageView iv = holder.getView(R.id.iv_image);
                ImageUtils.loadImgByPicasso(activity, images.get(position), iv);
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mView.setGridClick(position);
                    }
                });
            }
        };

    }

    @Override
    public BaseDelegateAdapter initList2() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setAspectRatio(4.0f);
        linearLayoutHelper.setDividerHeight(5);
        linearLayoutHelper.setMargin(0, 0, 0, 0);
        linearLayoutHelper.setPadding(0, 0, 0, 0);

        return new BaseDelegateAdapter(activity, linearLayoutHelper, R.layout.item_tx_news_list, 3, Constant.viewType.typeNews) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                if (Constant.findNews != null && Constant.findNews.size() > 0) {
                    HomeBlogEntity model = Constant.findNews.get(position);
                    holder.setText(R.id.tv_title, model.getTitle());
                    ImageView iv = holder.getView(R.id.iv_logo);

                    ImageUtils.loadImgByPicasso(activity, model.getImageUrl(), R.drawable.image_default, iv);
                    holder.setText(R.id.tv_time, model.getTime());
                    holder.getItemView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mView.setNewsList2Click(position, Constant.findNews.get(position).getUrl());
                        }
                    });
                } else {
                    ImageView imageView = holder.getView(R.id.iv_logo);
                    holder.setText(R.id.tv_title, "标题");
                    imageView.setImageResource(R.drawable.image_default);
                    holder.setText(R.id.tv_time, "时间");
                }
            }
        };
    }

    @Override
    public BaseDelegateAdapter initList3() {
        final TypedArray typedArray = activity.getResources().obtainTypedArray(R.array.find_list3_image);
        final String[] titles = activity.getResources().getStringArray(R.array.find_list3_title);
        final List<Integer> images = new ArrayList<>();

        for (int i = 0; i < typedArray.length(); i++) {
            images.add(typedArray.getResourceId(i, R.drawable.ic_investment));
        }

        typedArray.recycle();

        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
        gridLayoutHelper.setMargin(20, 0, 20, 0);
        gridLayoutHelper.setPadding(0, 20, 0, 10);
        gridLayoutHelper.setVGap(0);
        gridLayoutHelper.setHGap(5);
        gridLayoutHelper.setWeights(new float[]{30, 40, 30});
        gridLayoutHelper.setBgColor(Color.WHITE);

        return new BaseDelegateAdapter(activity, gridLayoutHelper, R.layout.base_btn_title_view, 3, Constant.viewType.typeList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, titles[position]);
                ImageView imageView = holder.getView(R.id.iv_image);
                ImageUtils.loadImgByPicasso(activity, images.get(position), imageView);
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mView.setGridClick(position);
                    }
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initList4() {
        final TypedArray typedArray = activity.getResources().obtainTypedArray(R.array.find_list4_image);
        final String[] titles = activity.getResources().getStringArray(R.array.find_list4_title);
        final List<Integer> imageList = new ArrayList<>();

        for (int i = 0; i < typedArray.length(); i++) {
            imageList.add(typedArray.getResourceId(i, R.drawable.ic_investment));
        }

        typedArray.recycle();

        OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper();
        onePlusNLayoutHelper.setMargin(0, 0, 0, 0);
        onePlusNLayoutHelper.setPadding(10, 20, 10, 20);
        onePlusNLayoutHelper.setBgColor(activity.getResources().getColor(R.color.colorWhite));

        return new BaseDelegateAdapter(activity, onePlusNLayoutHelper, R.layout.view_vlayout_plus, 3, Constant.viewType.typePlus) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    holder.getView(R.id.ll_first).setVisibility(View.VISIBLE);
                    holder.getView(R.id.ll_second).setVisibility(View.GONE);

                    holder.setText(R.id.tv_title, titles[position]);
                    holder.setImageResource(R.id.iv_image, imageList.get(position));
                } else {

                    holder.getView(R.id.ll_first).setVisibility(View.GONE);
                    holder.getView(R.id.ll_second).setVisibility(View.VISIBLE);
                    holder.setText(R.id.tv_title2, titles[position]);
                    holder.setImageResource(R.id.iv_image2, imageList.get(position));
                }

                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mView.setGridClick(position);
                    }
                });

            }
        };
    }

    @Override
    public BaseDelegateAdapter initList5() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setMargin(0,0,0,0);
        linearLayoutHelper.setPadding(0,0,0,0);
        linearLayoutHelper.setDividerHeight(5);
        linearLayoutHelper.setAspectRatio(4.0f);


        return new BaseDelegateAdapter(activity,
                linearLayoutHelper, R.layout.view_vlayout_news, 10, Constant.viewType.typeFooter) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                if(Constant.findBottomNews!= null && Constant.findBottomNews.size() > 0){
                    HomeBlogEntity model = Constant.findBottomNews.get(position);
                    holder.setText(R.id.tv_title,model.getTitle());
                    ImageView imageView = holder.getView(R.id.iv_logo);
                    ImageUtils.loadImgByPicasso(activity,model.getImageUrl(),imageView);
                    holder.setText(R.id.tv_time,model.getTime());

                    holder.getItemView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ToastUtil.showToast(activity,"initList5 position:"+position);
                        }
                    });

                }else{
                    ImageView imageView = holder.getView(R.id.iv_logo);
                    imageView.setImageResource(R.drawable.image_default);
                    holder.setText(R.id.tv_title,"新闻标题");
                    holder.setText(R.id.tv_time,"新闻时间");

                }
            }
        };
    }

    @Override
    public void bindActivity(MainActivity activity) {
        this.activity = activity;
    }
}
