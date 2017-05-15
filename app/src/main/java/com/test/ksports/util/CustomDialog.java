package com.test.ksports.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import com.test.ksports.R;


/**
 * Created by kingwag on 2016/12/26.
 */

/**
 * 购物车删除自定义Dialog
 */
public abstract class CustomDialog extends Dialog {
    public CustomDialog(Context context) {
        super(context, R.style.CustomStyle);
        setContentView(R.layout.custom_dialog_layout);
        //设置颜色，透明度
        WindowManager.LayoutParams lp=this.getWindow().getAttributes();
        lp.alpha=0.95f;
        lp.dimAmount=0.1f;
        this.getWindow().setAttributes(lp);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        findViewById(R.id.posi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               out();
            }
        });
        findViewById(R.id.navi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public abstract void out();


}
