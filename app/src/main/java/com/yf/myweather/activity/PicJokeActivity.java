package com.yf.myweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.yf.myweather.R;
import com.yf.myweather.adapter.PicAdapter;
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

public class PicJokeActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv_joke;
    private ProgressBar pb;
    private PicAdapter mAdapter;
    private  String getPicJoke="http://japi.juhe.cn/joke/img/text.from";
    private List<PicJoke> mPicJokes=new ArrayList<>();
    private  ImageView iv_finish;
    private ImageView sharIv;
    private  int page=1;
    private View footer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_joke);
        initView();
        initData(1);
    }

    private void initData(int page) {
        RequestParams param=new RequestParams(getPicJoke);
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
                if(result!=null) {
                    pb.setVisibility(View.GONE);
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
        pb= (ProgressBar) findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
        footer= LayoutInflater.from(this).inflate(R.layout.ui_footer,null);
        sharIv= (ImageView) findViewById(R.id.share_iv);
        sharIv.setVisibility(View.GONE);
        lv_joke= (ListView) findViewById(R.id.lv_joke);
        lv_joke.addFooterView(footer);
        mAdapter=new PicAdapter(mPicJokes,this);
        iv_finish= (ImageView) findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(this);
        lv_joke.setAdapter(mAdapter);
        lv_joke.removeFooterView(footer);
        lv_joke.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View view1, int i, long l) {
                Intent intent=new Intent();
                intent.setClass(PicJokeActivity.this,ImageDetail.class);
                intent.putExtra("image_path",mPicJokes.get(i).getUrl());
                PicJokeActivity.this.startActivity(intent);
            }
        });
        lv_joke.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int i) {
                switch (i){
                    case SCROLL_STATE_IDLE:
                        if (view.getLastVisiblePosition() == view.getCount() - 1) {
                            int p=++page;
                            initData(p);
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
