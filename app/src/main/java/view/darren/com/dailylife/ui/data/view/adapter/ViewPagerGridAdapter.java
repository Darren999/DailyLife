package view.darren.com.dailylife.ui.data.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.comment.Constant;
import view.darren.com.dailylife.model.bean.ImageIconBean;

import java.util.List;

public class ViewPagerGridAdapter extends BaseAdapter {
    private Context context;
    private List<ImageIconBean> lists;      //数据源
    private int mIndex;                     // 页数下标，标示第几页，从0开始
    private int mPagerSize;                // 每页显示的最大的数量


    public ViewPagerGridAdapter(Context context, List<ImageIconBean> lists, int mIndex, int mPagerSize){
        this.context = context;
        this.lists = lists;
        this.mIndex = mIndex;
        this.mPagerSize = mPagerSize;
    }

    @Override
    public int getCount() {
        return lists.size() > (mIndex + 1) * mPagerSize ? mPagerSize : (lists.size() - mIndex*mPagerSize);
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position + mIndex * mPagerSize);
    }

    @Override
    public long getItemId(int position) {
        return position + mIndex * mPagerSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.item_vp_grid_iv, null);

            holder = new ViewHolder();
            holder.tv_name = (TextView)convertView.findViewById(R.id.tv_new_seed_title);
            holder.iv_nul = (ImageView)convertView.findViewById(R.id.iv_new_seed_ic);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        final int pos = position + mIndex * mPagerSize;//假设mPageSiez

        holder.tv_name.setText(lists.get(pos).getName());
        holder.iv_nul.setImageResource(lists.get(pos).getUrl());

        return convertView;
    }

    private static class ViewHolder{
        private TextView tv_name;
        private ImageView iv_nul;
    }
}
