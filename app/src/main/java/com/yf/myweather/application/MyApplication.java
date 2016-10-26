package com.yf.myweather.application;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.yf.myweather.utils.UILImageLoader;

import org.xutils.x;

import cn.bmob.v3.Bmob;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by Administrator on 2016/10/9.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        Bmob.initialize(this, "0cdd926251e86469ae4e02a3dc682c7a");
        SpeechUtility.createUtility(this,  SpeechConstant.APPID +"=58099a6c" );

        ThemeConfig theme = new ThemeConfig.Builder().build();  //设置主题
        FunctionConfig function = new FunctionConfig.Builder().build();  //配置功能
        ImageLoader loader = new UILImageLoader();  //配置ImageLoader
        CoreConfig config = new CoreConfig.Builder(this, loader, theme).setFunctionConfig(function).build();
        GalleryFinal.init(config);
    }
}
