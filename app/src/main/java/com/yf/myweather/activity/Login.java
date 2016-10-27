package com.yf.myweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yf.myweather.R;
import com.yf.myweather.model.User;
import com.yf.myweather.utils.StoreUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/10/27.
 */

public class Login extends Activity implements View.OnClickListener {
    private TextView toRegin;
    private ImageView iv_finish;
    private EditText account;
    private  EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_login);
        initView();
    }

    private void initView() {
        toRegin= (TextView) findViewById(R.id.toRegin);
        toRegin.setOnClickListener(this);
        iv_finish= (ImageView) findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(this);
        account= (EditText) findViewById(R.id.account);
        password= (EditText) findViewById(R.id.password);
        login= (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toRegin:
                startActivity(new Intent(this,Regin.class));
                this.finish();
                break;
            case R.id.iv_finish:
                this.finish();
                break;
            case R.id.login:
                //登录账号
                String accountStr=account.getText().toString();
                final String pwdStr=password.getText().toString();
                if(accountStr==null&&accountStr.trim()==""){
                    Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(pwdStr==null&&pwdStr.trim()==""){
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                BmobQuery<User> eq1=new BmobQuery<>();
                eq1.addWhereEqualTo("account",accountStr);
                BmobQuery<User> eq2=new BmobQuery<>();
                eq2.addWhereEqualTo("telPhone",accountStr);
                List<BmobQuery<User>> queries = new ArrayList<BmobQuery<User>>();
                queries.add(eq1);
                queries.add(eq2);
                BmobQuery<User> mainQuery = new BmobQuery<User>();
                mainQuery.or(queries);
                mainQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if(e==null){
                            if(list==null||list.size()<=0){
                                Toast.makeText(Login.this, "用户不存在", Toast.LENGTH_SHORT).show();
                            }else  {
                                String pwd = list.get(0).getPassword();
                                if(pwdStr.equals(pwd)){
                                    int uId=list.get(0).getId();
                                    StoreUtils.saveUid(Login.this,uId);
                                    Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    Login.this.finish();
                                }else {
                                    Toast.makeText(Login.this, "密码错误", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else {
                            Toast.makeText(Login.this, "登录失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


                break;
        }
    }
}
