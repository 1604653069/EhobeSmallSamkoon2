package com.su.ehobesmallsamkoon;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.common.base.Verify;
import com.xgzx.veinmanager.VeinApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*TODO 指静脉部分：
        特征值传输：step1:用户注册设置（模板采集）
                  step2:新的特征值上传到后端，后端通知客户端获取新的特征值
                  step3:安卓端获取新的特征值保存到本地
        特征值验证：step1:：用户登录（特征采集）
                  step2:1:N验证(从本地读取特征值)*/
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.connection)
    Button connection;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.verification)
    Button verification;

    public static int iThreadRun = 0;
    public static long DevHandle = 0; //设备操作句柄
    boolean mUsbPerMission = false; //USB设备权限

    private SoundPool soundPool; //本机语音播放
    Map<Integer, Integer> soundMap = new HashMap<Integer, Integer>();
    private Handler mHandler = new MyHandle();
    UsbManager mUsbManager;
    UsbDevice mUsbDev;
    public static final String ACTION_DEVICE_PERMISSION = "com.linc.USB_PERMISSION";

    String sThreadMsg = "";
    AlertDialog MsgDlg;

    public static String sTemp = ""; //当前采集的模板
    public static ArrayList<String> temps = new ArrayList<>();
    public static String sChara = ""; //当前采集的特征

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        soundPool = new SoundPool(12, AudioManager.STREAM_SYSTEM, 5);
        soundMap.put(1, soundPool.load(this, R.raw.enroll_success_00, 1)); //登记成功
        soundMap.put(2, soundPool.load(this, R.raw.enroll_fail_02, 1)); //登记失败
        soundMap.put(3, soundPool.load(this, R.raw.verify_success_33, 1)); //验证成功
        soundMap.put(4, soundPool.load(this, R.raw.verify_fail_32, 1)); //验证失败
        soundMap.put(5, soundPool.load(this, R.raw.put_finger_27, 1)); //请放手指
        soundMap.put(6, soundPool.load(this, R.raw.put_again_23, 1)); //请再放一次
        soundMap.put(7, soundPool.load(this, R.raw.put_right_26, 1)); //请正确放置手指
        soundMap.put(8, soundPool.load(this, R.raw.b_35, 1)); //滴1
        soundMap.put(9, soundPool.load(this, R.raw.b_36, 1)); //滴2
        soundMap.put(10, soundPool.load(this, R.raw.bb_37, 1)); //滴滴1
        soundMap.put(11, soundPool.load(this, R.raw.bb_38, 1)); //滴滴2
        MsgDlg = new AlertDialog.Builder(this)
                .setTitle("信息对话框")//设置对话框的标题
                .setMessage("提示信息")//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", null).create();
        SerachUSB();
        VeinApi.FVSetDebug(1); //开启调试信息输出，调试信息会输出到/sdcard/Debug.txt

    }

    @OnClick({R.id.connection, R.id.register,R.id.verification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.connection:
                //TODO 连接设备
                Log.e(TAG, "onClick: 连接设备");
                long ret = 0;
                if (DevHandle > 0) {
                    Log.e(TAG, "设备已处于连接状态");
                    return;
                }
                if (mUsbPerMission == false) {
                    //int dev = SerachUSB();
                }
                if (mUsbPerMission) {
                    if (!mUsbManager.hasPermission(mUsbDev)) {
                        Log.e(TAG, "设备没有权限");
                    } else {
                        ret = VeinApi.initUsbCommunication(mUsbDev, mUsbManager);
//                        VeinApi.PrintfDebug("initUsbCommunication:" + ret);
                    }
                } else {
                    //String sDev = "IP:192.168.31.200,PORT:8080"; //通过SO主动连接网络设备
                    //String sDev = "COM:ttyS1,BAUD:115200";// 通过SO连接串口设备,需要安卓有ROOT权限或串口设备文件有读写权限
                    //String sDev = String.format("VID=%d,PID=%d", 0x2109, 0x7638);//通过SO连接指定PID和VID USB设备，安卓系统要有ROOT权限或USB设备文件有读写权限
                    ChmodUsbRW();
                    String sDev = "USB";//通过SO连接USB设备，安卓系统要有ROOT权限或USB设备文件有读写权限
                    Log.e(TAG, sDev);
                    ret = VeinApi.FVConnectDev(sDev, "00000000"); //默认连接密码是8个字符0
                }
                if (ret > 0) {
                    DevHandle = ret;
                    //获取设备名称及相关设置参数
                    String sParam = VeinApi.GetDevParam(DevHandle);
                    //Log.e(TAG,"设备信息:" + sParam);

                    Log.e(TAG, "设备信息：" + sParam);

                    VeinApi.PrintfDebug("正在读取设备调试信息...");
                    ret = VeinApi.FVGetDevDebugInfo(DevHandle, "./sdcard/DevDebug.txt");
                    if (ret > 0) {
                        VeinApi.PrintfDebug("读取调试信息成功");
                    } else {
                        VeinApi.PrintfDebug("读取调试信息失败");
                    }

                }
                break;
            case R.id.register:
                //TODO 采集模板
//                if (DevHandle <= 0) {
//                    DebugMsg("请先连接设备");
//                    break;
//                }
//                if (iThreadRun == 0) {
//                    DevAddUserThread mDevAddUserThread = new DevAddUserThread();
//                    mDevAddUserThread.start();
//                }
                if(DevHandle <= 0)
                {
                    DebugMsg("请先连接设备");
                    break;
                }
                //GetTemp(); //采集指静脉模板，通过获取3次特征融合，采集后的模板在sTemp
                if(iThreadRun == 0) {
                    GetTempThread mGetTempThread = new GetTempThread();
                    mGetTempThread.start();
                }
                break;
            case R.id.verification:
                //TODO 验证用户: 采集特征，1:N验证
                GetChara();
//                if(DevHandle <= 0)
//                {
//                    DebugMsg("请先连接设备");
//                    break;
//                }
//                if(iThreadRun == 0) {
//                    DevVerifyThread mDevVerifyThread = new DevVerifyThread();
//                    mDevVerifyThread.start();
//                }

                break;
        }
    }

    //采集模板线程
    class GetTempThread extends Thread {
        @Override
        public void run() {
            iThreadRun = 1; //线程在运行
            GetTemp();
            iThreadRun = 0; //线程结束
            Message Msg = mHandler.obtainMessage(1000);
            mHandler.sendMessage(Msg);
        }
    }

    //采集特征方法
    public void GetChara() {
        if(DevHandle <= 0)
        {
            DebugMsg("请先连接设备");
            return;
        }
        PlayVoice(VeinApi.XG_VOICE_PUTFINGER, true);
        sChara = VeinApi.GetVeinChara(DevHandle, 10000); //通过指静脉设备采集特征，等待手指放入超时为6秒
        if(sChara.length() < 10)
        {
            int ret = 0;
            try {
                ret = Integer.parseInt(sChara);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            PlayVoice(VeinApi.XG_VOICE_BEEP11, true);
            if(ret == -16) {
                sThreadMsg = "此设备不支持特征采集";
                DebugMsg(sThreadMsg);
            } else if(ret == -11) {
                sThreadMsg = "手指检测超时";
                DebugMsg(sThreadMsg);
            } else if(ret == -17) {
                sThreadMsg = "请正确放置手指";
                VeinApi.PrintfDebug(sThreadMsg);
                try {
                    Thread.sleep(500); //等语音播完
                } catch (InterruptedException e) {
                }
                PlayVoice(VeinApi.XG_VOICE_PUTFINGER_RIGHT, true);
            } else {
                sThreadMsg = "特征采集失败:" + sChara;
                DebugMsg(sThreadMsg);
            }
        } else {
            PlayVoice(VeinApi.XG_VOICE_BEEP1, true);
            sThreadMsg = "特征采集成功";
            DebugMsg(sThreadMsg);
            boolean find = false;
            for (String temp : temps) {
                if (Verify(temp)) {
                    Log.e(TAG, "Verify: 验证成功" );
                    PlayVoice(VeinApi.XG_VOICE_VERIFY_SUCCESS, true);
                    find = true;
                    break;
                }
            }
            if (!find) {
                Log.e(TAG, "Verify: 验证失败" );
                PlayVoice(VeinApi.XG_VOICE_VERIFY_FAIL, true);
            }
//            if(ServerSocket > 0)
//            {
//                VeinApi.FVSocketSendPack(ServerSocket, 0x02, sChara);
//            }
        }
    }

    //采集模板方法
    public void GetTemp()
    {
        int MaxCharaNum = 3;
        int error = 0;
        if(DevHandle <= 0)
        {
            DebugMsg("请先连接设备");
            return;
        }
        String[] ssChara = new String[MaxCharaNum];
        String ssTemp = "";
        int CharaNum = 0;
        PlayVoice(VeinApi.XG_VOICE_PUTFINGER, true);
        while(true) {
            ssChara[CharaNum] = VeinApi.GetVeinChara(DevHandle, 6000);//通过指静脉设备采集特征，等待手指放入超时为6秒
            if (ssChara[CharaNum].length() < 10) {
                PlayVoice(VeinApi.XG_VOICE_BEEP11, true);
                try {
                    error = Integer.parseInt(ssChara[CharaNum]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if(error == -16) {
                    sThreadMsg = "此设备不支持特征采集";
                    DebugMsg(sThreadMsg);
                    break;
                } else if(error == -11) {
                    sThreadMsg = "手指检测超时";
                    DebugMsg(sThreadMsg);
                    break;
                } else if(error == -17) { //特征采集失败可以继续采集下一次
                    sThreadMsg = "请正确放置手指";
                    VeinApi.PrintfDebug(sThreadMsg);
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
                    sThreadMsg = "采集失败:" + ssChara[CharaNum];
                    DebugMsg(sThreadMsg);
                    break;
                }
            } else {
                error = 0;
                PlayVoice(VeinApi.XG_VOICE_BEEP1, true);

                //通过增加特征的这个函数来创建模板，一个模板最多可以增加6个特征，等同于FVCreateVeinTemp
                ssTemp = VeinApi.FVAddCharaToTemp(ssTemp, ssChara[CharaNum], null, 0);
                if (ssTemp.length() < 10) {
                    sThreadMsg = "增加特征失败:" + ssTemp;
                    DebugMsg(sThreadMsg);
                }

                CharaNum++;
                if(CharaNum == 2)
                {
                    //检测2次采集的特征是不是同一个手指，可以不检测
                    long ret = VeinApi.FVCharaMatch(ssChara[0], ssChara[1], 60);
                    if(ret != 0)
                    {
                        DebugMsg("不是同一根手指");
                    }
                }
            }
            if(CharaNum >= MaxCharaNum)
            {
                break;
            }
            PlayVoice(VeinApi.XG_VOICE_PUTFINGER_AGAIN, true);
            //等待手指拿开采集下一次
            VeinApi.CheckFinger(DevHandle, 10000, 0);
        }
        if(error == 0) {
            VeinApi.PrintfDebug("3次特征采集完成,准备融合");
            //通过采集的3个特征融合为一个模板，也可以同时导入用户信息，没有用户信息bUserInfo为null就行
            sTemp = VeinApi.FVCreateVeinTemp(ssChara[0], ssChara[1], ssChara[2], null, 0);
            Log.e("模板", ""+sTemp );
            if (sTemp.length() > 10) {
                PlayVoice(VeinApi.XG_VOICE_ENRORLL_SUCCESS, true);
                sThreadMsg = "模板采集成功,长度:" + sTemp.length();
                DebugMsg(sThreadMsg);
                temps.add(sTemp);
            } else {
                PlayVoice(VeinApi.XG_VOICE_ENRORLL_FAIL, true);
                sThreadMsg = "模板融合失败:" + sTemp;
                DebugMsg(sThreadMsg);
            }
        }
    }

    //验证
    public boolean Verify(String temp)
    {
        String sStudyTemp = VeinApi.FVVerifyUser(temp, sChara, 78); //1:1验证,分数70-80之间，分数越低通过率越高
        if(sStudyTemp.length() > 10)
        {
            return true;
        } else {
            return false;
        }
    }


    //这个要有ROOT权限才能执行
    public void ChmodUsbRW() {
        String command = "chmod -R 777 /dev/bus/usb";
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"su", "-c", command});
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //如果安卓系统没有ROOT权限，则可以用此方法在APP里获取权限，但只能使用APP的通讯函数，不能使用JNI的通讯API
    public int SerachUSB() {
        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_DEVICE_PERMISSION), 0);
        IntentFilter permissionFilter = new IntentFilter(ACTION_DEVICE_PERMISSION);
        registerReceiver(mUsbReceiver, permissionFilter);

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceHashMap = mUsbManager.getDeviceList();
        Iterator<UsbDevice> iterator = deviceHashMap.values().iterator();
        Log.e(TAG, "hasNext:" + iterator.hasNext());

        int FoundUserDev = 0;
        while (iterator.hasNext()) {
            mUsbDev = iterator.next();
            Log.e(TAG, "device name: " + mUsbDev.getDeviceName() + ",device product id:"
                    + mUsbDev.getProductId() + "vendor id:" + mUsbDev.getVendorId());
            if ((mUsbDev.getProductId() & 0xff00) == 0x7600 && (mUsbDev.getVendorId() == 0x2109 || mUsbDev.getVendorId() == 0x200d)) {
                //if (device.getProductId() == 0x7638 && device.getVendorId() == 0x7B09) {
                if (mUsbManager.hasPermission(mUsbDev)) {
                    Log.e(TAG, "hasPermission..........");
                    mUsbPerMission = true; //已经有权限了
                } else {
                    Log.e(TAG, "requestPermission..........");
                    mUsbManager.requestPermission(mUsbDev, mPermissionIntent); //还没有权限，通过用户对话框获取授权
                }
                FoundUserDev++;
                break;
            } else {
                Log.e(TAG, "no our device, pass...");
                continue;
            }
        }
        if (FoundUserDev > 0) {
            Log.e(TAG, "发现USB设备");
        } else {
            Log.e(TAG, "未发现USB设备");
        }
        return FoundUserDev;
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e(TAG, "BroadcastReceiver in");
            if (ACTION_DEVICE_PERMISSION.equals(action)) {
                synchronized (this) {
                    mUsbDev = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (mUsbDev != null) {
                            Log.e(TAG, "device name: " + mUsbDev.getDeviceName() + ",device product id:"
                                    + mUsbDev.getProductId() + "vendor id:" + mUsbDev.getVendorId());
                            Log.e(TAG, "getInterfaceCount:" + mUsbDev.getInterfaceCount());
                            Log.e(TAG, "USB获取权限成功");
                            mUsbPerMission = true;
                        }
                    } else {
                        Log.e(TAG, "USB获取权限失败");
                    }
                }
            }
        }
    };

    //打印信息
    public void DebugMsg(String sMsg) {
        VeinApi.PrintfDebug(sMsg);
        if (iThreadRun == 0) { //在线程里不能用Toast
            Toast.makeText(getApplicationContext(), sMsg, Toast.LENGTH_SHORT).show();
        } else {
            //Looper.prepare();
            //Toast.makeText(getApplicationContext(), sMsg, Toast.LENGTH_SHORT).show();
            //Looper.loop();
        }
    }

    protected void PlayVoice(int id, boolean DevPlay) {
        if (DevPlay)
            VeinApi.PlayDevSound(DevHandle, id);
        switch (id) {
            case VeinApi.XG_VOICE_ENRORLL_SUCCESS:
                soundPool.play(soundMap.get(1), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_ENRORLL_FAIL:
                soundPool.play(soundMap.get(2), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_VERIFY_SUCCESS:
                soundPool.play(soundMap.get(3), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_VERIFY_FAIL:
                soundPool.play(soundMap.get(4), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_PUTFINGER:
                soundPool.play(soundMap.get(5), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_PUTFINGER_AGAIN:
                soundPool.play(soundMap.get(6), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_PUTFINGER_RIGHT:
                soundPool.play(soundMap.get(7), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_BEEP1:
                soundPool.play(soundMap.get(8), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_BEEP2:
                soundPool.play(soundMap.get(9), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_BEEP11:
                soundPool.play(soundMap.get(10), 1, 1, 1, 0, 1);
                break;
            case VeinApi.XG_VOICE_BEEP22:
                soundPool.play(soundMap.get(11), 1, 1, 1, 0, 1);
                break;
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
                ShowMsgDlg("模板采集", sThreadMsg);
            }
            if (msg.what == 1001) {
                ShowMsgDlg("特征采集", sThreadMsg);
            }
            if (msg.what == 1002) {
                ShowMsgDlg("图像采集", sThreadMsg);
            }
            if (msg.what == 1003) {
                ShowMsgDlg("在设备增加用户", sThreadMsg);
            }
            if (msg.what == 1004) {
                ShowMsgDlg("在设备验证用户", sThreadMsg);
            }
        }
    }

    public void ShowMsgDlg(String Title, String Msg) {
        if (MsgDlg.isShowing()) return;
        MsgDlg.setTitle(Title);
        MsgDlg.setMessage(Msg);
        MsgDlg.show();
    }
}
