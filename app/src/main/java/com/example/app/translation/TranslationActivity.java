package com.example.app.translation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.net.Net;
import com.example.app.R;

import org.json.JSONObject;

/**
 * TODO:实现翻译功能
 * author:sanhuzhen
 * email:sanhuzhen@foxmail.com
 * data:2024/01/30
 */

public class TranslationActivity extends AppCompatActivity {
    private Spinner enChangeToZn;
    private EditText etTranslation;
    private TextView tvTranslation;
    private Button btTranslation;
    private final Handler mHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                String strData = (String) msg.obj;
                pareJson(strData);

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
        initView();
        initEvent();
    }

    private void pareJson(String JsonStr){
        try{
            JSONObject jsonObject = new JSONObject(JsonStr);
            String data = jsonObject.optString("data","i");
            JSONObject mData = new JSONObject(data);
            String result = mData.optString("result","i");
            tvTranslation.setText(result);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    private void initEvent(){


        btTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String word = etTranslation.getText().toString();
                        String meaning = null;
                        String spinner = enChangeToZn.getSelectedItem().toString();//获取spinner中的词
                        if(spinner.equals("汉翻英") ){
                            meaning = Net.doGet("https://api.gumengya.com/Api/Translate?format=json&text="+word+"&from=zh&to=en");
                        }
                        else if(spinner.equals("英翻汉")){
                            meaning = Net.doGet("https://api.gumengya.com/Api/Translate?format=json&text="+word+"&from=en&to=zh");
                        }
                        Message message = new Message();
                        message.what = 1;
                        message.obj = meaning;
                        mHandler.sendMessage(message);
                    }
                }).start();
            }
        });
    }//绑定事件
    private void initView(){
        etTranslation = findViewById(R.id.et_translation);
        tvTranslation = findViewById(R.id.tv_translation);
        btTranslation = findViewById(R.id.trButton);
        enChangeToZn = findViewById(R.id.enChangeToZh);
    }//绑定UI控件
}
