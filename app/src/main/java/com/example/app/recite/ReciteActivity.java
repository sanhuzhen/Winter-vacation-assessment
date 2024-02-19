package com.example.app.recite;

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

import com.example.app.R;
import com.example.app.SplashActivity;
import com.example.app.net.Net;

import org.json.JSONObject;

import java.util.Random;

/**
 * TODO:
 * author:sanhuzhen
 * email:sanhuzhen@foxmail.com
 * data:2024/02/05
 */

public class ReciteActivity extends AppCompatActivity {
    private TextView tv;
    private Button bt1;
    private Button bt2;
    String meaning = null;
    private final Handler mHandler = new Handler(Looper.myLooper()){
      @Override
      public void handleMessage(@NonNull Message msg){
          super.handleMessage(msg);

          if(msg.what==1){
              String data = (String)msg.obj;
              meaning = pareJson(data);
              Log.d("you", "handleMessage: "+meaning);
          }
      }
    };
    private final String[] EnglishWord = {
            "magnetosphere",
            "locomotion",
            "navigate",
            "porcelain",
            "outset",
            "ecologist",
            "counseling",
            "parachute",
            "reflection",
            "complain",
            "crust",
            "herald",
            "regenerate",
            "horror film/movie",
            "gallantry",
            "verify",
            "urban",
            "bust",
            "mania",
            "exceed",
            "circuit",
            "aviation",
            "temperate",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recite);
        initView();
        initEvent();
    }

    private void initEvent(){
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int number = random.nextInt(EnglishWord.length);
                tv.setText(EnglishWord[number]);
                bt1.setText("认识");
            }
        });


        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String word = tv.getText().toString();
                        String json = Net.doGet("https://api.gumengya.com/Api/Translate?format=json&text="+word+"&from=en&to=zh");
                        Message msg = new Message();
                        msg.obj = json;
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                    }
                }).start();
                Intent intent = new Intent(ReciteActivity.this,ReciteFailActivity.class);
                intent.putExtra("word",meaning);
                startActivity(intent);

            }
        });
    }
    private void initView(){
        tv = findViewById(R.id.tv_recite);
        bt1 = findViewById(R.id.bt_recite);
        bt2 = findViewById(R.id.bt2_recite);
    }
    private String pareJson(String data){
        String result = null;
        try{
            JSONObject jsonObject = new JSONObject(data);
            String str = jsonObject.optString("data","i");
            JSONObject mData = new JSONObject(str);
            result = mData.optString("result","i");
            Log.d("you","-----"+result);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
