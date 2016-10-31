package com.yf.myweather.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yf.myweather.R;
import com.yf.myweather.activity.ImageDetail;
import com.yf.myweather.model.ImageFile;
import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */

public class ImageAdapter extends BaseAdapter implements View.OnClickListener {
    private  Context context;
    private  int width;
    private  List<ImageFile> mFiles;

    public ImageAdapter(Context context, List<ImageFile> mFiles, int width, int height) {
        this.mFiles=mFiles;
        this.context=context;
        this.width=width;
    }

    public void setFiles(List<ImageFile> files) {
        this.mFiles = files;
    }

    @Override
    public int getCount() {
        return mFiles.size();
    }

    @Override
    public Object getItem(int i) {
        return mFiles.get(i);
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
        TextView address= (TextView) view.findViewById(R.id.address);
        RelativeLayout itemLay= (RelativeLayout) view.findViewById(R.id.itemLay);
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(width/2, width/2);
        itemLay.setLayoutParams(lp);
        iv_ta.setTag(R.string.tagUrl,mFiles.get(i));
        address.setText(mFiles.get(i).getAddress());
        Glide.with(context).load(mFiles.get(i).getUrl().getUrl())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv_ta);
        iv_ta.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        ImageFile f=(ImageFile)view.getTag(R.string.tagUrl);
        Intent intent=new Intent(context, ImageDetail.class);
        intent.putExtra("image_path",f.getUrl().getUrl());
        intent.putExtra("uName",f.getuName());
        intent.putExtra("time",f.getCreatedAt());
        intent.putExtra("id",f.getObjectId());
        intent.putExtra("type",1);
        context.startActivity(intent);

    }
}
