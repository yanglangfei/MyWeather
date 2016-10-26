package com.yf.myweather.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Administrator on 2016/10/26.
 */

public class AppUtils {


    public static int getWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }

    public static int getHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int h = dm.heightPixels;
        return h;
    }
}
