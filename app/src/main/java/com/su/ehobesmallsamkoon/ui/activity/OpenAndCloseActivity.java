package com.su.ehobesmallsamkoon.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.base.BaseActivity;
import com.su.ehobesmallsamkoon.presenter.OpenAndClosePresenter;

import butterknife.BindView;

public class OpenAndCloseActivity extends BaseActivity<OpenAndClosePresenter> {

    @BindView(R.id.logintip)
    LinearLayout logintip;
    @BindView(R.id.tip)
    TextView tip;
    @BindView(R.id.userName)
    TextView userName;
    public static void start(Context context) {
        context.startActivity(new Intent(context, OpenAndCloseActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_oc;
    }

    @Override
    protected void initialize() {
        userName.setText("登陆成功，"+"AAA"+"!");
        logintip.postDelayed(new Runnable() {
            @Override
            public void run() {
                logintip.setVisibility(View.GONE);
            }
        }, 2000);
    }

}
