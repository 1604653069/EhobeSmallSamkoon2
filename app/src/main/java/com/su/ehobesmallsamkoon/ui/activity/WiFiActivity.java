package com.su.ehobesmallsamkoon.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.base.ToolbarActivity;
import com.su.ehobesmallsamkoon.ui.adapter.WiFiAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WiFiActivity extends ToolbarActivity {

    @BindView(R.id.wifi_gridview)
    GridView wifiGridview;
    private WifiManager wifiManager;
    private List<ScanResult> list = new ArrayList<>();
    private WiFiAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wi_fi;
    }

    @Override
    protected void initialize() {
        getInfo();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.parseColor("#FFFFFF"))
                .setTitle("WIFI连接")
                .setTitleTextColor(Color.parseColor("#04a8ff"))
                .addLeftImage(R.mipmap.back, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    /**
     * 获取设备周围WiFi信息
     */
    private void getInfo(){
        wifiManager = (WifiManager)this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        wifiManager.startScan();  //开始扫描AP
        list = (ArrayList<ScanResult>) wifiManager.getScanResults();
        adapter =new WiFiAdapter(this,list);
        wifiGridview.setAdapter(adapter);
    }
    private void initListener() {
        wifiGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(WiFiActivity.this, WiFiConnActivity.class);
                intent.putExtra("wifiName",list.get(i).SSID);
                startActivity(intent);
            }
        });
    }
}