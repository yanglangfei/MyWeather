package com.yf.myweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yf.myweather.R;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/10/27.
 */

public class ActionCodeActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private Button codeNext;
    private ImageView iv_finish;
    private TextView markPhone;
    private EditText actionCode;
    private String phone;
    private Button reSend;
    private Handler mHandle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                Toast.makeText(ActionCodeActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
            }else if(msg.what==2){
                Toast.makeText(ActionCodeActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
            }else if(msg.what==3){
                Toast.makeText(ActionCodeActivity.this, "手机验证失败", Toast.LENGTH_SHORT).show();
            }else {
               String errMsg= (String) msg.obj;
                Toast.makeText(ActionCodeActivity.this,errMsg, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private CountDownTimer mCountDownTimer = new CountDownTimer(1000 * 60, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            reSend.setText("重新发送（" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            reSend.setText("重新发送");
            reSend.setBackgroundColor(Color.parseColor("#1EB9F2"));
            reSend.setTextColor(Color.WHITE);
            reSend.setEnabled(true);
        }
    };
    private EventHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_action);
        initView();
    }

    private void initView() {
        codeNext = (Button) findViewById(R.id.codeNext);
        iv_finish = (ImageView) findViewById(R.id.iv_finish);
        markPhone = (TextView) findViewById(R.id.markPhone);
        actionCode = (EditText) findViewById(R.id.actionCode);
        reSend = (Button) findViewById(R.id.ReSend);
        phone = getIntent().getStringExtra("phone");
        markPhone.setText("+086-" + phone);
        codeNext.setEnabled(false);
        actionCode.addTextChangedListener(this);
        reSend.setOnClickListener(this);
        iv_finish.setOnClickListener(this);
        codeNext.setOnClickListener(this);
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
                        if (result == -1) {
                            Intent intent = new Intent(ActionCodeActivity.this, ReginInfo.class);
                            intent.putExtra("phone", phone);
                            startActivity(intent);
                            ActionCodeActivity.this.finish();
                        }else {
                            mHandle.sendEmptyMessage(3);
                        }
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
        switch (view.getId()) {
            case R.id.codeNext:
                String code = actionCode.getText().toString();
                //TODO 验证手机号码
               SMSSDK.submitVerificationCode("86", phone, code);
                break;
            case R.id.iv_finish:
                this.finish();
                break;
            case R.id.ReSend:
                mCountDownTimer.start();
                reSend.setEnabled(false);
                reSend.setBackgroundColor(Color.parseColor("#E9EBEC"));
                reSend.setTextColor(Color.parseColor("#BBBBBB"));
                //TODO 发送验证码
                SMSSDK.getVerificationCode("86", phone, new OnSendMessageHandler() {
                    @Override
                    public boolean onSendMessage(String s, String s1) {
                        return false;
                    }
                });

                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
        if (sequence.length() >= 4) {
            codeNext.setEnabled(true);
            codeNext.setBackgroundColor(Color.parseColor("#1EB9F2"));
            codeNext.setTextColor(Color.WHITE);
        } else {
            codeNext.setEnabled(false);
            codeNext.setBackgroundColor(Color.parseColor("#E9EBEC"));
            codeNext.setTextColor(Color.parseColor("#BBBBBB"));
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
