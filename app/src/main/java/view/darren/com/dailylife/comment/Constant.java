package view.darren.com.dailylife.comment;

import android.os.Environment;
import view.darren.com.dailylife.model.bean.HomeBlogEntity;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static final int REALM_VERSION = 0;
    public static final String REALM_NAME = "yc";
    public static final String SP_NAME = "yc";
    public static final String ExternalStorageDirectory =
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/";

    public static String DATABASE_FILE_PATH_FOLDER = "WeekToDo";

    public interface viewType{
        int typeBanner = 1;         //轮播图
        int typeGv = 2;             //九宫格
        int typeTitle = 3;          //标题
        int typeList = 4;           //list
        int typeNews = 5;           //新闻
        int typeMarquee = 6;        //跑马灯
        int typePlus = 7 ;          //不规则视图
        int typeSticky = 8;         //指示器
        int typeFooter = 9;         //底部
        int typeGvSecond = 10;      //九宫格
    }

    public static final String KEY_FIRST_SPLASH = "first_splash";                 //是否第一次启动

    /**-------------------------------------集合-------------------------------------------------**/
    //
    public static List<HomeBlogEntity> findNews = new ArrayList<>();
    public static List<HomeBlogEntity> findBottomNews = new ArrayList<>();


    /**-------------------------------------腾讯x5页面-------------------------------------------------**/
    public static final String SP_NO_IMAGE = "no_image";
    public static final String SP_AUTO_CACHE = "auto_cache";
}
