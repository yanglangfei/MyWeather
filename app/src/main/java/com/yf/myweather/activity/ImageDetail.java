package com.yf.myweather.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yf.myweather.R;
import com.yf.myweather.adapter.MarkAdapter;
import com.yf.myweather.model.Remark;
import com.yf.myweather.model.User;
import com.yf.myweather.utils.StoreUtils;
import com.yf.myweather.view.TestListView;
import com.yf.myweather.view.ZoomImageView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/10/21.
 */

public class ImageDetail extends BaseActivity implements View.OnClickListener {
    private ImageView iv_zoom;
    private  ImageView iv_finish;
    private TextView tvTime;
    private TestListView marLv;
    private  TextView tvUpLoder;
    private MarkAdapter mAdapter;
    private Button btSub;
    private EditText markEt;
    private  String id;
    private  String uId;
    private  TextView MArk;
    private List<Remark> mRemarks=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.ui_iv_detail);
        initView();
        initData();
    }

    private void initData() {
        BmobQuery<Remark> query=new BmobQuery<>();
        query.addWhereEqualTo("type",1);
        query.addWhereEqualTo("fkId",id);
        query.findObjects(new FindListener<Remark>() {
            @Override
            public void done(List<Remark> list, BmobException e) {
                  mAdapter.setRemarks(list);
                mAdapter.notifyDataSetChanged();
                MArk.setText("共"+list.size()+"条评论");
            }
        });
    }

    private void initView() {
        iv_zoom= (ImageView) findViewById(R.id.iv_zoom);
        iv_finish= (ImageView) findViewById(R.id.iv_finish);
        tvTime= (TextView) findViewById(R.id.tvTime);
        marLv= (TestListView) findViewById(R.id.marLv);
        tvUpLoder= (TextView) findViewById(R.id.tvUpLoder);
        markEt= (EditText) findViewById(R.id.markEt);
        btSub= (Button) findViewById(R.id.btSub);
        MArk= (TextView) findViewById(R.id.MArk);
        uId= StoreUtils.getUid(this);
        btSub.setOnClickListener(this);
        iv_finish.setOnClickListener(this);
        mAdapter=new MarkAdapter(this,mRemarks);
        marLv.setAdapter(mAdapter);
        id=getIntent().getStringExtra("id");
        String imagePath = getIntent().getStringExtra("image_path");
        String time=getIntent().getStringExtra("time");
        String uName=getIntent().getStringExtra("uName");
        tvUpLoder.setText(uName.trim().length()>0 ?"上传者:"+uName : "上传者:未知");
        tvTime.setText(time.length()>0 ? "上传时间:"+time :"上传时间:"+"未知");
        if(imagePath.endsWith("gif")){
            Glide.with(this).load(imagePath).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_zoom);
        }else {
            Glide.with(this).load(imagePath).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_zoom);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btSub:
                final String mark=markEt.getText().toString();
                if(mark.trim().length()<=0){
                    Toast.makeText(this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(uId.trim().length()>0){
                    BmobQuery<User> userBmobQuery=new BmobQuery<>();
                    userBmobQuery.addWhereEqualTo("objectId",uId);
                    userBmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                           if(e==null&&list.size()>0){
                                User u=list.get(0);
                                submitMark(u,mark);
                           }else {
                               Toast.makeText(ImageDetail.this, "提交评论失败", Toast.LENGTH_SHORT).show();
                           }
                        }
                    });
                }
                break;
            case R.id.iv_finish:
                this.finish();
                break;
        }
    }

    private void submitMark(User u,String mark) {
        Remark remark=new Remark();
        remark.setuId(uId);
        remark.setBody(mark);
        remark.setType(1);
        remark.setuName(u.getUserName());
        remark.setFkId(id);
        remark.setLogo(u.getFace());
        remark.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    initData();
                    Toast.makeText(ImageDetail.this, "评论提交成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ImageDetail.this, "提交评论失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
