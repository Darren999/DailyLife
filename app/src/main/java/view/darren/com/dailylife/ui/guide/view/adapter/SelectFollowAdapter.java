package view.darren.com.dailylife.ui.guide.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yc.cn.ycrecycleviewlib.select.SelectRecyclerViewAdapter;
import com.ycbjie.library.inter.listener.OnListItemClickListener;

import java.util.List;

import view.darren.com.dailylife.R;
import view.darren.com.dailylife.model.bean.SelectPoint;

public class SelectFollowAdapter extends SelectRecyclerViewAdapter<SelectFollowAdapter.MyViewHolder> {

    public Activity activity;
    public List<SelectPoint> data;
    private OnListItemClickListener mItemClickListener;


    public SelectFollowAdapter(Activity activity, List<SelectPoint> data) {
        this.activity = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(activity).inflate(R.layout.tag_select_follow,parent,false);
        return new MyViewHolder(view,mItemClickListener);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(data!= null && data.size()>0){
            holder.tvName.setText(data.get(position).getName());
            if(isIndexSelected(position)){
                holder.tvName.setBackgroundResource(R.drawable.shape_btn_color_bg_press);
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            }else {
                holder.tvName.setBackgroundResource(R.drawable.shape_btn_color_bg);
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.blackText));
            }
        }
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private  OnListItemClickListener listener;
        TextView tvName;

        public MyViewHolder(@NonNull View itemView, OnListItemClickListener mItemClickListener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            this.listener = mItemClickListener;
            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view,getAdapterPosition());
                }
            });
        }
    }

    public void setmItemClickListener(OnListItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
