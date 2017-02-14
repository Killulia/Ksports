package com.test.ksports.util;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by kingwag on 2016/11/28.
 */

public class JsonTask extends AsyncTask<Void,Void,String> {
    private OnDownloadLisntner downloadLisntner;
    private String url;
    public JsonTask(String url,OnDownloadLisntner downloadLisntner) {
        this.url = url;
        this.downloadLisntner = downloadLisntner;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            String result = HttpUtil.getHttpData(url);

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (!TextUtils.isEmpty(s)){
            Log.d("check", s);
            downloadLisntner.onSuccess(s);
        }

    }

    public interface OnDownloadLisntner{
        void onSuccess(String result);
    }
}
