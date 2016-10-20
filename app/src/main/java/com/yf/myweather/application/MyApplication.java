package com.yf.myweather.application;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2016/10/9.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
