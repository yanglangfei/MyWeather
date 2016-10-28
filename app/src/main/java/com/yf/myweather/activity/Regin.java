package com.yf.myweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yf.myweather.R;
import com.yf.myweather.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/10/27.
 */

public class Regin extends BaseActivity implements View.OnClickListener, TextWatcher {
    private Button phoneNext;
    private EditText etPhone;
    private ImageView iv_finish;
    private EventHandler handler;
    private Handler mHandle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                Toast.makeText(Regin.this, "发送成功", Toast.LENGTH_SHORT).show();
            }else if(msg.what==2){
                Toast.makeText(Regin.this, "发送失败", Toast.LENGTH_SHORT).show();
            }else if(msg.what==3){
                Toast.makeText(Regin.this, "手机验证失败", Toast.LENGTH_SHORT).show();
            }else {
                String errMsg= (String) msg.obj;
                Toast.makeText(Regin.this,errMsg, Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_regin);
        initView();
    }

    private void initView() {
        phoneNext= (Button) findViewById(R.id.phoneNext);
        etPhone= (EditText) findViewById(R.id.etPhone);
        iv_finish= (ImageView) findViewById(R.id.iv_finish);
        phoneNext.setEnabled(false);
        etPhone.addTextChangedListener(this);
        iv_finish.setOnClickListener(this);
        phoneNext.setOnClickListener(this);
        handler = new EventHandler() {
            @Override
            public void onRegister() {
                super.onRegister();
            }

            @Override
            public void beforeEvent(int i, Object o) {
                super.beforeEvent(i, o);
            }

            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        if(result==-1){
                           mHandle.sendEmptyMessage(1);
                        }else {
                            mHandle.sendEmptyMessage(2);
                        }
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    Throwable d= (Throwable) data;
                    if(d!=null){
                        try {
                            JSONObject object=new JSONObject(d.getMessage());
                            String errMsg=object.optString("detail");
                            mHandle.obtainMessage(5,errMsg).sendToTarget();;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onUnregister() {
                super.onUnregister();
            }
        };
        SMSSDK.registerEventHandler(handler);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SMSSDK.unregisterEventHandler(handler);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.phoneNext:
                final String phone=etPhone.getText().toString();
                //判断是否注册
                BmobQuery<User> userBmobQuery=new BmobQuery<>();
                userBmobQuery.addWhereEqualTo("telPhone",phone);
                userBmobQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                       if(e==null){
                           if(list.size()>0){
                               Toast.makeText(Regin.this, "手机号已经注册", Toast.LENGTH_SHORT).show();
                           }else {
                              handleRegin(phone);
                           }
                       }else {
                           handleRegin(phone);
                       }
                    }
                });
                break;
            case R.id.iv_finish:
                this.finish();
                break;
        }
    }

    private void handleRegin(String phone) {
        //发送验证码
        SMSSDK.getVerificationCode("86", phone, new OnSendMessageHandler() {
            @Override
            public boolean onSendMessage(String s, String s1) {
                return false;
            }
        });

        Intent intent=new Intent(this,ActionCodeActivity.class);
        intent.putExtra("phone",phone);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
       if(sequence.length()>=11){
           phoneNext.setEnabled(true);
           phoneNext.setBackgroundColor(Color.parseColor("#1EB9F2"));
           phoneNext.setTextColor(Color.WHITE);
       }else {
           phoneNext.setEnabled(false);
           phoneNext.setBackgroundColor(Color.parseColor("#E9EBEC"));
           phoneNext.setTextColor(Color.parseColor("#BBBBBB"));
       }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
