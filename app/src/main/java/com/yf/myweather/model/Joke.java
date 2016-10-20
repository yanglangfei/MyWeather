package com.yf.myweather.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/20.
 */

public class Joke implements Serializable {

    /**
     * content : 物理课上，物理老师正在讲卷子，　　“你们把这道题好好看看、仔细看、认真看！”　　然后大家就开始认真读题，眉头紧锁，苦思冥想，几分钟过去。　　老师：“好，这道题是个错题，我们来把它划掉！”
     * hashId : 64dca0d30a12d7e099bb9bb3f842ef60
     * unixtime : 1476927230
     * updatetime : 2016-10-20 09:33:50
     */

    private String content;
    private String hashId;
    private int unixtime;
    private String updatetime;

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
}
