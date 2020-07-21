package com.su.ehobesmallsamkoon.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.api.HostUrl;
import com.su.ehobesmallsamkoon.app.App;
import com.su.ehobesmallsamkoon.app.Constant;
import com.su.ehobesmallsamkoon.base.ToolbarActivity;
import com.su.ehobesmallsamkoon.ui.dialog.LoginErrorDialog;
import com.su.ehobesmallsamkoon.util.OkhttpUtils;
import com.xgzx.veinmanager.VeinApi;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static com.su.ehobesmallsamkoon.util.SoundPoolUtil.PlayVoice;

public class VeinActivity extends ToolbarActivity implements LoginErrorDialog.OnLeftRightClickListener {
    @BindView(R.id.vein_img)
    ImageView veinImg;
    @BindView(R.id.vein_tip)
    TextView veinTip;
    private int iThreadRun = 0;
    private Handler mHandler = new MyHandle();
    private LoginErrorDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vein;
    }

    @Override
    protected void initialize() {
        //PlayVoice(VeinApi.XG_VOICE_PUTFINGER, false);
        starGetChar();
        dialog = new LoginErrorDialog(this);
        dialog.setLeftRightClickListener(this);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, VeinActivity.class));
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.parseColor("#FFFFFF"))
                .setTitle("静脉录入")
                .setTitleTextColor(Color.parseColor("#04a8ff"))
                .addLeftImage(R.mipmap.back, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    @Override
    public void onLeftClickListener() {
        dialog.dismiss();
        startActivity(new Intent(this,CheckInActivity.class));
        finish();
    }

    @Override
    public void onRightClickListener() {
        dialog.dismiss();
        startActivity(new Intent(this,UserInfoActivity.class));
        finish();
    }

    class GetTempThread extends Thread {
        @Override
        public void run() {
            iThreadRun = 1; //线程在运行
            GetTemp();
            iThreadRun = 0; //线程结束
//            Message Msg = mHandler.obtainMessage(1000);
//            mHandler.sendMessage(Msg);
        }
    }

    public void GetTemp() {
        int MaxCharaNum = 3;
        int error = 0;
        if (App.DevHandle <= 0) {
            Log.i("TAG", "请先连接设备");
            return;
        }
        String[] ssChara = new String[MaxCharaNum];
        String ssTemp = "";
        int CharaNum = 0;
        PlayVoice(VeinApi.XG_VOICE_PUTFINGER, true);
        while (true) {
            ssChara[CharaNum] = VeinApi.GetVeinChara(App.DevHandle, 6000);//通过指静脉设备采集特征，等待手指放入超时为6秒
            if (ssChara[CharaNum].length() < 10) {
                PlayVoice(VeinApi.XG_VOICE_BEEP11, true);
                try {
                    error = Integer.parseInt(ssChara[CharaNum]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if (error == -16) {
                    Log.i("TAG", "此设备不支持特征采集");
                    break;
                } else if (error == -11) {
                    Log.i("TAG", "手指检测超时");
                    break;
                } else if (error == -17) { //特征采集失败可以继续采集下一次
                    Log.i("TAG", "请正确放置手指");
                    try {
                        Thread.sleep(500); //等语音播完
                    } catch (InterruptedException e) {
                    }
                    PlayVoice(VeinApi.XG_VOICE_PUTFINGER_RIGHT, true);
                    try {
                        Thread.sleep(2000); //等语音播完
                    } catch (InterruptedException e) {
                    }
                } else {
                    Log.i("TAG", "采集失败" + ssChara[CharaNum]);
                    break;
                }
            } else {
                error = 0;
                PlayVoice(VeinApi.XG_VOICE_BEEP1, true);
                //通过增加特征的这个函数来创建模板，一个模板最多可以增加6个特征，等同于FVCreateVeinTemp
                ssTemp = VeinApi.FVAddCharaToTemp(ssTemp, ssChara[CharaNum], null, 0);
                if (ssTemp.length() < 10) {
                    Log.i("TAG", "增加特征失败:" + ssTemp);
                }
                CharaNum++;
                if (CharaNum == 2) {
                    //检测2次采集的特征是不是同一个手指，可以不检测
                    long ret = VeinApi.FVCharaMatch(ssChara[0], ssChara[1], 66);
                    Log.i("TAG", "我这里有被执行:ret的值为:" + ret);
                    if (ret != 0) {
                        Log.i("TAG", "不是同一根手指");
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        changImgAndTip();
                    }
                });
            }
            if (CharaNum >= MaxCharaNum) {
                break;
            }
            PlayVoice(VeinApi.XG_VOICE_PUTFINGER_AGAIN, true);
            //等待手指拿开采集下一次
            VeinApi.CheckFinger(App.DevHandle, 10000, 0);
        }
        if (error == 0) {
            VeinApi.PrintfDebug("3次特征采集完成,准备融合");
            String sUserInfo = "NAME:刘二,ADMIN:0,DEPART:0,CARDNO:66778899,WORKNO:123,PASSWORD:666666,UID:441422197707140088;";
            byte[] bUserInfo = VeinApi.StrToGBKBytes(sUserInfo); //用户信息有汉字，需要转换为GBK编码
            //通过采集的3个特征融合为一个模板，也可以同时导入用户信息，没有用户信息bUserInfo为null就行
            String sTemp = VeinApi.FVCreateVeinTemp(ssChara[0], ssChara[1], ssChara[2], bUserInfo, bUserInfo.length);
            if (sTemp.length() > 10) {
                Message message = new Message();
                message.what=200;
                handler.sendMessage(message);
                PlayVoice(VeinApi.XG_VOICE_ENRORLL_SUCCESS, true);
                Log.i("TAG", "模板采集成功,采集的数据为:" + sTemp.toString());
                Log.i("TAG", "模板采集成功,采集的长度为:" + sTemp.length());
            } else {
                PlayVoice(VeinApi.XG_VOICE_ENRORLL_FAIL, true);
                Log.i("TAG", "模板融合失败:" + sTemp);
            }
        }else{
            Message message = new Message();
            message.what=400;
            handler.sendMessage(message);
        }
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
//                ShowMsgDlg("在设备验证用户", sThreadMsg);
            }
        }
    }

    public void starGetChar() {
        if (App.DevHandle <= 0) {
            Log.i("TAG", "请先连接设备");
            return;
        }
        //GetTemp(); //采集指静脉模板，通过获取3次特征融合，采集后的模板在sTemp
        if (iThreadRun == 0) {
            GetTempThread mGetTempThread = new GetTempThread();
            mGetTempThread.start();
        }
    }
    private void changImgAndTip(){
        veinImg.setImageResource(R.mipmap.icon_jinmaiyichu);
        veinTip.setText("请将手指移出右侧识别器");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                veinImg.setImageResource(R.mipmap.icon_jinmai);
                veinTip.setText("请将手指再次放入右侧识别器中");
            }
        },500);
    }
    private void showDilog(){

    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast.makeText(VeinActivity.this, "登记失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                  //  Toast.makeText(VeinActivity.this, "登记成功", Toast.LENGTH_SHORT).show();
                    dialog.setSomeMsg(1,"录入成功","继续登记","返回主页");
                    break;
                case 200:
                    registUser();
                    break;
                case 400:
                    dialog.setSomeMsg(0,"录入失败","返回主页","重新录入");
                    break;
            }
        }
    };
    public void registUser(){
        String userId = getIntent().getStringExtra("userId");
        String id = getIntent().getStringExtra("id");
        String password = getIntent().getStringExtra("password");
        Map<String,Object> maps =new HashMap<>();
        maps.put("termnum", Constant.TERM_ID);
        maps.put("account",id);
        maps.put("pwd",password);
        maps.put("usertype",1);
        maps.put("username",userId);
        OkhttpUtils.post(HostUrl.registUser,OkhttpUtils.mapToStr(maps),handler);
    }
}