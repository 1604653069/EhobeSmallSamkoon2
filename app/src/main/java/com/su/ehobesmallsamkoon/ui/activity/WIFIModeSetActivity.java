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
import android.text.TextUtils;
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
import com.su.ehobesmallsamkoon.util.ToastUtil;
import com.su.ehobesmallsamkoon.util.WifiConnector;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WIFIModeSetActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.wifi_mode_back)
    ImageView wifiModeBack;
    @BindView(R.id.wifi_mode_title)
    TextView wifiModeTitle;
    @BindView(R.id.wifi_mode_conn)
    TextView wifiModeConn;
    @BindView(R.id.wifi_mode_name)
    EditText wifiModeName;
    @BindView(R.id.wifi_mode_password)
    EditText wifiModePassword;
    @BindView(R.id.wifi_mode_show)
    ImageView wifiModeShow;
    WifiConnector wifiConnector;
    private boolean isShow =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_w_i_f_i_mode_set);
        ButterKnife.bind(this);
        init();
        hideBottomUIMenu();
        intiListener();
    }

    private void init() {
        wifiConnector = new WifiConnector((WifiManager)this.getApplicationContext().getSystemService(Context.WIFI_SERVICE));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.wifi_mode_back:
                finish();
                break;
            case R.id.wifi_mode_conn:
                connectWiFi();
                break;
            case R.id.wifi_mode_show:
                showOrHidden();
                break;
        }
    }
    private void intiListener() {
        wifiModeConn.setOnClickListener(this);
        wifiModeShow.setOnClickListener(this);
        wifiModeBack.setOnClickListener(this);
        wifiModePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(wifiModeName.getText().toString())&&charSequence.length()>=8)
                    wifiModeConn.setTextColor(Color.parseColor("#04A8FF"));
                else{
                    wifiModeConn.setTextColor(Color.parseColor("#cccccc"));
                    wifiModeConn.setClickable(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        wifiModeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0&&wifiModePassword.length()>=8){
                    wifiModeConn.setTextColor(Color.parseColor("#04A8FF"));
                    wifiModeConn.setClickable(true);
                }else{
                    wifiModeConn.setTextColor(Color.parseColor("#cccccc"));
                    wifiModeConn.setClickable(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void hideBottomUIMenu() {
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

    /**
     * 连接WiFi
     */
    private void connectWiFi(){
        String wifiName = wifiModeName.getText().toString();
        String wifiPassword = wifiModePassword.getText().toString();
        wifiConnector.setmHandler(handler);
        wifiConnector.connect(wifiName,wifiPassword,WifiConnector.WifiCipherType.WIFICIPHER_WPA);
    }

    private void showOrHidden(){
        if(!isShow){
            isShow = true;
            wifiModeShow.setImageResource(R.mipmap.xianshi_sele);
            wifiModePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            return;
        }else{
            isShow = false;
            wifiModeShow.setImageResource(R.mipmap.buxianshi_nor);
            wifiModePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ToastUtil.show("WiFi连接的消息",500);
        }
    };
}