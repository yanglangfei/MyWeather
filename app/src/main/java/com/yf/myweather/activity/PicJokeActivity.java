package com.yf.myweather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.yf.myweather.R;
import com.yf.myweather.adapter.PicAdapter;
import com.yf.myweather.model.Joke;
import com.yf.myweather.model.PicJoke;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */

public class PicJokeActivity extends BaseActivity {
    private ListView lv_joke;
    private PicAdapter mAdapter;
    private  String getPicJoke="http://japi.juhe.cn/joke/img/text.from";
    private List<PicJoke> mPicJokes=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_joke);
        initView();
        initData();
    }

    private void initData() {
        RequestParams param=new RequestParams(getPicJoke);
        param.addParameter("key","c4da5201e43eebf18ca5630e1390f525");
        param.addParameter("page",1);
        param.addParameter("pagesize",20);
        x.http().get(param, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                Log.i("111","res:"+result);
                if(result!=null) {
                    try {
                        JSONObject object = new JSONObject(result);
                        int error_code = object.optInt("error_code");
                        if (error_code == 0) {
                            JSONObject res = object.getJSONObject("result");
                            JSONArray array = res.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                String content = obj.optString("content");
                                String updatetime = obj.optString("updatetime");
                                int unixtime = obj.optInt("unixtime");
                                String hashId = obj.optString("hashId");
                                String url = obj.optString("url");
                                PicJoke joke = new PicJoke();
                                joke.setUpdatetime(updatetime);
                                joke.setUnixtime(unixtime);
                                joke.setHashId(hashId);
                                joke.setContent(content);
                                joke.setUrl(url);
                                mPicJokes.add(joke);
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void initView() {
        lv_joke= (ListView) findViewById(R.id.lv_joke);
        mAdapter=new PicAdapter(mPicJokes,this);
        lv_joke.setAdapter(mAdapter);


    }
}
