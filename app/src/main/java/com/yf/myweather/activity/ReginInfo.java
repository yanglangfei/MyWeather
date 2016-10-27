package com.yf.myweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.yf.myweather.R;
import com.yf.myweather.model.User;
import com.yf.myweather.utils.StoreUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/10/27.
 */

public class ReginInfo extends Activity implements View.OnClickListener {
    private Button reginTo;
    private  ImageView iv_finish;
    private EditText passwordInfo;
    private  EditText accountInfo;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_regin_info);
        initView();
    }

    private void initView() {
        iv_finish= (ImageView) findViewById(R.id.iv_finish);
        reginTo= (Button) findViewById(R.id.reginTo);
        passwordInfo= (EditText) findViewById(R.id.passwordInfo);
        accountInfo= (EditText) findViewById(R.id.accountInfo);
        phone=getIntent().getStringExtra("phone");
        reginTo.setOnClickListener(this);
        iv_finish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reginTo:
                final String pwdStr = passwordInfo.getText().toString();
                String accountStr = accountInfo.getText().toString();
                if(accountStr==null||accountStr.trim()==""){
                    Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pwdStr==null||pwdStr.trim()==""){
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //提交注册信息
                User user=new User();
                user.setAddress(StoreUtils.getAddress(this));
                user.setPassword(pwdStr);
                user.setUserName(accountStr);
                user.setTelPhone(phone);
                user.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            Toast.makeText(ReginInfo.this, "注册成功", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(ReginInfo.this,ReginResult.class);
                            intent.putExtra("phone",phone);
                            startActivity(intent);
                            ReginInfo.this.finish();
                        }else {
                            Toast.makeText(ReginInfo.this, "注册失败:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.iv_finish:
                this.finish();
                break;
        }
    }
}
