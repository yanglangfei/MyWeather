package com.yf.myweather.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yf.myweather.R;
import com.yf.myweather.activity.Login;
import com.yf.myweather.model.User;
import com.yf.myweather.utils.StoreUtils;
import com.yf.myweather.view.CircleImageView;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/10/27.
 */

public class MyFragment extends Fragment implements View.OnClickListener {
    private  View mView;
    private CircleImageView userLogo;
    private TextView locationLabe;
    private  int uId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView= inflater.inflate(R.layout.ui_my_fg,container,false);
        initView();
        uId=StoreUtils.getUid(getActivity());
        if(uId>0){
            initUserData();
        }
        return mView;
    }

    private void initUserData() {
        final BmobQuery<User> user=new BmobQuery<>();
        user.addWhereEqualTo("id",uId);
        user.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
             if(e==null&&list!=null){
                if(list.size()>0){
                   String name= list.get(0).getUserName();
                    Log.i("111","name:"+name);
                }
             }
            }
        });

    }

    private void initView() {
        userLogo= (CircleImageView) mView.findViewById(R.id.userLogo);
        locationLabe= (TextView) mView.findViewById(R.id.locationLabe);
        locationLabe.setText(StoreUtils.getAddress(getActivity()));
        userLogo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.userLogo:
                if(uId>0){
                    //个人资料修改
                }else {
                    //登录
                    startActivity(new Intent(getActivity(), Login.class));
                }
                break;
        }

    }
}
