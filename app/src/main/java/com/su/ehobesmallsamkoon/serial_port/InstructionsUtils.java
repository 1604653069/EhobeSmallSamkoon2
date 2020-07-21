package com.su.ehobesmallsamkoon.serial_port;


import com.su.ehobesmallsamkoon.app.Constant;
import com.su.ehobesmallsamkoon.util.CRC16Util;
import com.su.ehobesmallsamkoon.util.ChangeUtils;
import com.su.ehobesmallsamkoon.util.FilesUtils;

public class InstructionsUtils {
    /*数据包包头*/
    public final static String SFD = "584C4B4A";
    /*第一个01帧控制符，第二个01帧总数符，第三个01帧计数符*/
    public final static String FC_FT_FN = "010101";
    /*开门指令*/
    public final static String OPEN_DOOR = "01";
    /*开门应答帧*/
    public final static String DOOR_OPENED = "30";
    /*关门应答帧*/
    public final static String DOOR_CLOSED = "40";
    /*获取设置信息*/
    public final static String GET_SETTING = "05";
    /*设置指令*/
    public final static String SETTING = "06";
    /*获取选中的下位机*/
    public final static String CHOOSE_LIGHT = "07";
    /**/
    public final static String CLOSED_DATA = "80";
    /*获取柜内所有的标签数据*/
    public final static String ALL_DATA = "09";
    /*轮询*/
    public final static String CHECK = "0A";
    /**/
    public final static String REPORT = "E0";
    public final static String RESTART = "F0";
    public final static String READ = "0B";
    public final static String UPDATE = "1F";
    public final static String VERSION = "1E";
    public final static String UPDATE_ERROR = "EF";
    /*主板开机*/
    public final static byte R_RESTART = 0x0F;
    public final static byte R_DOOR_OPENED = 0x03;
    public final static byte R_OPEN_DOOR = 0x10;  //没用
    public final static byte R_DOOR_CLOSED = 0x04;
    public final static byte R_GET_SETTING = 0x50;
    public final static byte R_SETTING = 0x60;
    public final static byte R_CHOOSE_LIGHT = 0x70;
    public final static byte R_CLOSED_DATA = 0x08;
    public final static byte R_ALL_DATA =(byte) 0x90;
    public final static byte R_CHECK = (byte)0xA0;
    public final static byte R_REPORT = (byte)0x0E;
    public final static byte R_READ = (byte)0xB0;
    public final static byte R_UPDATE = (byte) 0x0F1;
    public final static byte R_UPDATE_ERROR = (byte) 0x0FE;
    public final static byte R_VERSION =(byte) 0x0E1;

    public static byte[] getInstruction(String commandType, String receiveAddress) {
        FilesUtils.saveSLogEasyToFile("发送:"+commandType+"到"+receiveAddress);
        String sFrame = FC_FT_FN + receiveAddress + Constant.ANDROID_ADDRESS + commandType + "0000";
        byte[] bytes = ChangeUtils.HexToByteArr(sFrame);
        int crc = CRC16Util.crc16(bytes);
        String check = CRC16Util.hexNumberFormat( crc, 4);
        return ChangeUtils.HexToByteArr(SFD + sFrame + check);
    }

    public static byte[] getInstruction(String commandType, String receiveAddress, String data) {

        String sFrame = FC_FT_FN + receiveAddress + Constant.ANDROID_ADDRESS + commandType +ChangeUtils.integerToHex(data.length()/2,4)+data;
        byte[] bytes = ChangeUtils.HexToByteArr(sFrame);
        int crc = CRC16Util.crc16(bytes);
        String check = CRC16Util.hexNumberFormat( crc, 4);
        return ChangeUtils.HexToByteArr(SFD + sFrame + check);
    }
    public static byte[] getInstruction(int totalCount,int currentCount,String commandType, String receiveAddress, String data) {

        String sFrame = "01"+ChangeUtils.integerToHex(totalCount,2)+ChangeUtils.integerToHex(currentCount,2) + receiveAddress + Constant.ANDROID_ADDRESS + commandType +ChangeUtils.integerToHex(data.length()/2,4)+data;
        byte[] bytes = ChangeUtils.HexToByteArr(sFrame);
        int crc = CRC16Util.crc16(bytes);
        String check = CRC16Util.hexNumberFormat( crc, 4);
        return ChangeUtils.HexToByteArr(SFD + sFrame + check);
    }


}
