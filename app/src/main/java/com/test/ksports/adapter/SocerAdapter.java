package com.test.ksports.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.test.ksports.R;
import com.test.ksports.bean.SocerBean;
import java.util.List;

/**
 * Created by kingwag on 2016/12/3.
 * 足球适配器
 */

public class SocerAdapter extends BaseQuickAdapter<SocerBean.ResultBean.ViewsBean.Saicheng1Bean> {
    private Context context;
    public SocerAdapter(Context context, List<SocerBean.ResultBean.ViewsBean.Saicheng1Bean> datas) {
        super(R.layout.live_item2,datas);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, SocerBean.ResultBean.ViewsBean.Saicheng1Bean trBean) {
        String status = trBean.getC1();
        holder.setText(R.id.ball_time_tv2, trBean.getC2())
                .setText(R.id.ball_team1_tv2,trBean.getC4T1())
                .setText(R.id.ball_team2_tv2,trBean.getC4T2())
                .setText(R.id.ball_state_tv2,status)
                .setText(R.id.ball_score_tv2,trBean.getC4R());

    }


}
