package com.yf.myweather.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/10/26.
 */

public class ImageFile extends BmobObject {
    private BmobFile url;

    public BmobFile getUrl() {
        return url;
    }

    public void setUrl(BmobFile url) {
        this.url = url;
    }
}
