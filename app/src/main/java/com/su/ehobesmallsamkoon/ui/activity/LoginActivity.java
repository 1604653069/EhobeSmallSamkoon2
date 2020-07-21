package com.su.ehobesmallsamkoon.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.app.App;
import com.su.ehobesmallsamkoon.app.Constant;
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

import static com.su.ehobesmallsamkoon.util.SoundPoolUtil.PlayVoice;

public class LoginActivity extends BaseActivity<LoginPresenter> implements InputFragment.LoginListener {

    @BindView(R.id.tl)
    SlidingTabLayout tl;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.login_label_tip)
    LinearLayout loginTip;
    @BindView(R.id.login_label_tv)
    TextView tipView;
    private Animation myAnim;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private TPagerAdapter tPagerAdapter;
    private String[] title = {"静脉登录", "密码登录"};

    private SoundPoolUtil soundPoolUtil = new SoundPoolUtil();
    private static String sChara = ""; //当前采集的特征
    private String sThreadMsg = "";
    private boolean isGet = false;
    private Context context;
    public static int iThreadRun = 0;
    private MyHandle mHandler = new MyHandle();
    AlertDialog MsgDlg;
    public static void start(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initialize() {
        Log.i("TAG", "设备ID："+Constant.TERM_ID);
        context = this;
        //soundPoolUtil.PlayVoice(VeinApi.XG_VOICE_BEEP1, true);
        PlayVoice(VeinApi.XG_INPUT_FINGER, true);
//        fragmentList.add(new FingerFragment2());
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
        MsgDlg = new AlertDialog.Builder(this)
                .setTitle("信息对话框")//设置对话框的标题
                .setMessage("提示信息")//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", null).create();
    }
    public void ShowMsgDlg(String Title, String Msg)
    {
        if(MsgDlg.isShowing()) return;
        MsgDlg.setTitle(Title);
        MsgDlg.setMessage(Msg);
        MsgDlg.show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        isGet = false;
        new Thread(getCharaThread).start();
       //new Thread(devVerifyThread).start();
        Log.i(TAG,"设备ID："+Constant.TERM_ID);
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
    Thread devVerifyThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (!isGet){
                try {
                    devVerify();
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    //采集特征方法
    public void GetChara() {
        Log.i(TAG, "GetChara: ");
        if (App.DevHandle <= 0) {
            return;
        }
        sChara = VeinApi.GetVeinChara(App.DevHandle, 500); //通过指静脉设备采集特征，等待手指放入超时为6秒
        Log.i(TAG, "GetChara: " + sChara);
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
            PlayVoice(VeinApi.XG_VOICE_BEEP1, true);
            sThreadMsg = "特征采集成功";
            Log.i(TAG, "GetChara: 特征采集成功");
            //验证
            tl.post(new Runnable() {
                @Override
                public void run() {
                    getPresenter().login(sChara).subscribe(new RxCallback<ArrayList<LoginResults>>() {
                        @Override
                        public void onSuccess(ArrayList<LoginResults> data) {
                            if (data != null || !data.equals("")) {
                                Log.e(TAG, "onSuccess: " + data.get(0).toString());
                                PlayVoice(VeinApi.XG_VOICE_VERIFY_SUCCESS, true);
                                OpenAndCloseActivity.start(context);
                                isGet = true;
                            }else{
                                PlayVoice(VeinApi.XG_VOICE_VERIFY_FAIL, true);
                            }
                        }
                    });
                }
            });
        }
    }
    public void devVerify()
    {
        if(App.DevHandle <= 0)
        {
            Log.i("TAG","请先连接设备");
            return;
        }
        long ret = 0;
        byte[] bData = new byte[16];
        int UserId = 0; //如果UserId为0则1:N识别，如指定ID则1:1验证，只跟对应的ID比对
        bData[0] = (byte)(UserId&0xff);
        bData[1] = (byte)((UserId>>8)&0xff);
        bData[2] = 0;
        bData[3] = 0;
        bData[4] = 0; //分组组号

        String sData = VeinApi.FVHexToAscii(bData, 16); //要转成HEX字符串
        ret = VeinApi.SendCmdPacket(App.DevHandle, VeinApi.XG_CMD_VERIFY, sData);
        if(ret == 0)
        {
            for(;;)
            {
                sData = VeinApi.RecvCmdPacket(App.DevHandle, 6000); //默认设置手指检测超时是5秒，这里接收超时要大于设置的手指超时
                if(sData != null && sData.length() > 0)
                {
                    VeinApi.FVAsciiToHex(sData, bData);
                    ret = bData[0];
                    if(ret == VeinApi.XG_ERR_SUCCESS)
                    {
                        UserId = (bData[1]&0xff) + (bData[2]&0xff)*256; //识别成功的ID号
                        PlayVoice(VeinApi.XG_VOICE_VERIFY_SUCCESS, false);
                        sThreadMsg = "验证成功:" + UserId;
                        Log.i("TAG",sThreadMsg);
                        Log.i("TAG",ret+"");
                        startActivity(new Intent(LoginActivity.this,UserInfoActivity.class));
                        return;
                    } else if(ret == VeinApi.XG_ERR_FAIL) {
                       // PlayVoice(VeinApi.XG_VOICE_VERIFY_FAIL, false);
                        sThreadMsg = "验证失败";
                        Log.i("TAG",sThreadMsg);
                        Log.i("TAG",ret+"");
                        return;
                    } else if(ret == VeinApi.XG_INPUT_FINGER) {
                        //请放手指
                      //  PlayVoice(VeinApi.XG_VOICE_PUTFINGER, false);
                    } else if(ret == VeinApi.XG_RELEASE_FINGER) {
                        //采集成功，提示可以拿开手指了
                      //  PlayVoice(VeinApi.XG_VOICE_BEEP1, false);
                    } else {
                        //未知错误
                        Log.i("TAG","验证发生未知错误");
                        return;
                    }
                } else {
                    //收包失败
                    Log.i("TAG","等待数据收集中...");
                    return;
                }
            }
        } else {
            //发包失败
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

    @Override
    public void onSuccess(String username) {
        //显示登录提示动画
        starTipMove();
        //显示登录用户
        tipView.setText("登录成功,"+username);
    }
    private void starTipMove(){
        myAnim = AnimationUtils.loadAnimation(this, R.anim.login_label_tip);
        myAnim.setFillAfter(true);//android动画结束后停在结束位置
        loginTip.setVisibility(View.VISIBLE);
        loginTip.startAnimation(myAnim);
    }
    class MyHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            //DebugMsg("Msg.what:" + msg.what);
            //DebugMsg(sThreadMsg);
            if (msg.what == 1000) {
//                ShowMsgDlg("模板采集", sThreadMsg);
            }
            if (msg.what == 1001) {
//                ShowMsgDlg("特征采集", sThreadMsg);
            }
            if (msg.what == 1002) {
//                ShowMsgDlg("图像采集", sThreadMsg);
            }
            if (msg.what == 1003) {
//                ShowMsgDlg("在设备增加用户", sThreadMsg);
            }
            if (msg.what == 1004) {
               // ShowMsgDlg("在设备验证用户", sThreadMsg);
                Log.i("TAG","在设备验证用户:"+sThreadMsg);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isGet = true;
    }
}
