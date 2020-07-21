package com.su.ehobesmallsamkoon.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.su.ehobesmallsamkoon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BRateActivity extends AppCompatActivity {


    String[] datas = {"", "321", "213"};
    @BindView(R.id.test_edit)
    TextView testEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_b_rate);
        ButterKnife.bind(this);
        hideBottomUIMenu();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        testEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
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

    private void show() {
        PopupMenu popupMenu = new PopupMenu(this, testEdit);
        popupMenu.getMenuInflater().inflate(R.menu.date, popupMenu.getMenu());
        hideBottomUIMenu();
        popupMenu.show();
    }
}