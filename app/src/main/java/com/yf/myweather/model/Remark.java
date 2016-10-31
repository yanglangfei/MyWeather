package com.yf.myweather.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/10/31.
 */

public class Remark extends BmobObject {
    private  String uId;
    private  String fkId;
    private  String body;
    private  String uName;
    private  int type;
    private BmobFile logo;

    public BmobFile getLogo() {
        return logo;
    }

    public void setLogo(BmobFile logo) {
        this.logo = logo;
    }

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

    public String getFkId() {
        return fkId;
    }

    public void setFkId(String fkId) {
        this.fkId = fkId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
