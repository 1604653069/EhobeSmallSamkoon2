package com.su.ehobesmallsamkoon.app;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.su.ehobesmallsamkoon.base.BaseApplication;
import com.xgzx.veinmanager.VeinApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import static com.su.ehobesmallsamkoon.MainActivity.ACTION_DEVICE_PERMISSION;

public class App extends BaseApplication {
    private static final String TAG = "App";
    private static Context mContext;
    private UsbManager mUsbManager;
    private boolean mUsbPerMission = false; //USB设备权限
    private UsbDevice mUsbDev;
    private static int iThreadRun = 0;
    public static long DevHandle = 0; //设备操作句柄
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        SerachUSB();
    }

    private void connectDev(){
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
                Log.i("TAG","权限已获取");
                ret = VeinApi.initUsbCommunication(mUsbDev, mUsbManager);
//                        VeinApi.PrintfDebug("initUsbCommunication:" + ret);
            }
        } else {
            ChmodUsbRW();
            String sDev = "USB";//通过SO连接USB设备，安卓系统要有ROOT权限或USB设备文件有读写权限
            Log.e(TAG, sDev);
            ret = VeinApi.FVConnectDev(sDev, "00000000"); //默认连接密码是8个字符0
        }
        if (ret > 0) {
            Log.e(TAG, "onClick: 连接设备");
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
                            //TODO 连接设备
                            connectDev();
                        }
                    } else {
                        Log.e(TAG, "USB获取权限失败");
                    }
                }
            }
        }
    };

    //如果安卓系统没有ROOT权限，则可以用此方法在APP里获取权限，但只能使用APP的通讯函数，不能使用JNI的通讯API
    private int SerachUSB() {
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
                    connectDev();
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

    public static Context getContext() {
        return mContext;
    }
}
