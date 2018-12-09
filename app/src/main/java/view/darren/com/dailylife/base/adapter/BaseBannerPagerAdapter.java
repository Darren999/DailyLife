package view.darren.com.dailylife.base.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.yc.cn.ycbannerlib.banner.adapter.AbsStaticPagerAdapter;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.comment.Constant;
import view.darren.com.dailylife.utils.image.ImageUtils;

import java.util.List;

public class BaseBannerPagerAdapter extends AbsStaticPagerAdapter {

    private Context ctx;
    private List<Object> list;

    public BaseBannerPagerAdapter(Context ctx, List<Object> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView imageView = new ImageView(ctx);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        if(list != null){
            if(list.get(position) != null){
                if(list.get(position) instanceof String){
                    ImageUtils.loadImgByPicasso(ctx,(String) list.get(position), R.drawable.image_default,imageView);
                }else if(list.get(position) instanceof Integer){
                    ImageUtils.loadImgByPicasso(ctx,(Integer) list.get(position), R.drawable.image_default,imageView);
                }else if(list.get(position) instanceof Bitmap){
                    ImageUtils.loadImgByPicasso(ctx,(Bitmap) list.get(position), R.drawable.image_default,imageView);
                }
            }
        }else {
            ImageUtils.loadImgByPicasso(ctx, R.drawable.image_default,imageView);
        }

        return imageView;
    }

    @Override
    public int getCount() {
        return (list == null) ? 0 : list.size();
    }
}
