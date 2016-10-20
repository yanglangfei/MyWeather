package com.yf.myweather.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/20.
 */

public class PicJoke implements Serializable {
    /**
     * content : 你们是来搞笑的吗
     * hashId : 190B25E454C75D24510C603FB08ADD95
     * unixtime : 1474171515
     * updatetime : 2016-09-18 12:05:15
     * url : http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201609/18/190B25E454C75D24510C603FB08ADD95.gif
     */

    private String content;
    private String hashId;
    private int unixtime;
    private String updatetime;
    private String url;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public int getUnixtime() {
        return unixtime;
    }

    public void setUnixtime(int unixtime) {
        this.unixtime = unixtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
