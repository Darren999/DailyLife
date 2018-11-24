package view.darren.com.dailylife.ui.guide.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yc.cn.ycrecycleviewlib.select.SelectRecyclerViewAdapter;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.inter.listener.OnListItemClickListener;
import view.darren.com.dailylife.model.SelectPoint;

import java.util.List;

public class SelectFollowAdapter extends SelectRecyclerViewAdapter<SelectFollowAdapter.MyViewHolder> {

    public Activity activity;
    public List<SelectPoint> data;
    private OnListItemClickListener mItemClickListener;

    public SelectFollowAdapter(Activity activity,List<SelectPoint> data){
        this.activity = activity;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.tag_select_follow,parent,false);
        return new MyViewHolder(view,mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return (data==null) ? 0 :data.size();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(data != null && data.size()>0){
            holder.tvName.setText(data.get(position).getName());
            if(isIndexSelected(position)){
                holder.tvName.setBackgroundResource(R.drawable.shape_btn_color_bg_press);
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            }else{
                holder.tvName.setBackgroundResource(R.drawable.shape_btn_color_bg);
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.blackText));
            }
        }
    }

    public void setOnItemClickListener(OnListItemClickListener listener){
        this.mItemClickListener = listener;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        private  OnListItemClickListener listener;
        TextView tvName;

        public MyViewHolder(View itemView,OnListItemClickListener mItemClickListener) {
            super(itemView);
            tvName = (TextView)itemView.findViewById(R.id.tv_name);
            this.listener = mItemClickListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view,getAdapterPosition());
                }
            });
        }
    }
}
