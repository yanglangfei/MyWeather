package com.yf.myweather.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * Created by Administrator on 2016/6/19.
 */
public class UILImageLoader implements cn.finalteam.galleryfinal.ImageLoader {

    private Bitmap.Config mImageConfig;

    public UILImageLoader() {
        this(Bitmap.Config.RGB_565);
    }

    public UILImageLoader(Bitmap.Config config) {
        this.mImageConfig = config;
    }

    @Override
    public void displayImage(Activity activity, String path, GFImageView imageView, Drawable defaultDrawable, int width, int height) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(false)
                .cacheInMemory(false)
                .bitmapConfig(mImageConfig)
                .build();
        ImageLoader loader = ImageLoader.getInstance();
        loader.init(ImageLoaderConfiguration.createDefault(activity));
        ImageSize imageSize = new ImageSize(width, height);
        loader.displayImage("file://" + path, new ImageViewAware(imageView), options, imageSize, null, null);
    }

    @Override
    public void clearMemoryCache() {

    }
}
