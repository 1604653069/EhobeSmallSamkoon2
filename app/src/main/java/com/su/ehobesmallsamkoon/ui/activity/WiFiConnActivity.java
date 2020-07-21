package com.su.ehobesmallsamkoon.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.ui.dialog.LoginErrorDialog;
import com.su.ehobesmallsamkoon.util.ToastUtil;
import com.su.ehobesmallsamkoon.util.WifiConnector;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WiFiConnActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.wifi_conn_edit)
    EditText wifiConnEdit;
    @BindView(R.id.wifi_conn_show)
    ImageView wifiConnShow;
    @BindView(R.id.wifi_conn_back)
    ImageView wifiConnBack;
    @BindView(R.id.wifi_conn_title)
    TextView wifiConnTitle;
    @BindView(R.id.wifi_conn_conn)
    TextView wifiConnConn;
    WifiConnector wifiConnector;
    private boolean isShow = false;
    private LoginErrorDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wi_fi_conn);
        ButterKnife.bind(this);
        hideBottomUIMenu();
        initListener();
        intiView();
    }

    private void intiView() {
        wifiConnTitle.setText(getIntent().getStringExtra("wifiName"));
        wifiConnector = new WifiConnector((WifiManager)this.getApplicationContext().getSystemService(Context.WIFI_SERVICE));
        wifiConnector.setmHandler(handler);
        dialog = new LoginErrorDialog(this);
    }

    public void hideBottomUIMenu() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.wifi_conn_back:
                finish();
                break;
            case R.id.wifi_conn_conn:
                dialog.setTipRota(3,"连接中...","");
                dialog.show();
               // wifiConnector.connect(getIntent().getStringExtra("wifiName"),wifiConnEdit.getText().toString(), WifiConnector.WifiCipherType.WIFICIPHER_WPA);
                break;
            case R.id.wifi_conn_show:
                showOrHidden();
                break;
        }
    }
    private void initListener() {
        wifiConnBack.setOnClickListener(this);
        wifiConnConn.setOnClickListener(this);
        wifiConnShow.setOnClickListener(this);
        wifiConnEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>=8){
                    wifiConnConn.setTextColor(Color.parseColor("#04A8FF"));
                    wifiConnConn.setClickable(true);
                }
                else
                    wifiConnConn.setTextColor(Color.parseColor("#CCCCCC"));
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           // dialog.dismiss();
            if(msg.what==2){
                ToastUtil.show("WiFi连接成功",500);
            }
        }
    };
    private void showOrHidden(){
        if(!isShow){
            isShow = true;
            wifiConnShow.setImageResource(R.mipmap.xianshi_sele);
            wifiConnEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            return;
        }else{
            isShow = false;
            wifiConnShow.setImageResource(R.mipmap.buxianshi_nor);
            wifiConnEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

    }
}