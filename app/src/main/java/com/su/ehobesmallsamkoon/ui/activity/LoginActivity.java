package com.su.ehobesmallsamkoon.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.app.App;
import com.su.ehobesmallsamkoon.base.BaseActivity;
import com.su.ehobesmallsamkoon.base.BaseFragment;
import com.su.ehobesmallsamkoon.bean.respond.LoginResults;
import com.su.ehobesmallsamkoon.network.RxCallback;
import com.su.ehobesmallsamkoon.presenter.LoginPresenter;
import com.su.ehobesmallsamkoon.ui.adapter.TPagerAdapter;
import com.su.ehobesmallsamkoon.ui.fragment.FingerFragment;
import com.su.ehobesmallsamkoon.ui.fragment.InputFragment;
import com.su.ehobesmallsamkoon.util.SoundPoolUtil;
import com.xgzx.veinmanager.VeinApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnLongClick;

public class LoginActivity extends BaseActivity<LoginPresenter> {

    @BindView(R.id.tl)
    SlidingTabLayout tl;
    @BindView(R.id.vp)
    ViewPager vp;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private TPagerAdapter tPagerAdapter;
    private String[] title = {"静脉登录", "密码登录"};

    private SoundPoolUtil soundPoolUtil = new SoundPoolUtil();
    private static String sChara = ""; //当前采集的特征
    private String sThreadMsg = "";
    private boolean isGet = false;
    private Context context;

    public static void start(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initialize() {
        context = this;
        soundPoolUtil.PlayVoice(VeinApi.XG_VOICE_BEEP1, true);
        fragmentList.add(new FingerFragment());
        fragmentList.add(new InputFragment());
        tPagerAdapter = new TPagerAdapter(getSupportFragmentManager(), fragmentList);
        vp.setAdapter(tPagerAdapter);
        vp.setCurrentItem(0);
        tl.setViewPager(vp, title);
        tl.setTextSelectColor(Color.parseColor("#04a8ff"));
        tl.setTextUnselectColor(Color.parseColor("#999999"));
        tl.setTextsize(18);
        tl.setIndicatorColor(Color.parseColor("#04a8ff"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        isGet = false;
        new Thread(getCharaThread).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isGet = true;
    }

    //采集特征线程
    Thread getCharaThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (!isGet) {
                try {
                    GetChara();
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    //采集特征方法
    public void GetChara() {
        Log.e(TAG, "GetChara: ");
        if (App.DevHandle <= 0) {
            return;
        }
        sChara = VeinApi.GetVeinChara(App.DevHandle, 500); //通过指静脉设备采集特征，等待手指放入超时为6秒
        Log.e(TAG, "GetChara: " + sChara);
        if (sChara.length() < 10) {
            int ret = 0;
            try {
                ret = Integer.parseInt(sChara);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (ret == -16) {
                sThreadMsg = "此设备不支持特征采集";
            } else if (ret == -11) {
                sThreadMsg = "手指检测超时";
            } else if (ret == -17) {
                sThreadMsg = "请正确放置手指";
                VeinApi.PrintfDebug(sThreadMsg);
                try {
                    Thread.sleep(500); //等语音播完
                } catch (InterruptedException e) {
                }
            } else {
                sThreadMsg = "特征采集失败:" + sChara;
            }
        } else {
            soundPoolUtil.PlayVoice(VeinApi.XG_VOICE_BEEP1, true);
            sThreadMsg = "特征采集成功";
            Log.e(TAG, "GetChara: 特征采集成功");
            //验证
            tl.post(new Runnable() {
                @Override
                public void run() {
                    getPresenter().login(sChara).subscribe(new RxCallback<ArrayList<LoginResults>>() {
                        @Override
                        public void onSuccess(ArrayList<LoginResults> data) {
                            if (data != null || !data.equals("")) {
                                Log.e(TAG, "onSuccess: " + data);
                                OpenAndCloseActivity.start(context);
                                isGet = true;
                            }
                        }
                    });
                }
            });

        }
    }


    @OnLongClick(R.id.logo)
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.logo:
                SettingActivity.start(this);
                break;
        }
        return true;
    }

}
