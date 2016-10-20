package com.yf.myweather;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.yf.myweather.activity.BaseActivity;

/**
 * Created by Administrator on 2016/10/20.
 */
public class WelcomActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_welcom);
        initView();
    }

    private void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             WelcomActivity.this.startActivity(new Intent(WelcomActivity.this,ChoseCityActivity.class));
            }
        },2000);
    }
}
