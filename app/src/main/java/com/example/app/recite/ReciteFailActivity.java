package com.example.app.recite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;
/**
 * TODO:
 * author:sanhuzhen
 * email:sanhuzhen@foxmail.com
 * data:2024/02/07
 */
public class ReciteFailActivity extends AppCompatActivity {
    private TextView tv;
    private Button bt;
    String meaning = null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recite1);
        initView();
        Intent intent = getIntent();
        if(intent != null){
            meaning = intent.getStringExtra("word");
            Log.d("you", "initEvent: "+meaning);
        }
        tv.setText(meaning);
        initEvent();

    }
    private void initView(){
        bt = findViewById(R.id.bt_recite1);
        tv = findViewById(R.id.tv_recite1);
    }
    private void initEvent(){

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
