package com.example.app;

import com.example.app.net.Net;
import com.example.app.recite.ReciteActivity;
import com.example.app.translation.TranslationActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONObject;

/**
 * TODO:
 * author:sanhuzhen
 * email:sanhuzhen@foxmail.com
 * data:2024/01/30
 */

public class EnglishViewActivity extends AppCompatActivity {
    private Button button;
    private TextView tv2;
    private Button translationButton;
    private Button reciteButton;
    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String strData = (String) msg.obj;
                parseJsonDataAndShow(strData);
                Log.d("you", "------" + strData);
            }
        }
    };
    String ob = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_englishview);
        initView();
        initEvent();
    }

    private void initEvent() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ob = Net.doGet("https://api.oioweb.cn/api/common/OneDayEnglish");
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = ob;
                        mHandler.sendMessage(msg);
                    }
                }).start();
            }
        });

        translationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context TranslationActivity = new TranslationActivity();
                JumpView(TranslationActivity);
//                Intent intent = new Intent(EnglishViewActivity.this,TranslationActivity.class);
//                startActivity(intent);
            }
        });
        reciteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context ReciteActivity = new ReciteActivity();
                JumpView(ReciteActivity);
            }
        });

    }//绑定控件的事件

    private void JumpView(Context context) {
        Intent intent = new Intent(this, context.getClass());
        startActivity(intent);
    }//封装一个跳转方法


    private void parseJsonDataAndShow(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            String content = jsonObject.optString("result", "I am shaBi");
            JSONObject json = new JSONObject(content);
            String tent = json.optString("content", "i");
            String note = json.optString("note", "i");
            String dateline = json.optString("dateline", "i");
            //显示json数据
            tv2.setText(tent + "\n" + note + "\n" + dateline);
            Log.d("you", "----" + tent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//解析Json数据


    private void initView() {
        translationButton = findViewById(R.id.translationbutton);
        reciteButton = findViewById(R.id.recitebutton);
        button = findViewById(R.id.button);
        tv2 = findViewById(R.id.tv_endlishview2);
    }//绑定UI控件
}
