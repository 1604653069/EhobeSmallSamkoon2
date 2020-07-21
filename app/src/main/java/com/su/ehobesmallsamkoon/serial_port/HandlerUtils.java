package com.su.ehobesmallsamkoon.serial_port;


import android.annotation.SuppressLint;
import android.os.Looper;
import android.util.Log;

import com.su.ehobesmallsamkoon.frame.Frame;
import com.su.ehobesmallsamkoon.frame.RFIDFrame;
import com.su.ehobesmallsamkoon.frame.RFIDFramePan;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HandlerUtils {
    private static RFIDFrame rfidFrame = new RFIDFrame();
    private static RFIDFramePan rfidFramePan = new RFIDFramePan();
    private static int count = 0;

    @SuppressLint("CheckResult")
    public static void handlerFrame(Frame frame) {
        Observable.just(frame).observeOn(Schedulers.io()).subscribe(new Consumer<Frame>() {
            @Override
            public void accept(Frame frame) throws Exception {
                EventBus.getDefault().post(frame);
                switch (frame.commandType) {
                    case InstructionsUtils.R_DOOR_OPENED:
                        Log.i("TAG","智能柜打开了");
                        SerialPortUart.getInstance().sendSerialPort(InstructionsUtils.getInstruction(InstructionsUtils.DOOR_OPENED, frame.sendAddress), false);
                        break;
                    case InstructionsUtils.R_REPORT:
                        SerialPortUart.getInstance().sendSerialPort(InstructionsUtils.getInstruction(InstructionsUtils.REPORT, frame.sendAddress), false);
                        break;
                    case InstructionsUtils.R_DOOR_CLOSED:
                        Log.i("TAG","智能柜关上了");
                        SerialPortUart.getInstance().sendSerialPort(InstructionsUtils.getInstruction(InstructionsUtils.DOOR_CLOSED, frame.sendAddress), false);
                        break;
                    case InstructionsUtils.R_CLOSED_DATA:
                        //关闭时的数据
                        Log.i("TAG","获取到了关闭时的数据了");
                        if (frame.currentCount == 1) {
                            count = 0;
                            rfidFrame = new RFIDFrame();
                            SerialPortUart.getInstance().sendSerialPort(InstructionsUtils.getInstruction(InstructionsUtils.CLOSED_DATA, frame.sendAddress), false);
                        }
                        if (frame.currentCount < frame.totalCount) {
                            count++;
                            rfidFrame.addData(frame.dataField);
                        } else {
                            count++;
                            if (count == frame.totalCount) {
                                rfidFrame.addData(frame.dataField);
                                rfidFrame.setAddress(frame.sendAddress);
                                EventBus.getDefault().post(rfidFrame);
                            }
                        }
                        break;
                    case InstructionsUtils.R_ALL_DATA:
                        //获取智能柜中的数据
                        if (frame.currentCount == 1) {
                            count = 0;
                            rfidFramePan = new RFIDFramePan();
                        }
                        if (frame.currentCount < frame.totalCount) {
                            count++;
                            rfidFramePan.addData(frame.dataField);
                        } else {
                            count++;
                            if (count == frame.totalCount) {
                                rfidFramePan.addData(frame.dataField);
                                rfidFramePan.setAddress(frame.sendAddress);
                                EventBus.getDefault().post(rfidFramePan);
                            }
                        }
                        break;
                    case InstructionsUtils.R_RESTART:
                        //重启智能柜
                        SerialPortUart.getInstance().sendSerialPort(InstructionsUtils.getInstruction(InstructionsUtils.RESTART, frame.sendAddress), false);
                        new Thread() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                Log.i("TAG","重启智能柜");
                                Looper.loop();
                            }
                        }.start();
                        break;
                }
            }
        });

    }

}
