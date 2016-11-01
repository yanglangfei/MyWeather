package com.yf.myweather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yf.myweather.R;

/**
 * Created by Administrator on 2016/11/1.
 */

public class EditInfo extends Activity implements View.OnClickListener {

    private ImageView ivFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_info);
        initView();
    }

    private void initView() {
        ivFinish = (ImageView) findViewById(R.id.iv_finish);
        ivFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.finish();
    }
}
