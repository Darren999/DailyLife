package view.darren.com.dailylife.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.yc.cn.ycbaseadapterlib.BaseViewHolder;

public class BaseDelegateAdapter extends DelegateAdapter.Adapter<BaseViewHolder> {
    private LayoutHelper mLayoutHelper;
    private int mCount = -1;
    private int mLayoutId = -1;
    private Context mContext;
    private int mViewTypeItem = -1;

    protected BaseDelegateAdapter(Context context, LayoutHelper layoutHelper, int layoutId, int count, int viewTypeItem) {
        this.mContext = context;
        this.mLayoutHelper = layoutHelper;
        this.mLayoutId = layoutId;
        this.mCount = count;
        this.mViewTypeItem = viewTypeItem;
    }
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == mViewTypeItem){
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(mLayoutId,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return mViewTypeItem;
    }

    @Override
    public int getItemCount() {
        return mCount;
    }
}