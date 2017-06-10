package com.test.ksports.util;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by bodhixu on 2016/12/6.
 */

public class OkHttpUtils {

    public static OkHttpUtils okHttpUtils;
    public static OkHttpClient okHttpClient;

    private OkHttpUtils() {
    }

    //创建工具类单例模式
    public static OkHttpUtils newInstance() {
        if (okHttpUtils == null) {
            synchronized (OkHttpUtils.class) {
                if (okHttpUtils == null) {
                    okHttpUtils = new OkHttpUtils();
                }
            }
        }
        return okHttpUtils;
    }

    //创建OkHttpClient单例模式
    public static OkHttpClient newOkHttpClient() {
        if (okHttpClient == null) {

            synchronized (OkHttpUtils.class) {
                if (okHttpClient == null) {
                    //设置缓存目录和大小
                    //  int cacheSize = 10 << 20; // 10 MiB
                    //  Cache cache = new Cache(context.getCacheDir(), cacheSize);

                    okHttpClient = new OkHttpClient().newBuilder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
//                .cache(cache) //设置缓存
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    //=====================GET请求=================
    //获得Request实例
    private Request getRequest(String url) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        // builder.tag(Object obj); //设置tag，用于取消
        return builder.build();
    }

    /**
     * 获得Call实例
     *
     * @param url
     * @return
     */
    private Call getCall(String url) {
        okHttpUtils = newInstance();
        okHttpClient = newOkHttpClient();
        Request request = okHttpUtils.getRequest(url);
        return okHttpClient.newCall(request);
    }

    //=====================同步GET请求=================
    //通过Url获得字符串 -- 同步的GET 请求
    public static ResponseBody getResponseBody(String url) throws Exception {
        okHttpUtils = newInstance();
        Call call = okHttpUtils.getCall(url);
        return call.execute().body();
    }

    //通过Url获得字符串 -- 同步的GET 请求
    public static String getString(String url) throws Exception {
        ResponseBody responseBody = getResponseBody(url);
        if (responseBody != null) {
            return responseBody.string();
        }
        return null;
    }

    //=================异步GET请求======================
    public static void doAsyncGETRequest(String url, Callback callback) {
        okHttpUtils = newInstance();
        Call call = okHttpUtils.getCall(url);
        call.enqueue(callback);//执行异步的任务
    }

    //=================POST请求======================
    //创建POST请求
    private Request getPostRequest(String url, RequestBody requestBody) {
        Request.Builder builder = new Request.Builder()
                .url(url)
//                .addHeader(key, value) //上传请求头
                .post(requestBody);
        return builder.build();
    }

    ;

    //通过键值对创建RequestBody
    private RequestBody getRequestBody(HashMap<String, String> map) {
        FormBody.Builder builder = new FormBody.Builder();
        for (HashMap.Entry<String, String> entry : map.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    //通过Json字符串创建RequestBody
    private RequestBody getRequestBody(String url, String jsonString) {
        //定义mimetype对象
        /*String MEDIA_TYPE_STREAM = "application/octet-stream;charset=utf-8";
        String MEDIA_TYPE_STRING = "text/plain;charset=utf-8";*/
        String MEDIA_TYPE_JSON = "application/json;charset=utf-8";
        MediaType JSON = MediaType.parse(MEDIA_TYPE_JSON);
        return RequestBody.create(JSON, jsonString);
    }

    //获取文件MimeType
    private String getMimeType(String filename) {
        FileNameMap filenameMap = URLConnection.getFileNameMap();
        String contentTypeFor = filenameMap.getContentTypeFor(filename);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    //根据请求参数的键值对和上传的文件生成RequestBody
    private RequestBody getRequestBody(HashMap<String, String> map, List<String> fileNames) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (HashMap.Entry<String, String> entry : map.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        for (int i = 0; i < fileNames.size(); i++) {
            File file = new File(fileNames.get(i));
            String fileType = getMimeType(file.getName());
            builder.addFormDataPart("image", file.getName(),
                    RequestBody.create(MediaType.parse(fileType), file));
        }
        return builder.build();
    }
    //--------------------------POST请求------------------------------

    //=================同步POST请求======================
    //同步POST - 带键值对参数(上传Form表单)
    public static String doPostForm(String url, HashMap<String, String> map) throws Exception {
        okHttpUtils = newInstance();
        RequestBody requestBody = okHttpUtils.getRequestBody(map);
        Request request = okHttpUtils.getPostRequest(url, requestBody);
        okHttpClient = newOkHttpClient();
        Response response = okHttpClient.newCall(request).execute();
        if (response != null) {
            return response.body().string();
        }
        return null;
    }

    //同步POST - 上传键值对参数和文件列表(提交分块请求-上传表单及多文件)
    public static String doPostMultiFiles(String url, HashMap<String, String> map, List<String> fileNames) throws Exception {
        okHttpUtils = newInstance();
        RequestBody requestBody = okHttpUtils.getRequestBody(map, fileNames);
        Request request = okHttpUtils.getPostRequest(url, requestBody);
        okHttpClient = newOkHttpClient();
        Response response = okHttpClient.newCall(request).execute();
        if (response != null) {
            return response.body().string();
        }
        return null;
    }
    //---------------------------同步POST请求----------------

    //=================异步POST请求======================
    //异步POST - 带键值对参数(上传Form表单)
    public static void doAsyncPostForm(String url, HashMap<String, String> map, Callback callback) {
        okHttpUtils = newInstance();
        RequestBody requestBody = okHttpUtils.getRequestBody(map);
        Request request = okHttpUtils.getPostRequest(url, requestBody);
        okHttpClient = newOkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //异步POST - 上传键值对参数和文件列表(提交分块请求-上传表单及多文件)
    public static void doAsyncPostMultiFiles(String url, HashMap<String, String> map, List<String> fileNames, Callback callback) {
        okHttpUtils = newInstance();
        RequestBody requestBody = okHttpUtils.getRequestBody(map, fileNames);
        Request request = okHttpUtils.getPostRequest(url, requestBody);
        okHttpClient = newOkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }


}
