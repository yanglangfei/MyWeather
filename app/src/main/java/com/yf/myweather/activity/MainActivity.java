package com.yf.myweather.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yf.myweather.R;
import com.yf.myweather.adapter.MainAdapter;
import com.yf.myweather.fragment.ImageFragment;
import com.yf.myweather.fragment.WeatherActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */

public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {
    private RelativeLayout qxLay;
    private  RelativeLayout mtLay;
    private View currentButton;
    private ImageView mtIcon;
    private  ImageView qxIcon;
    private TextView qxLabe;
    private  TextView mtLabe;
    private MainAdapter adapter;
    private ViewPager mainVp;
    private List<Fragment> fragments=new ArrayList<>();
    private WeatherActivity   weather;
    private ImageFragment image;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main);
        initView();
        initFragments();
    }

    private void initFragments() {
        fragments.clear();

        weather=new WeatherActivity();
        Bundle build=new Bundle();
        build.putString("city",city);
        weather.setArguments(build);
        image=new ImageFragment();
        fragments.add(weather);
        fragments.add(image);
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        mtLay= (RelativeLayout) findViewById(R.id.mtLay);
        qxLay= (RelativeLayout) findViewById(R.id.qxLay);
        mtIcon= (ImageView) findViewById(R.id.mtIcon);
        qxIcon= (ImageView) findViewById(R.id.qxIcon);
        qxLabe= (TextView) findViewById(R.id.qxLabe);
        mtLabe= (TextView) findViewById(R.id.mtLabe);
        mainVp= (ViewPager) findViewById(R.id.mainVp);
        city=getIntent().getStringExtra("city");
        adapter=new MainAdapter(getSupportFragmentManager(),fragments);
        mainVp.setAdapter(adapter);
        qxLay.setOnClickListener(qxClick);
        mtLay.setOnClickListener(mtClick);
        qxLay.performClick();

        mainVp.setOnPageChangeListener(this);

    }


    private View.OnClickListener qxClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setChange(0);
            setButton(view);
        }
    };


    private View.OnClickListener mtClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setChange(1);
            setButton(view);
        }
    };

    private void setButton(View v) {
        // 将上个控件设置为可点击
        if (currentButton != null && currentButton.getId() != v.getId()) {
            currentButton.setEnabled(true);
        }
        // 当前按钮设置为不可点击，
        v.setEnabled(false);
        currentButton = v;
    }


    public void setChange(int change) {
        if(change==0){
            qxIcon.setImageResource(R.drawable.qx);
            mtIcon.setImageResource(R.drawable.mt_no);
            qxLabe.setTextColor(Color.BLUE);
            mtLabe.setTextColor(Color.BLACK);
            mainVp.setCurrentItem(0);
        }else {
            qxIcon.setImageResource(R.drawable.qx_no);
            mtIcon.setImageResource(R.drawable.mt);
            mtLabe.setTextColor(Color.BLUE);
            qxLabe.setTextColor(Color.BLACK);
            mainVp.setCurrentItem(1);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position==0){
            setChange(0);
            setButton(qxLay);
        }else {
            setChange(1);
            setButton(mtLay);
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
