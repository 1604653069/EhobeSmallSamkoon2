package com.su.ehobesmallsamkoon.util;


import android.os.Handler;
import android.util.Log;

import com.su.ehobesmallsamkoon.frame.Frame;
import com.su.ehobesmallsamkoon.serial_port.HandlerUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPort;
import me.jessyan.autosize.utils.LogUtils;


/**
 * Created by Administrator on 2018/6/28.
 */

public class SerialPortUart {

    private static volatile SerialPortUart mSerialPortUart = null;
    private SerialPort serialPort = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private Handler handler = new Handler();
    private SendRunnable sendRunnable;
    private boolean isRunnable = false;
    private int readSize = 2048;
    private final int READ_BUF_MAX_LEN = 8192;
    private byte[] szDevReadBuf = new byte[READ_BUF_MAX_LEN];
    public static int totalLen = 0;
    private byte STX1 = (byte) 0x58;
    private byte STX2 = (byte) 0x4C;
    private byte STX3 = (byte) 0x4B;
    private byte STX4 = (byte) 0x4A;
    private int timeMin = 200;
    private int timeMax = 1700;
    private boolean isMax = false;


    public static SerialPortUart getInstance() {
        if (mSerialPortUart == null) {
            synchronized (SerialPortUart.class) {
                if (mSerialPortUart == null)
                    mSerialPortUart = new SerialPortUart();
            }
        }
        return mSerialPortUart;
    }

    public void openSerialPort(String pathname, int baudrate) {
        if(isRunnable)
            return;
        try {
            if (serialPort == null)
                serialPort = new SerialPort(new File(pathname), baudrate, 0);
            if (inputStream == null)
                inputStream = serialPort.getInputStream();
            if (outputStream == null)
                outputStream = serialPort.getOutputStream();
            isRunnable = true;
            new Thread(new ReadRunnable()).start();
            new Thread(new CheckRunnable()).start();
            Log.i("TAG","串口打开成功");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendSerialPort(byte[] sendData, boolean isRepeat) {
        sendRunnable = new SendRunnable(sendData, isRepeat);
        handler.post(sendRunnable);
    }


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
            totalLen = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMax(boolean max) {
        isMax = max;
    }

    class SendRunnable implements Runnable {
        byte[] sendData;
        int count = 0;
        boolean isRepeat;
        boolean hasReceive;

        public SendRunnable(byte[] sendData, boolean isRepeat) {
            this.sendData = sendData;
            this.isRepeat = isRepeat;
            hasReceive = false;
        }

        @Override
        public void run() {
            try {
                if (sendData.length > 0&&outputStream!=null) {
                    FilesUtils.saveSLogToFile("发送：" + ChangeUtils.ByteArrToHex(sendData)); //保存
                    LogUtils.e("发送：" + ChangeUtils.ByteArrToHex(sendData));
                    Log.i("TAG","发送:"+ChangeUtils.ByteArrToHex(sendData));
                    outputStream.write(sendData);
                    outputStream.flush();
                    if (isRepeat && !hasReceive) {
                        count++;
                        if (count < 3) {
                            if (isMax)
                                handler.postDelayed(sendRunnable, timeMax);
                            else
                                handler.postDelayed(sendRunnable, timeMin);
                        } else {
                            FilesUtils.saveSLogToFile("发送失败"); //保存
                            EventBus.getDefault().post(false);
                            Log.i("TAG","发送失败");
                            handler.removeCallbacks(sendRunnable);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadRunnable implements Runnable {
        @Override
        public void run() {
            while (isRunnable) {
                final byte[] buffer = new byte[readSize];
                try {
                    if (inputStream == null)
                        return;
                    final int size = inputStream.read(buffer);
                    if (size > 0) {
                        FilesUtils.saveSLogToFile("接收：" + ChangeUtils.ByteArrToHex(buffer, 0, size)); //保存
                        Log.i("TAG","接收：" + ChangeUtils.ByteArrToHex(buffer, 0, size));
                        doBuf(buffer, size);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class CheckRunnable implements Runnable {
        @Override
        public void run() {
            while (isRunnable) {
                if (totalLen >= 24) {
                    if (matchHead()) {
                        int dataLen = bytesToInt(szDevReadBuf, 20);
                        int packLen = dataLen + 24;
                        if (totalLen >= packLen) {
                            if (packLen > 2045) {
                                findHead();
                            } else {
                                byte[] result = new byte[packLen];
                                System.arraycopy(szDevReadBuf, 0, result, 0, packLen);
                                if (CRC16Util.checkCrc(result)) {
                                    if (sendRunnable != null)
                                        if (!sendRunnable.hasReceive) {
                                            sendRunnable.hasReceive = true;
                                            handler.removeCallbacks(sendRunnable);
                                        }
                                    Frame frame = new Frame(result, dataLen);
                                    HandlerUtils.handlerFrame(frame);
                                    FilesUtils.saveSLogEasyToFile("接收:" + frame.toString());
                                    LogUtils.e("接收:" + frame.toString());
                                    Log.i("TAG","接收"+frame.toString());
                                    totalLen -= packLen;
                                    if (totalLen <= 0) {
                                        totalLen = 0;
                                    } else {
                                        doBuf(null, packLen);
                                    }
                                } else {
                                    FilesUtils.saveSLogToFile("校验失败：" + ChangeUtils.ByteArrToHex(result)); //保存
                                    Log.i("TAG","校验失败"+ChangeUtils.ByteArrToHex(result));
                                    String error = "error";
                                    EventBus.getDefault().post(error);
                                    totalLen -= 10;
                                    if (totalLen <= 0) {
                                        totalLen = 0;
                                    } else {
                                        doBuf(null, 10);
                                    }
                                    findHead();

                                }
                            }
                        }
                    } else {
                        //从新寻找头
                        findHead();
                    }
                }
            }
        }
    }

    synchronized void doBuf(byte[] data, int len) {
        if (data == null) {
            System.arraycopy(szDevReadBuf, len, szDevReadBuf, 0, totalLen);
        } else {
            if (totalLen + len > READ_BUF_MAX_LEN) {
                totalLen = 0;
                return;
            }
            System.arraycopy(data, 0, szDevReadBuf, totalLen, len);
            totalLen += len;
        }
    }
    public void clearBuf(){
        totalLen = 0;
    }
    public int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) (((src[offset] & 0xFF) << 8)
                | ((src[offset + 1] & 0xFF)));
        return value;
    }

    private boolean matchHead() {
        if (STX1 != szDevReadBuf[0]) {
            return false;
        }
        if (STX2 != szDevReadBuf[1]) {
            return false;
        }
        if (STX3 != szDevReadBuf[2]) {
            return false;
        }
        if (STX4 != szDevReadBuf[3]) {
            return false;
        }
        return true;
    }

    private void findHead() {
        int nowSTXLen = 0;
        for (int i = 0; i < totalLen; i++) {
            if (i + 3 < totalLen && STX1 == szDevReadBuf[i] && STX2 == szDevReadBuf[i + 1] && STX3 == szDevReadBuf[i + 2] && STX4 == szDevReadBuf[i + 3]) {
                nowSTXLen = i;
                break;
            }
        }
        if (nowSTXLen != 0) {
            FilesUtils.saveSLogToFile("头异常：" + ChangeUtils.ByteArrToHex(szDevReadBuf, 0, totalLen)); //保存
            totalLen -= nowSTXLen;
            System.arraycopy(szDevReadBuf, nowSTXLen, szDevReadBuf, 0, totalLen);
            FilesUtils.saveSLogToFile("正常：" + ChangeUtils.ByteArrToHex(szDevReadBuf, 0, totalLen)); //保存

        } else {
            //没有新头匹配的时候，继续缓存
            return;
        }
    }

}

