package com.test.ksports.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.test.ksports.R;
import com.test.ksports.activity.DetailActivity;
import com.test.ksports.bean.NewsBean;
import com.test.ksports.constant.MyConstants;
import com.test.ksports.fragment.NewsOneFragment;
import com.test.ksports.util.OkHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.test.ksports.R.mipmap.datas;

public class MyReceiver extends BroadcastReceiver {
    private NotificationManager manager;
    private Notification notification;
    private Context context;
    private MyReceiver mContext = this;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NewsBean.DataBean.ArticlesBean  articlesBean = (NewsBean.DataBean.ArticlesBean) msg.obj;
            notification = instanceNotification(articlesBean);
            manager.notify(1,notification);
        }


    };

    private Notification instanceNotification(NewsBean.DataBean.ArticlesBean bean){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("item", bean);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.mipmap.hupu)
                .setContentTitle("头条新闻")
                .setContentText(bean.getTitle())
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL);
        Notification notification = builder.build();
        return notification;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        getInfo();
    }

    //获取数据
    private void getInfo() {
        OkHttpUtils.doAsyncGETRequest(MyConstants.NEWS_URL1_1, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("MyReceiver", "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    String jsonString = body.string();
                    //json解析
                    Gson gson = new Gson();
                    final NewsBean newsBean = gson.fromJson(jsonString, NewsBean.class);
                    if (newsBean != null) {
                        NewsBean.DataBean.ArticlesBean articlesBean = newsBean.getData().getArticles().get(0);
                        if (!NewsOneFragment.receiverCache.equals(articlesBean.getTitle())){
                            Message message = new Message();
                            message.obj = articlesBean;
                            handler.sendMessage(message);
                            NewsOneFragment.receiverCache = articlesBean.getTitle();
                        }

                    }


                }
            }
        });


    }
}
