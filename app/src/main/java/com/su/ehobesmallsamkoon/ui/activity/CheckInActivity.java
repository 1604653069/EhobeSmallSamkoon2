package com.su.ehobesmallsamkoon.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.su.ehobesmallsamkoon.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckInActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.check_back)
    ImageView checkBack;
    @BindView(R.id.check_title)
    TextView checkTitle;
    @BindView(R.id.check_in)
    TextView checkIn;
    @BindView(R.id.check_edit_userid)
    EditText checkEditUserid;
    @BindView(R.id.check_edit_id)
    EditText checkEditId;
    @BindView(R.id.check_img_random)
    LinearLayout checkImgRandom;
    @BindView(R.id.check_edit_pwd)
    EditText checkEditPwd;
    @BindView(R.id.check_img_show)
    LinearLayout checkImgShow;
    @BindView(R.id.check_img_img)
    ImageView checkImgImg;
    private boolean isShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_check_in);
        ButterKnife.bind(this);
        hideBottomUIMenu();
        initListener();
    }

    private void initListener() {
        checkImgRandom.setOnClickListener(this);
        checkBack.setOnClickListener(this);
        checkIn.setOnClickListener(this);
        checkIn.setClickable(false);
        checkImgShow.setOnClickListener(this);
        checkEditUserid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0 && checkEditId.length() > 0 && checkEditPwd.length() > 0) {
                    checkIn.setTextColor(Color.parseColor("#04A8FF"));
                    checkIn.setClickable(true);
                } else {
                    checkIn.setTextColor(Color.parseColor("#CCCCCC"));
                    checkIn.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        checkEditId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0 && checkEditUserid.length() > 0 && checkEditPwd.length() > 0) {
                    checkIn.setTextColor(Color.parseColor("#04A8FF"));
                    checkIn.setClickable(true);
                } else {
                    checkIn.setTextColor(Color.parseColor("#CCCCCC"));
                    checkIn.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        checkEditPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0 && checkEditUserid.length() > 0 && checkEditId.length() > 0) {
                    checkIn.setTextColor(Color.parseColor("#04A8FF"));
                    checkIn.setClickable(true);
                } else {
                    checkIn.setTextColor(Color.parseColor("#CCCCCC"));
                    checkIn.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.check_img_random:
                checkEditId.setText(randromNumber() + "");
                break;
            case R.id.check_in:
                Intent intent = new Intent(CheckInActivity.this, VeinActivity.class);
                intent.putExtra("userId",checkEditUserid.getText().toString());
                intent.putExtra("id",checkEditId.getText().toString());
                intent.putExtra("password",checkEditPwd.getText().toString());
                startActivity(intent);
                finish();
                break;
            case R.id.check_back:
                finish();
                break;
            case R.id.check_img_show:
                showOrHidden();
                break;
        }
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

    private String randromNumber() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            int number = (new Random().nextInt(9) + 1);
            sb.append(number);
        }
        return sb.toString();
    }

    private void showOrHidden() {
        if (!isShow) {
            isShow = true;
            checkImgImg.setImageResource(R.mipmap.xianshi_sele);
            checkEditPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            return;
        } else {
            isShow = false;
            checkImgImg.setImageResource(R.mipmap.buxianshi_nor);
            checkEditPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

    }
}