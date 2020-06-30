package com.su.ehobesmallsamkoon.util;

import android.os.Build;

import java.io.File;
import java.io.FileWriter;

public class PowerUtils {
    public static void startMPower(){
        if (!Build.MANUFACTURER.equals("alps")) {
            if (Build.MODEL.equals("msm8953 for arm64")) {
                ZstBarcodeCtrl.getInstance().set_gpio(0, 99);
                ZstBarcodeCtrl.getInstance().set_gpio(1, 99);
            } else {
                ZstBarcodeCtrl.getInstance().set_gpio(0, 75);
                ZstBarcodeCtrl.getInstance().set_gpio(1, 75);
            }
        }
    }
    public static void stopMPower(){
        if (!Build.MANUFACTURER.equals("alps")) {
            if (Build.MODEL.equals("msm8953 for arm64")) {
                ZstBarcodeCtrl.getInstance().set_gpio(0, 99);
            } else {
                ZstBarcodeCtrl.getInstance().set_gpio(0, 75);
            }
        }
    }
    public static void startRfPowerNew(){
        //启动串口电源
        if (Build.MANUFACTURER.equals("alps")) {
            try {
                FileWriter localFileWriterOn = new FileWriter(new File(
                        "/proc/gpiocontrol/set_uhf"));
                localFileWriterOn.write("1");
                localFileWriterOn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void startRfPowerOld(){
        if (!Build.MANUFACTURER.equals("alps")) {
            if (Build.MODEL.equals("msm8953 for arm64")) {
                ZstBarcodeCtrl.getInstance().set_gpio(0, 98);
                ZstBarcodeCtrl.getInstance().set_gpio(1, 66);
            } else {
                ZstBarcodeCtrl.getInstance().set_gpio(0, 81);
                ZstBarcodeCtrl.getInstance().set_gpio(0, 113);
            }
        }
    }
    public static void  closeRfPowerNew(){
        //关闭串口电源
        if (Build.MANUFACTURER.equals("alps")) {
            try {
                FileWriter localFileWriterOn = new FileWriter(new File(
                        "/proc/gpiocontrol/set_uhf"));
                localFileWriterOn.write("0");
                localFileWriterOn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void  closeRfPowerOld(){
        //关闭串口电源
        if (!Build.MANUFACTURER.equals("alps"))  {
            if (Build.MODEL.equals("msm8953 for arm64")) {
                ZstBarcodeCtrl.getInstance().set_gpio(1, 98);
                ZstBarcodeCtrl.getInstance().set_gpio(0, 66);
            } else {
                ZstBarcodeCtrl.getInstance().set_gpio(1, 81);
                ZstBarcodeCtrl.getInstance().set_gpio(1, 113);
            }
            ZstBarcodeCtrl.getInstance().closeZstBarcodeCtrl();
        }
    }
}

