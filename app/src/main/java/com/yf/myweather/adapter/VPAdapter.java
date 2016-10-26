package com.yf.myweather.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */

public class VPAdapter extends PagerAdapter {
    private final List<ImageView> views;
    private final Context context;

    public VPAdapter(List<ImageView> views, Context context) {
       this.views=views;
        this.context=context;

    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv=views.get(position);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView iv=views.get(position);
        container.removeView(iv);
    }
}
