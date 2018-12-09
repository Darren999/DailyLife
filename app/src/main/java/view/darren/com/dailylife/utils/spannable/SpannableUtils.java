package view.darren.com.dailylife.utils.spannable;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import view.darren.com.dailylife.R;

public class SpannableUtils {


    /**
     * 字符串拼接
     * @param context                   上下文
     * @param beforeText                前面标签文件
     * @param afterText                 后面内容
     * @return
     */
    public static SpannableStringBuilder appendString(
            Context context , String beforeText , String afterText){
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        String thisTag = " " + beforeText + " ";
        stringBuilder.append(thisTag);
        stringBuilder.append("  ");
        stringBuilder.append(afterText);
        RoundedBackgroundSpan span;
        span= new RoundedBackgroundSpan(ContextCompat.getColor(context,
                R.color.redTab), R.color.priority_yellow);
        stringBuilder.setSpan(span, 0, thisTag.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.append(" ");
        return stringBuilder;
    }


}