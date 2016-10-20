package com.yf.myweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.yf.myweather.activity.BaseActivity;
import com.yf.myweather.activity.WeatherActivity;

/**
 * Created by Administrator on 2016/10/20.
 */

public class ChoseCityActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_city);
        initView();
    }

    private void initView() {
        Intent intent=new Intent(this,WeatherActivity.class);
        intent.putExtra("city","上海");
       startActivity(intent);
    }

}
