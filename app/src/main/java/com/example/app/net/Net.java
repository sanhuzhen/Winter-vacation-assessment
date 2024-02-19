package com.example.app.net;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * TODO:将get方法封装
 * author:sanhuzhen
 * email:sanhuzhen@foxmail.com
 * data:2024/01/26
 */

public class Net {
    public static String doGet(String myurl) {
        HttpURLConnection mHttpURLConnection = null;
        BufferedReader read = null;
        String ob = null;
        try {
            URL url = new URL(myurl);
            mHttpURLConnection = (HttpURLConnection) url.openConnection();
            //设置请求参数
            mHttpURLConnection.setRequestMethod("GET");
            //设置连接超时时间s
            mHttpURLConnection.setConnectTimeout(15000);
            //设置读取超时时间
            mHttpURLConnection.setReadTimeout(15000);
            //添加请求头
            mHttpURLConnection.setRequestProperty("Connection", "keep-alive");
            //获取服务器返回的输入流
            InputStream inputStream = mHttpURLConnection.getInputStream();
            //读取输入流s
            read = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder respose = new StringBuilder();
            String line;
            while ((line = read.readLine()) != null) {
                respose.append(line);
            }
            ob = respose.toString();
            Log.d("TAG", ob);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (read != null) {
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (mHttpURLConnection != null) {
                //请求结束后关闭HTTP链接
                mHttpURLConnection.disconnect();
            }
            return ob;
        }//将get方法封装
    }
}

