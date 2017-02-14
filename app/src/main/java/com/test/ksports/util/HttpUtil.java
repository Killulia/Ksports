package com.test.ksports.util;

import android.util.Log;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kingwag on 2016/11/28.
 * 网络请求工具类
 */

public class HttpUtil {
    public static String getHttpData(String stringUrl) throws Exception {
        URL url = new URL(stringUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        if (conn.getResponseCode() == 200){
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            byte[] bytes = new byte[1024];
            int len = 0;
            StringBuffer buffer = new StringBuffer();
            while ((len = bis.read(bytes))!= -1){
                String str = new String(bytes, 0, len,"utf-8");
                buffer.append(str);
            }


            bis.close();
            conn.disconnect();
            Log.d("check", buffer.toString());
            return  buffer.toString();
        }
        return null;

    }
}
