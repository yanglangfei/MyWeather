package com.yf.myweather.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/28.
 */

public class AppManager {
    private static List<Activity> mActivities=new ArrayList<>();
    public  static  void addActivity(Activity activity){
      if(activity!=null&&!mActivities.contains(activity)){
          mActivities.add(activity);
      }

    }

    public  static  void removeActivity(Activity activity){
        if(mActivities.contains(activity)){
            mActivities.remove(activity);
        }

    }

    public  static  void finishActivity(){
        for(Activity activity : mActivities){
            activity.finish();
        }

    }


}
