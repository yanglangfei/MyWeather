package com.yf.myweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.android.flexbox.FlexboxLayout;
import com.yf.myweather.R;
import com.yf.myweather.activity.BaseActivity;
import com.yf.myweather.activity.WeatherActivity;

/**
 * Created by Administrator on 2016/10/20.
 */

public class ChoseCityActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_finish;
    private EditText mEditText;
    private  ImageView share_iv;
    private Button mSearch;
    private FlexboxLayout lay_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_city);
        initView();
    }

    private void initView() {
        AMapLocationClient client=new AMapLocationClient(getApplicationContext());
        AMapLocationListener listener=new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation location) {
                if(location!=null&&location.getErrorCode()==0){
                    String adress=location.getAddress();
                    String city=location.getCity();
                    mEditText.setText(city);
                }
            }
        };
        client.setLocationListener(listener);
        AMapLocationClientOption option=new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        option.setOnceLocationLatest(true);
        client.setLocationOption(option);
        client.startLocation();

        share_iv= (ImageView) findViewById(R.id.share_iv);
        share_iv.setVisibility(View.GONE);

        lay_city= (FlexboxLayout) findViewById(R.id.lay_city);
        iv_finish= (ImageView) findViewById(R.id.iv_finish);
        mEditText= (EditText) findViewById(R.id.et_city);
        mSearch= (Button) findViewById(R.id.btn_s);
        mSearch.setOnClickListener(this);
        iv_finish.setOnClickListener(this);

        for (int i = 0; i < lay_city.getChildCount(); i++) {
            lay_city.getChildAt(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if(view instanceof  TextView&& ((view instanceof  Button)==false)){
           mEditText.setText(((TextView) view).getText().toString());
            return;
        }

        switch (view.getId()){
            case R.id.btn_s:
                String cityStr=mEditText.getText().toString();
                if(cityStr.length()<=0){
                    Toast.makeText(this, "请选择城市", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent(this,WeatherActivity.class);
                intent.putExtra("city",cityStr);
                startActivity(intent);
                break;
            case R.id.iv_finish:
                this.finish();
                break;
        }
    }
}
