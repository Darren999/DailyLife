package view.darren.com.dailylife.utils;

import android.graphics.Color;
import android.view.View;
import com.ns.yc.ycutilslib.rippleLayout.MaterialRippleLayout;

public class AppToolUtils {



    /**
     * 设置水波纹效果
     * @param view          view视图
     */
    public static void setRipper(View view){
        MaterialRippleLayout.on(view)
                .rippleColor(Color.parseColor("#999999"))
                .rippleAlpha(0.2f)
                .rippleHover(true)
                .create();
    }



}
