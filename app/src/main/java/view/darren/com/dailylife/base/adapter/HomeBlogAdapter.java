package view.darren.com.dailylife.base.adapter;

import android.app.Activity;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.model.bean.HomeBlogEntity;
import view.darren.com.dailylife.utils.image.ImageUtils;
import view.darren.com.dailylife.utils.spannable.SpannableUtils;

public class HomeBlogAdapter extends RecyclerArrayAdapter<HomeBlogEntity> {
    public HomeBlogAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    public class MyViewHolder extends BaseViewHolder<HomeBlogEntity> {

        @Bind(R.id.ll_news_head)
        LinearLayout llNewsHead;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_author)
        TextView tvAuthor;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_type)
        TextView tvType;
        @Bind(R.id.iv_img)
        ImageView ivImg;
        @Bind(R.id.ll_new_content)
        LinearLayout llNewContent;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_home_list);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(HomeBlogEntity data) {
            super.setData(data);
            if (getAdapterPosition() == 0) {
                llNewContent.setVisibility(View.GONE);
                llNewsHead.setVisibility(View.VISIBLE);
            } else {
                llNewContent.setVisibility(View.VISIBLE);
                llNewsHead.setVisibility(View.GONE);
                ImageUtils.loadImgByPicasso(getContext(), data.getImageUrl(), R.drawable.image_default, ivImg);
                tvAuthor.setText(data.getAuthor());
                tvTime.setText(data.getTime());
                if (getAdapterPosition() == 2 || getAdapterPosition() == 5 || getAdapterPosition() == 7) {
                    SpannableStringBuilder string = SpannableUtils.appendString(
                            getContext(), "行者", data.getTitle());
                    tvTitle.setText(string);
                }else {
                    tvTitle.setText(data.getTitle());
                }
            }
        }
    }
}
