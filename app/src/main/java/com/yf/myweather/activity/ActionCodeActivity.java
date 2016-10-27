package com.yf.myweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yf.myweather.R;

import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/10/27.
 */

public class ActionCodeActivity extends Activity implements View.OnClickListener, TextWatcher {
    private Button codeNext;
    private ImageView iv_finish;
    private TextView markPhone;
    private EditText actionCode;
    private  String phone;
    private  Button reSend;
    private CountDownTimer mCountDownTimer=new CountDownTimer(1000*60,1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            reSend.setText("重新发送（" + millisUntilFinished / 1000+"s)");
        }

        @Override
        public void onFinish() {
            reSend.setText("重新发送");
            reSend.setBackgroundColor(Color.parseColor("#1EB9F2"));
            reSend.setTextColor(Color.WHITE);
            reSend.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_action);
        initView();
    }

    private void initView() {
        codeNext= (Button) findViewById(R.id.codeNext);
        iv_finish= (ImageView) findViewById(R.id.iv_finish);
        markPhone= (TextView) findViewById(R.id.markPhone);
        actionCode= (EditText) findViewById(R.id.actionCode);
        reSend= (Button) findViewById(R.id.ReSend);
        phone=getIntent().getStringExtra("phone");
        markPhone.setText("+086-"+phone);
        codeNext.setEnabled(false);
        actionCode.addTextChangedListener(this);
        reSend.setOnClickListener(this);
        iv_finish.setOnClickListener(this);
        codeNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.codeNext:
                String code=actionCode.getText().toString();
                //TODO 验证手机号码
                SMSSDK.submitVerificationCode("cn",phone,code);
                Intent intent=new Intent(this,ReginInfo.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
                this.finish();
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
                SMSSDK.getVerificationCode("cn", phone, new OnSendMessageHandler() {
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
        if(sequence.length()>5){
            codeNext.setEnabled(true);
            codeNext.setBackgroundColor(Color.parseColor("#1EB9F2"));
            codeNext.setTextColor(Color.WHITE);
        }else {
            codeNext.setEnabled(false);
            codeNext.setBackgroundColor(Color.parseColor("#E9EBEC"));
            codeNext.setTextColor(Color.parseColor("#BBBBBB"));
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
