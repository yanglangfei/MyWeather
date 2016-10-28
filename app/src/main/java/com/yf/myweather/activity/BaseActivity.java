package com.yf.myweather.activity;

import android.app.Activity;
import android.os.Bundle;

import com.yf.myweather.utils.AppManager;
import com.yf.myweather.utils.AppUtils;

/**
 * Created by Administrator on 2016/10/20.
 */

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.addActivity(this);
    }


}
