package com.su.ehobesmallsamkoon.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.base.ToolbarActivity;
import com.su.ehobesmallsamkoon.bean.NetWorkBean;
import com.su.ehobesmallsamkoon.ui.adapter.NetWorkAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NetWorkSettingActivity extends ToolbarActivity {

    @BindView(R.id.network_gridview)
    GridView networkGridview;
    private List<NetWorkBean> datas = new ArrayList<>();
    private NetWorkAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_work_setting;
    }

    @Override
    protected void initialize() {
        datas.add(new NetWorkBean("wifi连接",R.mipmap.wifi));
        datas.add(new NetWorkBean("WIFI模式设置",R.mipmap.wifi));
        datas.add(new NetWorkBean("IP地址设置",R.mipmap.ip));
        datas.add(new NetWorkBean("串口波特率设置",R.mipmap.botelv));
        datas.add(new NetWorkBean("WIFI模组重启",R.mipmap.zhongqi));
        adapter = new NetWorkAdapter(this,datas);
        networkGridview.setAdapter(adapter);
        initListener();
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.parseColor("#FFFFFF"))
                .setTitle("网络设置")
                .setTitleTextColor(Color.parseColor("#04a8ff"))
                .addLeftImage(R.mipmap.back, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
    public void initListener(){
        networkGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        startActivity(new Intent(NetWorkSettingActivity.this,WiFiActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(NetWorkSettingActivity.this,WIFIModeSetActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(NetWorkSettingActivity.this,IPSetActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(NetWorkSettingActivity.this,BRateActivity.class));
                        break;
                    case 4:
                        break;
                }
            }
        });
    }
}