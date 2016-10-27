package com.yf.myweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yf.myweather.R;

import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/10/27.
 */

public class Regin extends Activity implements View.OnClickListener, TextWatcher {
    private Button phoneNext;
    private EditText etPhone;
    private ImageView iv_finish;

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.phoneNext:
                String phone=etPhone.getText().toString();
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
                break;
            case R.id.iv_finish:
                this.finish();
                break;
        }
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
