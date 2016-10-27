package com.yf.myweather.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

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

public class JokeActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv_joke;
    private JokeAdapter mAdapter;
    private List<Joke> mJokes=new ArrayList<>();
    private ImageView iv_finish;
    private  String getJoke="http://japi.juhe.cn/joke/content/text.from";
    private View footer;
    private ProgressBar pb;
    private  int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_joke);
        initView();
        initJokeData(1);

    }

    private void initJokeData(int page) {
        RequestParams param=new RequestParams(getJoke);
        param.addParameter("key","c4da5201e43eebf18ca5630e1390f525");
        param.addParameter("page",page);
        param.addParameter("pagesize",20);
        x.http().get(param, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                if(result!=null){
                    pb.setVisibility(View.GONE);
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
        pb= (ProgressBar) findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
        footer= LayoutInflater.from(this).inflate(R.layout.ui_footer,null);
        lv_joke= (ListView) findViewById(R.id.lv_joke);
        lv_joke.addFooterView(footer);
        mAdapter=new JokeAdapter(mJokes,this);
        iv_finish= (ImageView) findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(this);
        lv_joke.setAdapter(mAdapter);
        lv_joke.removeFooterView(footer);
        lv_joke.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int i) {
                switch (i){
                    case SCROLL_STATE_IDLE:
                        if (view.getLastVisiblePosition() == view.getCount() - 1) {
                            int p=++page;
                            initJokeData(p);
                        }
                        break;
                    case  SCROLL_STATE_FLING:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int i, int i1, int i2) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        this.finish();
    }
}
