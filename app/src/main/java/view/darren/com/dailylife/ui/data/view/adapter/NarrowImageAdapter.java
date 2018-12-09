package view.darren.com.dailylife.ui.data.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.blankj.utilcode.util.SizeUtils;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

public class NarrowImageAdapter extends RecyclerArrayAdapter<Integer> {
    public NarrowImageAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NarrowImageViewHolder(parent);
    }

    private static class NarrowImageViewHolder extends BaseViewHolder<Integer> {
        ImageView imgPicture;

        public NarrowImageViewHolder(ViewGroup parent) {
            super(new ImageView(parent.getContext()));
            imgPicture = (ImageView)itemView;
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(SizeUtils.dp2px(150f), ViewGroup.LayoutParams.MATCH_PARENT);
            imgPicture.setLayoutParams(layoutParams);
            imgPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        @Override
        public void setData(Integer data) {
            imgPicture.setImageResource(data);
        }
    }
}
