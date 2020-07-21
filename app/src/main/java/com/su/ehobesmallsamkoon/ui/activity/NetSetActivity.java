package com.su.ehobesmallsamkoon.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.base.ToolbarActivity;

public class NetSetActivity extends ToolbarActivity {

    public static void start(Context context){
       // context.startActivity(new Intent(context,NetSetActivity.class));
        context.startActivity(new Intent(context,NetWorkSettingActivity.class));
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.parseColor("#FFFFFF"))
                .setTitle("IP地址设置")
                .setTitleTextColor(Color.parseColor("#04a8ff"))
                .addLeftImage(R.mipmap.back, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).addRightText("保存", Color.parseColor("#04a8ff"),16f,new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO 保存
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net;
    }

    @Override
    protected void initialize() {

    }
}
