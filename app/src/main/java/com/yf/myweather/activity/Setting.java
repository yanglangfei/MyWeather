package com.yf.myweather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yf.myweather.R;

/**
 * Created by Administrator on 2016/11/1.
 */

public class Setting extends Activity implements View.OnClickListener
{

    private ImageView ivFinish;
    private TextView lable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_setting);
        initView();
    }

    private void initView() {
        ivFinish = (ImageView) findViewById(R.id.iv_finish);
        lable= (TextView) findViewById(R.id.lable);
        lable.setVisibility(View.VISIBLE);
        lable.setText("设置");
        ivFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.finish();
    }
}
