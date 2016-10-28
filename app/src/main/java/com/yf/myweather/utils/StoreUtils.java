package com.yf.myweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/10/27.
 */

public class StoreUtils {

    private static SharedPreferences localStr;
    private static SharedPreferences lonstr;
    private static SharedPreferences latstr;
    private static SharedPreferences addressStr;
    private static SharedPreferences userInfo;

    public  static  String getPlace(Context context){
        localStr=context.getSharedPreferences("localPlace",Context.MODE_PRIVATE);
        return  localStr.getString("city","未知");
    }


    public  static  void savePlace(Context context,String city){
        localStr=context.getSharedPreferences("localPlace",Context.MODE_PRIVATE);
        SharedPreferences.Editor et = localStr.edit();
        et.putString("city",city).commit();
    }


    public  static  String getAddress(Context context){
        addressStr=context.getSharedPreferences("localAddress",Context.MODE_PRIVATE);
        return  addressStr.getString("address","未知");
    }


    public  static  void saveAddress(Context context,String city){
        addressStr=context.getSharedPreferences("localAddress",Context.MODE_PRIVATE);
        SharedPreferences.Editor et = addressStr.edit();
        et.putString("address",city).commit();
    }


    public  static  String getLon(Context context){
        lonstr=context.getSharedPreferences("localLon",Context.MODE_PRIVATE);
        return  lonstr.getString("lon","0");
    }


    public  static  void saveLon(Context context,String lon){
        lonstr=context.getSharedPreferences("localLon",Context.MODE_PRIVATE);
        SharedPreferences.Editor et = lonstr.edit();
        et.putString("lon",lon).commit();
    }


    public  static  String getLat(Context context){
        latstr=context.getSharedPreferences("localLat",Context.MODE_PRIVATE);
        return  latstr.getString("lat","0");
    }


    public  static  void saveLat(Context context,String lat){
        latstr=context.getSharedPreferences("localLat",Context.MODE_PRIVATE);
        SharedPreferences.Editor et = latstr.edit();
        et.putString("lat",lat).commit();
    }

    public  static  String getUid(Context context){
        userInfo=context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        return  userInfo.getString("uId","");
    }


    public  static  void saveUid(Context context,String  uId){
        userInfo=context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor et = userInfo.edit();
        et.putString("uId",uId).commit();
    }

    public  static  void cleanUid(Context context){
        userInfo=context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor et = userInfo.edit();
        et.clear().commit();
    }
}


