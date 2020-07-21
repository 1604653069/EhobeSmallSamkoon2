package com.su.ehobesmallsamkoon.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.base.ToolbarActivity;
import com.su.ehobesmallsamkoon.presenter.SettingPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends ToolbarActivity<SettingPresenter> {

    @BindView(R.id.net)
    LinearLayout net;
    @BindView(R.id.users)
    LinearLayout users;
    @BindView(R.id.finger)
    LinearLayout finger;
    @BindView(R.id.setting_save)
    LinearLayout settingSet;
    @BindView(R.id.setting_img_plus)
    ImageView settingImgPlus;
    @BindView(R.id.setting_tv_power)
    TextView settingTvPower;
    @BindView(R.id.setting_img_add)
    ImageView settingImgAdd;
    @BindView(R.id.setting_tv_year)
    TextView settingTvYear;
    @BindView(R.id.setting_img_year_add)
    ImageView settingImgYearAdd;
    @BindView(R.id.setting_img_year_plus)
    ImageView settingImgYearPlus;
    @BindView(R.id.setting_tv_month)
    TextView settingTvMonth;
    @BindView(R.id.setting_img_month_add)
    ImageView settingImgMonthAdd;
    @BindView(R.id.setting_img_month_plus)
    ImageView settingImgMonthPlus;
    @BindView(R.id.setting_tv_day)
    TextView settingTvDay;
    @BindView(R.id.setting_img_day_add)
    ImageView settingImgDayAdd;
    @BindView(R.id.setting_img_day_plus)
    ImageView settingImgDayPlus;
    @BindView(R.id.setting_tv_hour)
    TextView settingTvHour;
    @BindView(R.id.setting_img_hour_add)
    ImageView settingImgHourAdd;
    @BindView(R.id.setting_img_hour_plus)
    ImageView settingImgHourPlus;
    @BindView(R.id.setting_tv_min)
    TextView settingTvMin;
    @BindView(R.id.setting_img_min_add)
    ImageView settingImgMinAdd;
    @BindView(R.id.setting_img_min_plus)
    ImageView settingImgMinPlus;
    private double currentPower = 26.5;
    private int currentYear=2020;
    private int currentMonth = 8;
    private int currentDay = 20;
    private int currentHour = 17;
    private int currentMin =20;
    public static void start(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting2;
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

    @OnClick({R.id.net, R.id.finger, R.id.users, R.id.setting_save, R.id.setting_img_plus, R.id.setting_img_add,
    R.id.setting_img_year_add,R.id.setting_img_year_plus,R.id.setting_img_month_add,R.id.setting_img_month_plus,
    R.id.setting_img_day_add,R.id.setting_img_min_add,R.id.setting_img_min_plus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.net:
                //TODO 网络设置
                NetSetActivity.start(this);
                break;
            case R.id.finger:
                //TODO 指静脉
                VeinActivity.start(this);
                break;
            case R.id.users:
                //TODO 人员列表
                UsersActivity.start(this);
                break;
            case R.id.setting_save:
                Toast.makeText(this, "好了保存成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_img_plus:
                currentPower = Double.parseDouble(String.format("%.2f", currentPower - 0.1));
                settingTvPower.setText(currentPower + "W");
                break;
            case R.id.setting_img_add:
                currentPower = Double.parseDouble(String.format("%.2f", currentPower + 0.1));
                settingTvPower.setText(currentPower + "W");
                break;
            case R.id.setting_img_year_add:
                settingTvYear.setText(String.valueOf(++currentYear));
                break;
            case R.id.setting_img_year_plus:
                settingTvYear.setText(String.valueOf(--currentYear));
                break;
            case R.id.setting_img_month_add:
                settingTvMonth.setText(String.valueOf(++currentMonth));
                break;
            case R.id.setting_img_month_plus:
                settingTvMonth.setText(String.valueOf(--currentMonth));
                break;
            case R.id.setting_img_day_add:
                settingTvDay.setText(String.valueOf(++currentDay));
                break;
            case R.id.setting_img_day_plus:
                settingTvDay.setText(String.valueOf(--currentDay));
                break;
            case R.id.setting_img_hour_add:
                settingTvHour.setText(String.valueOf(++currentHour));
                break;
            case R.id.setting_img_hour_plus:
                settingTvHour.setText(String.valueOf(--currentHour));
                break;
            case R.id.setting_img_min_add:
                settingTvMin.setText(String.valueOf(++currentHour));
                break;
            case R.id.setting_img_min_plus:
                settingTvMin.setText(String.valueOf(--currentHour));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
