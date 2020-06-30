package com.su.ehobesmallsamkoon.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.base.ToolbarActivity;
import com.su.ehobesmallsamkoon.presenter.SettingPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends ToolbarActivity<SettingPresenter> {

    @BindView(R.id.net)
    LinearLayout net;
    @BindView(R.id.users)
    LinearLayout users;
    @BindView(R.id.finger)
    LinearLayout finger;

    public static void start(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initialize() {

    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle("设置")
                .setBackgroundColor(Color.parseColor("#FFFFFF"))
                .setTitleTextColor(Color.parseColor("#04a8ff"))
                .addLeftImage(R.mipmap.back, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    @OnClick({R.id.net,R.id.finger,R.id.users})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.net:
                //TODO 网络设置
                NetSetActivity.start(this);
                break;
            case R.id.finger:
                //TODO 指静脉
                break;
            case R.id.users:
                //TODO 人员列表
                UsersActivity.start(this);
                break;
        }
    }

}
