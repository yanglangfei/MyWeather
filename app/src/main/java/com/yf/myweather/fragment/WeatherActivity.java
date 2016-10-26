package com.yf.myweather.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yf.myweather.R;
import com.yf.myweather.activity.BaseActivity;
import com.yf.myweather.activity.JokeActivity;
import com.yf.myweather.activity.PicJokeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/10/18.
 */
public class WeatherActivity extends Fragment implements View.OnClickListener {
    private TextView tv_ad,tv_state,tv_wind,tv_temp,tv_wr,tv_remark,day1,day1_wea,nightWea,day2,day2_wea,nightWea2,day3,day3_wea,nightWea3;
    private RelativeLayout bg;
    private  String getWeather="http://op.juhe.cn/onebox/weather/query";
    private List<TextView> days=new ArrayList<>();
    private  List <TextView> dayWeath=new ArrayList<>();
    private  List<TextView> nightArray=new ArrayList<>();
    private  String hhSays[]={"趣图段子","精彩段子"};
    private ImageView iv_finish;
    private  ImageView share_iv;
    private  TextView todz;
    private  int i=0;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==100){
                if(i<=hhSays.length-1){
                    todz.setText(hhSays[i++]);
                    mHandler.sendEmptyMessageDelayed(100,1000*3);
                }else {
                    i=0;
                    mHandler.sendEmptyMessageDelayed(100,0);
                }
            }
        }
    };
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_main,container,false);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iv_finish= (ImageView) getActivity().findViewById(R.id.iv_finish);
        share_iv= (ImageView) getActivity().findViewById(R.id.share_iv);
        share_iv.setOnClickListener(this);
        iv_finish.setOnClickListener(this);
    }

    private void initView() {
        days.clear();
        dayWeath.clear();
        nightArray.clear();
        bg= (RelativeLayout) view.findViewById(R.id.bg);
        tv_ad= (TextView) view.findViewById(R.id.tv_ad);
        todz= (TextView) view.findViewById(R.id.todz);
        tv_state= (TextView) view.findViewById(R.id.tv_state);
        tv_wind= (TextView) view.findViewById(R.id.tv_wind);
        tv_temp= (TextView) view.findViewById(R.id.tv_temp);
        tv_wr= (TextView) view.findViewById(R.id.tv_wr);
        tv_remark= (TextView) view.findViewById(R.id.tv_remark);
        day1= (TextView) view.findViewById(R.id.day1);
        day1_wea= (TextView) view.findViewById(R.id.day1_wea);
        nightWea= (TextView) view.findViewById(R.id.nightWea1);
        day2= (TextView) view.findViewById(R.id.day2);
        day2_wea= (TextView) view.findViewById(R.id.day2_wea);
        nightWea2= (TextView) view.findViewById(R.id.nightWea2);
        day3= (TextView) view.findViewById(R.id.day3);
        day3_wea= (TextView) view.findViewById(R.id.day3_wea);
        nightWea3= (TextView) view.findViewById(R.id.nightWea3);
        todz.setOnClickListener(this);
        nightArray.add(nightWea);
        nightArray.add(nightWea2);
        nightArray.add(nightWea3);
        dayWeath.add(day1_wea);
        dayWeath.add(day2_wea);
        dayWeath.add(day3_wea);
        days.add(day1);
        days.add(day2);
        days.add(day3);
        String city = getArguments().getString("city");
        mHandler.sendEmptyMessage(100);
        initWeatherData(city);
    }


    private void initWeatherData(String city) {
        RequestParams param=new RequestParams(getWeather);
        param.addParameter("cityname",city);
        param.addParameter("key","26c63618167a8768db318e1c0a10c9d7");
        x.http().get(param, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                Log.i("111","res:"+result);
                if(result!=null){
                    try {
                        JSONObject object=new JSONObject(result);
                        int error_code=object.optInt("error_code");
                        if(error_code==0){
                            JSONObject resultObj=object.getJSONObject("result");
                            JSONObject data=resultObj.getJSONObject("data");
                            JSONObject realtime=data.getJSONObject("realtime");
                            JSONObject weather=realtime.getJSONObject("weather");
                            String city_name=realtime.optString("city_name");
                            JSONObject pm25=data.getJSONObject("pm25");
                            JSONObject pm25O=pm25.optJSONObject("pm25");
                            String  dese=pm25O.optString("des");
                            String quality=pm25O.optString("quality");
                            JSONObject wind=realtime.getJSONObject("wind");
                            JSONObject life=data.getJSONObject("life");
                            JSONObject leftInfo=life.getJSONObject("info");
                            String chuanyi=leftInfo.optString("chuanyi");
                            String ganmao=leftInfo.optString("ganmao");
                            String kongtiao=leftInfo.optString("kongtiao");
                            String xiche=leftInfo.optString("xiche");
                            String yundong=leftInfo.optString("yundong");
                            String ziwaixian=leftInfo.optString("ziwaixian");

                            String img=weather.optString("img");
                            String info=weather.optString("info");
                            String direct=wind.optString("direct");
                            String power=wind.optString("power");
                            String temperature=weather.optString("temperature");
                            setTodayBg(img);
                            tv_state.setText(info);
                            tv_wind.setText(direct+"  "+power);
                            tv_temp.setText(temperature+"度");
                            tv_wr.setText("污染指数  "+quality);
                            tv_remark.setText(dese);
                            tv_ad.setText(city_name);
                            JSONArray weatherData=data.getJSONArray("weather");
                            for (int i = 0; i < weatherData.length(); i++) {
                                JSONObject obj=weatherData.getJSONObject(i);
                                JSONObject infoObj=obj.getJSONObject("info");
                                JSONArray day=infoObj.getJSONArray("day");
                                JSONArray night=infoObj.getJSONArray("night");
                                String date=obj.optString("date");
                                days.get(i).setText(date);
                                dayWeath.get(i).setText("白天  "+day.toString().split(",")[1]);
                                nightArray.get(i).setText("晚上   "+night.toString().split(",")[1]);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setTodayBg(String img) {
        if(img.equals("0")){
            //晴朗
            bg.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.qing_bg));
        }else if(img.equals("1")){
            //多云
            bg.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.duoyun_bg));
        }else if(img.equals("2")){
            //阴天
            bg.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.yin_zuidiceng));
        }else  if(img.equals("3")||img.equals("4")||img.equals("5")||img.equals("6")||img.equals("7")||img.equals("8")){
            bg.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.yu_bg));
        }else if(img.equals("18")){
            //雾
            bg.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.wu_bg));
        }else  if(img.equals("14")||img.equals("15")||img.equals("16")){
            //雪
            bg.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.xue_bg));
        }else {
            bg.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.qing_bg));
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.todz:
                if(todz.getText().toString().equals("趣图段子")){
                    Intent intent=new Intent();
                    intent.setClass(getActivity(),PicJokeActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent();
                    intent.setClass(getActivity(),JokeActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.iv_finish:
                getActivity().finish();
                break;
            case R.id.share_iv:
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TITLE, "天气提醒"); // 分享的主题
                intent.putExtra(Intent.EXTRA_TEXT, "今日"+tv_ad.getText().toString()+"天气:"+tv_state.getText().toString()+",温度:"+tv_temp.getText().toString()); // 分享的内容
                startActivity(Intent.createChooser(intent, "请选择"));
                break;
        }

    }
}
