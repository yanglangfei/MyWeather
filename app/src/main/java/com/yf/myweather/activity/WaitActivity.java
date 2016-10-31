package com.yf.myweather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yf.myweather.R;

/**
 * Created by Administrator on 2016/10/31.
 */

public class WaitActivity extends Activity implements View.OnClickListener {
    private ImageView iv_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_wait);
        initView();
    }

    private void initView() {
        iv_finish= (ImageView) findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.finish();
    }
}
