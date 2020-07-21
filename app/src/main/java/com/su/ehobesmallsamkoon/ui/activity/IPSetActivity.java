package com.su.ehobesmallsamkoon.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.su.ehobesmallsamkoon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IPSetActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ip_mode_back)
    ImageView ipModeBack;
    @BindView(R.id.ip_mode_save)
    TextView ipModeSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_i_p_set);
        ButterKnife.bind(this);
        hideBottomUIMenu();
        initLisnter();
    }

    private void initLisnter() {
        ipModeBack.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ip_mode_back:
                finish();
                return;
        }
    }
}