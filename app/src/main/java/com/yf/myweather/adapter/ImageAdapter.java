package com.yf.myweather.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yf.myweather.R;
import com.yf.myweather.utils.BitMapUtils;

/**
 * Created by Administrator on 2016/10/26.
 */

public class ImageAdapter extends BaseAdapter {
    private final int[] pic;
    private final Context context;
    private final int width;

    public ImageAdapter(Context context, int[] pic, int width, int height) {
        this.pic=pic;
        this.context=context;
        this.width=width;
    }

    @Override
    public int getCount() {
        return pic.length;
    }

    @Override
    public Object getItem(int i) {
        return pic[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup group) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.ui_image_inte,null);
        }
        ImageView iv_ta= (ImageView) view.findViewById(R.id.iv_ta);
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(width/2, width/2);
        Bitmap bm=BitmapFactory.decodeResource(context.getResources(),R.drawable.dog);
        iv_ta.setLayoutParams(lp);
        iv_ta.setImageBitmap(BitMapUtils.centerSquareScaleBitmap(bm,width/2));
        return view;
    }
}
