package com.yf.myweather.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.yf.myweather.R;
import com.yf.myweather.adapter.JokeAdapter;
import com.yf.myweather.model.Joke;

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

public class JokeActivity extends BaseActivity {
    private ListView lv_joke;
    private JokeAdapter mAdapter;
    private List<Joke> mJokes=new ArrayList<>();
    private  String getJoke="http://japi.juhe.cn/joke/content/text.from";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_joke);
        initView();
        initJokeData();

    }

    private void initJokeData() {
        RequestParams param=new RequestParams(getJoke);
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
                if(result!=null){
                    try {
                        JSONObject object=new JSONObject(result);
                        int error_code=object.optInt("error_code");
                        if(error_code==0){
                            JSONObject res=object.getJSONObject("result");
                            JSONArray array=res.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj=array.getJSONObject(i);
                                String content=obj.optString("content");
                                String updatetime=obj.optString("updatetime");
                                int unixtime=obj.optInt("unixtime");
                                String hashId=obj.optString("hashId");
                                Joke joke=new Joke();
                                joke.setContent(content);
                                joke.setHashId(hashId);
                                joke.setUnixtime(unixtime);
                                joke.setUpdatetime(updatetime);
                                mJokes.add(joke);
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
        mAdapter=new JokeAdapter(mJokes,this);
        lv_joke.setAdapter(mAdapter);
    }
}
