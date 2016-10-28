package com.yf.myweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Random;
import android.os.Handler;

import com.yf.myweather.activity.BaseActivity;
import com.yf.myweather.activity.ChoseCityActivity;
import com.yf.myweather.view.HouseLoadingView;

public class WelcomActivity extends BaseActivity{

    private HouseLoadingView loadingView;
    private int mProgress = 0;
    private static final int REFRESH_PROGRESS = 0x10;

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_PROGRESS:
                    if (mProgress < 40) {
                        mProgress += 1;
                        // 随机500ms以内刷新一次
                        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS,
                                new Random().nextInt(200));
                        loadingView.setProgress(mProgress);
                    } else if(mProgress<100){
                        mProgress += 1;
                        // 随机800ms以内刷新一次
                        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS,
                                new Random().nextInt(300));
                        loadingView.setProgress(mProgress);
                    }else {
                        Intent chose=new Intent();
                        chose.setClass(WelcomActivity.this, ChoseCityActivity.class);
                        WelcomActivity.this.startActivity(chose);
                        WelcomActivity.this.finish();
                    }
                    break;

                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcom);

        loadingView = (HouseLoadingView) findViewById(R.id.house);
        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS, 3000);
    }


}
