package com.yf.myweather.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.yf.myweather.R;
import com.yf.myweather.activity.JokeActivity;
import com.yf.myweather.model.Joke;

import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */

public class JokeAdapter extends BaseAdapter implements View.OnClickListener {
    private final List<Joke> jokes;
    private final Context context;
    private SynthesizerListener mSynListener = new SynthesizerListener(){
        public void onCompleted(SpeechError error) {
            Toast.makeText(context, "end...", Toast.LENGTH_SHORT).show();
        }
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {}
        //开始播放
        public void onSpeakBegin() {
            Toast.makeText(context, "start...", Toast.LENGTH_SHORT).show();
        }
        //暂停播放
        public void onSpeakPaused() {}
        public void onSpeakProgress(int percent, int beginPos, int endPos) {}
        public void onSpeakResumed() {}
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {}
    };

    public JokeAdapter(List<Joke> jokes, Context context) {
        this.jokes=jokes;
        this.context=context;
    }

    @Override
    public int getCount() {
        return jokes.size();
    }

    @Override
    public Object getItem(int i) {
        return jokes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup group) {
        if(view==null){
           view= LayoutInflater.from(context).inflate(R.layout.ui_joke_item,null);
        }
        TextView jk_content= (TextView) view.findViewById(R.id.jk_content);
        TextView jk_time= (TextView) view.findViewById(R.id.jk_time);
        ImageView iv_share= (ImageView) view.findViewById(R.id.iv_share);
        ImageView iv_voice= (ImageView) view.findViewById(R.id.iv_voice);

        jk_time.setText(jokes.get(i).getUpdatetime());
        jk_content.setText(jokes.get(i).getContent());
        iv_share.setTag(i);
        iv_voice.setTag(i);
        iv_voice.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int index= (int) view.getTag();
        switch (view.getId()){
            case  R.id.iv_share:
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TITLE, "小强讲段子"); // 分享的主题
                intent.putExtra(Intent.EXTRA_TEXT,jokes.get(index).getContent()); // 分享的内容
                context.startActivity(Intent.createChooser(intent, "请选择"));
                break;
            case R.id.iv_voice:
                SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer(context, null);
                mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan"); //设置发音人
                mTts.setParameter(SpeechConstant.SPEED,  "50");//设置语速
                mTts.setParameter(SpeechConstant.VOLUME,  "80");//设置音量，范围 0~100
                mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
                mTts.startSpeaking(jokes.get(index).getContent(), mSynListener);
                break;
        }

    }
}
