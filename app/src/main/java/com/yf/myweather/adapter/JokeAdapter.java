package com.yf.myweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yf.myweather.R;
import com.yf.myweather.activity.JokeActivity;
import com.yf.myweather.model.Joke;

import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */

public class JokeAdapter extends BaseAdapter {
    private final List<Joke> jokes;
    private final Context context;

    public JokeAdapter(List<Joke> jokes, Context context) {
        this.jokes=jokes;
        this.context=context;
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
           view= LayoutInflater.from(context).inflate(R.layout.ui_joke_item,null);
        }
        TextView jk_content= (TextView) view.findViewById(R.id.jk_content);
        TextView jk_time= (TextView) view.findViewById(R.id.jk_time);
        jk_time.setText(jokes.get(i).getUpdatetime());
        jk_content.setText(jokes.get(i).getContent());
        return view;
    }
}
