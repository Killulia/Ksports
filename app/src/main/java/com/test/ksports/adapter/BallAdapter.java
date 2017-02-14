package com.test.ksports.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;
import com.test.ksports.R;
import com.test.ksports.bean.AgendaBean;
import java.util.List;

/**
 * Created by kingwag on 2016/12/3.
 */

public class BallAdapter extends BaseQuickAdapter<AgendaBean.ResultBean.ListBean.TrBean> {
    private Context context;
    public BallAdapter(Context context,List<AgendaBean.ResultBean.ListBean.TrBean> datas) {
        super(R.layout.item_ball,datas);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, AgendaBean.ResultBean.ListBean.TrBean trBean) {
        String status = "";
        switch (trBean.getStatus()){
            case 0:
                status = "未开赛";
                break;
            case 1:
                status = "直播中";
                break;
            case 2:
                status = "已结束";
                break;

        }
        holder.setText(R.id.ball_time_tv, trBean.getTime())
                .setText(R.id.ball_team1_tv,trBean.getPlayer1())
                .setText(R.id.ball_team2_tv,trBean.getPlayer2())
                .setText(R.id.ball_state_tv,status)
                .setText(R.id.ball_score_tv,trBean.getScore());
        if (!TextUtils.isEmpty(trBean.getPlayer1logo())){
            Picasso.with(context).load(trBean.getPlayer1logobig()).into((ImageView) holder.getView(R.id.ball_logo1_iv));
        }

        if (!TextUtils.isEmpty(trBean.getPlayer2logo())){
            Picasso.with(context).load(trBean.getPlayer2logobig()).into((ImageView) holder.getView(R.id.ball_logo2_iv));
        }

    }


}
