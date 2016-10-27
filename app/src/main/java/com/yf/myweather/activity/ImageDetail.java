package com.yf.myweather.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yf.myweather.R;
import com.yf.myweather.view.ZoomImageView;

/**
 * Created by Administrator on 2016/10/21.
 */

public class ImageDetail extends BaseActivity implements View.OnClickListener {
    private ImageView iv_zoom;
    private  ImageView iv_finish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_iv_detail);
        initView();
    }

    private void initView() {
        iv_zoom= (ImageView) findViewById(R.id.iv_zoom);
        iv_finish= (ImageView) findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(this);
        String imagePath = getIntent().getStringExtra("image_path");
        if(imagePath.endsWith("gif")){
            Glide.with(this).load(imagePath).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_zoom);
        }else {
            Glide.with(this).load(imagePath).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_zoom);
        }
    }

    @Override
    public void onClick(View view) {
        this.finish();
    }
}
