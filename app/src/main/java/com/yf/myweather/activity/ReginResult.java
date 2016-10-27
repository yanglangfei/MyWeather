package com.yf.myweather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yf.myweather.R;
import com.yf.myweather.model.User;
import com.yf.myweather.utils.StringUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2016/10/27.
 */

public class ReginResult extends Activity implements View.OnClickListener {
    private Button LoginTo;
    private TextView accountCreate;
    private String accountObj;
    private  String phone;
    private  ImageView iv_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_regin_res);
        initView();
    }

    private void initView() {
        iv_finish= (ImageView) findViewById(R.id.iv_finish);
        LoginTo= (Button) findViewById(R.id.LoginTo);
        accountCreate= (TextView) findViewById(R.id.accountCreate);
        phone=getIntent().getStringExtra("phone");
        iv_finish.setOnClickListener(this);
        LoginTo.setOnClickListener(this);
        createAccount();
    }

    private void createAccount() {
        BmobQuery<User> user=new BmobQuery<>();
        user.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
               if(e==null){
                   if(list!=null){
                       accountObj=StringUtil.createAccount(list,9);
                       LoginTo.setText(accountObj);
                       accountCreate.setText("正在获取账号");
                       createUser(accountObj);
                   }else {
                       Toast.makeText(ReginResult.this, "null,,,", Toast.LENGTH_SHORT).show();
                   }
               }else {
                   Toast.makeText(ReginResult.this, "账号获取失败1:"+e.getErrorCode(), Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    private void createUser(final String accountObj) {
        BmobQuery<User> userBmobQuery=new BmobQuery<>();
        userBmobQuery.addWhereEqualTo("telPhone",phone);
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
               if(e==null&&list.size()>0){
                  String id=list.get(0).getObjectId();
                   User user=new User();
                   user.setAccount(accountObj);
                   user.update(id, new UpdateListener() {
                       @Override
                       public void done(BmobException e) {
                           if(e==null){
                               accountCreate.setText(accountObj);
                               Toast.makeText(ReginResult.this, "账号获取成功", Toast.LENGTH_SHORT).show();
                           }else {
                               Toast.makeText(ReginResult.this, "账号获取失败2:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       }
                   });

               }else {
                   Toast.makeText(ReginResult.this, "账号获取失败3:"+e.getMessage(), Toast.LENGTH_SHORT).show();
               }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.LoginTo:
                this.finish();
                break;
            case R.id.iv_finish:
                this.finish();
                break;
        }
    }
}
