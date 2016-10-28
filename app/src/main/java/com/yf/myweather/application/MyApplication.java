package com.yf.myweather.application;

import android.app.Application;
import android.util.Log;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.yf.myweather.utils.UILImageLoader;

import org.xutils.x;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/10/9.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //xUtil
        x.Ext.init(this);

        //Bmob
        Bmob.initialize(this, "0cdd926251e86469ae4e02a3dc682c7a");
        // 使用推送服务时的初始化操作
        //BmobInstallation.getCurrentInstallation().save();
        // 启动推送服务
        BmobPush.startWork(this);

        //讯飞语音
        SpeechUtility.createUtility(this,  SpeechConstant.APPID +"=58099a6c" );

       //Mob
        SMSSDK.initSDK(this, "186779d041014", "cc0c60321b3747ea3b19052577d21f64");


        //相机、相册 GalleryFinal
        ThemeConfig theme = new ThemeConfig.Builder().build();  //设置主题
        FunctionConfig function = new FunctionConfig.Builder().build();  //配置功能
        ImageLoader loader = new UILImageLoader();  //配置ImageLoader
        CoreConfig config = new CoreConfig.Builder(this, loader, theme).setFunctionConfig(function).build();
        GalleryFinal.init(config);
    }
}
