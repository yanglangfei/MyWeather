package com.yf.myweather.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/10/26.
 */

public class ImageFile extends BmobObject implements Serializable{
    private BmobFile url;
    private  String city;
    private  String lon;
    private  String lat;
    private  int dev;
    private  String address;
    private String uName;
    private  String uId;

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }

    public int getDev() {
        return dev;
    }

    public void setDev(int dev) {
        this.dev = dev;
    }

    public BmobFile getUrl() {
        return url;
    }

    public void setUrl(BmobFile url) {
        this.url = url;
    }
}
