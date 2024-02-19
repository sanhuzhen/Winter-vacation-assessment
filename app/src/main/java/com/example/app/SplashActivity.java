package com.example.app;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

/**
 * TODO:Splash启动页面
 * author:sanhuzhen
 * email:sanhuzhen@foxmail.com
 * data:2024/01/25
 */

public class SplashActivity extends AppCompatActivity {
    private ImageView imageView;
    private final int SPLASH = 2000;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = findViewById(R.id.viewimageview);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, EnglishViewActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH);
    }
}
