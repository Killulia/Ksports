package com.test.ksports.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.test.ksports.R;
import com.test.ksports.util.StatusbarUtil;

/**
 * 反馈页面
 */
public class SugesstionActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mEdit;//文本框
    private Button mBtn;//提交按钮
    private Context mContext;//上下文
    private String mText;//文字内容
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏颜色
        StatusbarUtil.setStatusBarColor(this,getResources().getColor(R.color.red));
        setContentView(R.layout.activity_sugesstion);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mEdit = (EditText) findViewById(R.id.ed_sugess);
        mBtn = (Button) findViewById(R.id.angry_btn);
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.angry_btn) {
            mText = mEdit.getText().toString();
            Intent data=new Intent(Intent.ACTION_SENDTO);
            data.setData(Uri.parse("mailto:631703900@qq.com"));
            data.putExtra(Intent.EXTRA_SUBJECT, "意见反馈");
            data.putExtra(Intent.EXTRA_TEXT, mText);
            startActivityForResult(data,100);
            mEdit.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
    }
}
