package com.yf.myweather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.yf.myweather.R;
import com.yf.myweather.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2016/10/18.
 */

public class XingZuoActivity extends Activity implements View.OnClickListener {
    private EditText et_day, et_month;
    private Button btn_s;
    private LinearLayout lay_res;
    private TextView xz_name, xz_date, xz_all, xz_color, xz_heath, xz_love, xz_money, xz_number, xz_f, xz_work, xz_summ;
    private String getXz = "http://web.juhe.cn:8080/constellation/getAll";
    private ImageView iv_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_xz);
        initView();
    }

    private void initView() {
        iv_finish = (ImageView) findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(this);
        et_month = (EditText) findViewById(R.id.et_month);
        et_day = (EditText) findViewById(R.id.et_day);
        btn_s = (Button) findViewById(R.id.btn_s);
        xz_name = (TextView) findViewById(R.id.xz_name);
        xz_date = (TextView) findViewById(R.id.xz_date);
        xz_all = (TextView) findViewById(R.id.xz_zh);
        xz_color = (TextView) findViewById(R.id.xz_color);
        xz_love = (TextView) findViewById(R.id.xz_love);
        xz_heath = (TextView) findViewById(R.id.xz_heath);
        xz_money = (TextView) findViewById(R.id.xz_money);
        xz_f = (TextView) findViewById(R.id.xz_friend);
        xz_number = (TextView) findViewById(R.id.xz_number);
        xz_work = (TextView) findViewById(R.id.xz_work);
        xz_summ = (TextView) findViewById(R.id.xz_summ);
        lay_res = (LinearLayout) findViewById(R.id.lay_res);
        lay_res.setVisibility(View.GONE);
        btn_s.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_finish:
                this.finish();
                break;
            case R.id.btn_s:
                String month = et_month.getText().toString();
                String day = et_day.getText().toString();
                if (month.length() <= 0 || day.length() <= 0) {
                    Toast.makeText(this, "请输入出生日期", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!StringUtil.isInteger(month) || !StringUtil.isInteger(day)) {
                    Toast.makeText(this, "请正确输入出生日期", Toast.LENGTH_SHORT).show();
                    return;
                }
                int mon = Integer.parseInt(month);
                int d = Integer.parseInt(day);
                String xz = StringUtil.getConstellation(mon, d);
                getTodayData(xz);
                // getThisWeekData(xz);
                break;
        }
    }

    private void getThisWeekData(String xz) {
        RequestParams param = new RequestParams(getXz);
        param.addParameter("consName", xz);
        param.addParameter("type", "week");
        param.addParameter("key", "3e040a722d03eecd83da14c86ea86f44");
        x.http().get(param, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    try {
                        JSONObject object = new JSONObject(result);
                        int error_code = object.optInt("error_code");
                        if (error_code == 0) {
                            String name = object.optString("name");
                            String date = object.optString("date");
                            String job = object.optString("job");
                            String health = object.optString("health");
                            String love = object.optString("love");
                            String money = object.optString("money");
                            String work = object.optString("work");


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

    private void getTodayData(String xz) {
        RequestParams param = new RequestParams(getXz);
        param.addParameter("consName", xz);
        param.addParameter("type", "today");
        param.addParameter("key", "3e040a722d03eecd83da14c86ea86f44");
        x.http().get(param, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    try {
                        JSONObject object = new JSONObject(result);
                        int error_code = object.optInt("error_code");
                        if (error_code == 0) {
                            String name = object.optString("name");
                            String datetime = object.optString("datetime");
                            String all = object.optString("all");
                            String color = object.optString("color");
                            String health = object.optString("health");
                            String love = object.optString("love");
                            String money = object.optString("money");
                            String number = object.optString("number");
                            String QFriend = object.optString("QFriend");
                            String summary = object.optString("summary");
                            String work = object.optString("work");
                            xz_name.setText(name);
                            xz_all.setText("综合指数  " + all);
                            xz_date.setText(datetime);
                            xz_color.setText("幸运色  " + color);
                            xz_heath.setText("健康指数  " + health);
                            xz_love.setText("爱情指数  " + love);
                            xz_f.setText("速配星座  " + QFriend);
                            xz_money.setText("财运指数  " + money);
                            xz_number.setText("幸运数字  " + number);
                            xz_work.setText("工作指数  " + work);
                            xz_summ.setText(summary);
                            lay_res.setVisibility(View.VISIBLE);
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
}
