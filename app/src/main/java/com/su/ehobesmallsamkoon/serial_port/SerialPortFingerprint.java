package com.su.ehobesmallsamkoon.serial_port;


import com.su.ehobesmallsamkoon.util.ChangeUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPort;
import me.jessyan.autosize.utils.LogUtils;

public class SerialPortFingerprint {

    private static volatile SerialPortFingerprint mSerialPort = null;
    private SerialPort serialPort = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private boolean isRunnable = false;

    public static SerialPortFingerprint getInstance() {
        if (mSerialPort == null) synchronized (SerialPortFingerprint.class) {
            if (mSerialPort == null) mSerialPort = new SerialPortFingerprint();
        }
        return mSerialPort;
    }

    /**
     * 打开串口
     *
     * @param pathName 串口名
     * @param baudRate 波特率
     */
    public void openSerialPort(String pathName, int baudRate) {
        try {
            if (serialPort == null) serialPort = new SerialPort(new File(pathName), baudRate, (0));
            if (inputStream == null) inputStream = serialPort.getInputStream();
            if (outputStream == null) outputStream = serialPort.getOutputStream();
            if (!isRunnable) {
                isRunnable = true;
                new Thread(new ReadRunnable()).start();
            }
            LogUtils.e("打开--串口成功");
        } catch (SecurityException e) {
            e.printStackTrace();
            LogUtils.e("打开--串口异常：您没有对RFID串行端口的读/写权限。" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("打开--串口异常：由于未知原因，无法打开RFID串口。" + e.toString());
        } catch (InvalidParameterException e) {
            e.printStackTrace();
            LogUtils.e("打开--串口异常：请先配置RFID串口。" + e.toString());
        }
    }

    /**
     * 关闭串口
     */
    public void closeSerialPort() {
        try {
            isRunnable = false;
            if (inputStream != null)
                inputStream.close();
            inputStream = null;
            if (outputStream != null)
                outputStream.close();
            outputStream = null;
            if (serialPort != null)
                serialPort.close();
            serialPort = null;
            LogUtils.e("关闭--串口成功");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("关闭--串口异常：" + e.toString());
        }
    }

    /**
     * 发送串口指令（字符串）
     *
     * @param order 指令集
     */
    public void sendSerialPort(String order) {
        byte[] bytes = ChangeUtils.HexToByteArr(order);
        try {
            if (bytes.length > 0) {
                if (outputStream == null) return;
                outputStream.write(bytes);
                outputStream.flush();
                LogUtils.e("发送--指令成功:" + order);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("发送--指令失败:" + e.toString());
        }

    }

    /**
     * 单开一线程，读取串口数据
     */
    class ReadRunnable implements Runnable {

        ByteArrayOutputStream arrayOutputStream;
        boolean isFeatures = false;
        int ack31Length = 0;

        @Override
        public void run() {
            arrayOutputStream = new ByteArrayOutputStream();
            while (isRunnable) {
                byte[] buffer = new byte[1024];
                try {
                    int size = inputStream.read(buffer);
                    if (size > 0) {
                        LogUtils.e("接收--指令成功:" + size + "个字节:" + ChangeUtils.ByteArrToHex(buffer, (0), size));
                        receiveInstructions(buffer, size);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtils.e("接收--指令失败:" + e.toString());
                }
            }
        }

        /**
         * 将实时获取到的字节码经过二次处理
         * (保证返回指令完整性)
         *
         * @param buffer 原始字节码
         * @param size   原始字节数组大小
         */
        void receiveInstructions(byte[] buffer, int size) {
            arrayOutputStream.write(buffer, (0), size);
            byte[] bytes = arrayOutputStream.toByteArray();
            int ack_size = bytes.length;
            int ack_end = bytes[ack_size - 1] & 0xFF;
            if (ack_end == 0xF5) if (ack_size >= 8) {
                if ((bytes[1] & 0xFF) == 0x31 && (bytes[4] & 0xFF) == 0x00) {
                    if (!isFeatures) {
                        String byte2Code = ChangeUtils.Byte2Hex(bytes[2]);
                        String byte3Code = ChangeUtils.Byte2Hex(bytes[3]);
                        ack31Length = ChangeUtils.HexToInt((byte2Code + byte3Code));
                        isFeatures = true;
                    }
                } else exposureInstructions(bytes);
            }
            if (isFeatures) if (ack_size == (ack31Length + 11)) exposureInstructions(bytes);
        }

        /**
         * 将获取到的字节码暴露处理
         *
         * @param bytes 处理后的字节码（拼接）
         */
        void exposureInstructions(byte[] bytes) {
            EventBus.getDefault().post(bytes);
            arrayOutputStream.reset();
            isFeatures = false;
            ack31Length = 0;
        }
    }

}
