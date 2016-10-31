package com.yf.myweather.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.yf.myweather.R;
import com.yf.myweather.activity.ImageDetail;
import com.yf.myweather.model.Remark;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/10/31.
 */

public class MarkAdapter extends BaseAdapter {
    private  Context context;
    private  List<Remark> remarks;

    public MarkAdapter(Context context, List<Remark> remarks) {
        this.context=context;
        this.remarks=remarks;
    }

    public void setRemarks(List<Remark> remarks) {
        this.remarks = remarks;
    }

    @Override
    public int getCount() {
        return remarks==null ? 0 : remarks.size();
    }

    @Override
    public Object getItem(int i) {
        return remarks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup group) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.ui_rmar_item,null);
        }
        TextView tvBody= (TextView) view.findViewById(R.id.tvBody);
        TextView tvTime= (TextView) view.findViewById(R.id.tvTime);
        TextView tvName= (TextView) view.findViewById(R.id.tvName);
        final ImageView ivTou= (ImageView) view.findViewById(R.id.ivTou);
        tvBody.setText(remarks.get(i).getBody());
        tvName.setText(remarks.get(i).getuName());
        tvTime.setText(remarks.get(i).getCreatedAt());
        BmobFile file=remarks.get(i).getLogo();
        if(file!=null&&file.getUrl().length()>0){
            Glide.with(context).load(remarks.get(i).getLogo().getUrl())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new BitmapImageViewTarget(ivTou){
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable drawable= RoundedBitmapDrawableFactory.create(context.getResources(),resource);
                            drawable.setCircular(true);
                            ivTou.setImageDrawable(drawable);
                        }
                    });
        }

        return view;
    }
}
