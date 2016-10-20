package com.yf.myweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yf.myweather.R;
import com.yf.myweather.activity.PicJokeActivity;
import com.yf.myweather.model.PicJoke;

import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */

public class PicAdapter extends BaseAdapter {
    private final List<PicJoke> jokes;
    private final Context context;

    public PicAdapter(List<PicJoke> jokes, Context context) {
        this.jokes = jokes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return jokes.size();
    }

    @Override
    public Object getItem(int i) {
        return jokes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup group) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.ui_pic_item,null);
        }
        ImageView jk_pic= (ImageView) view.findViewById(R.id.jk_pic);
        TextView jk_remark= (TextView) view.findViewById(R.id.jk_remark);
        jk_remark.setText(jokes.get(i).getContent());
        String url = jokes.get(i).getUrl();
        if(url.endsWith("gif")){
            Glide.with(context).load(url)
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .into(jk_pic);
        }else {
            Glide.with(context).load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(jk_pic);
        }

        return view;
    }
}
