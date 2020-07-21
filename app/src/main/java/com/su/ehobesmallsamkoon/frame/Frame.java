package com.su.ehobesmallsamkoon.frame;


import com.su.ehobesmallsamkoon.util.ChangeUtils;

public class Frame {
    public byte commandType;
    public int totalCount;
    public int currentCount;
    public String receiveAddress;
    public String sendAddress;
    public String dataField;
    public Frame(  byte[] frame,int dataLen){
        this.commandType =frame[19];
        this.totalCount = frame[5] & 0xFF;
        this.currentCount = frame[6] & 0xFF;
        this.receiveAddress = ChangeUtils.ByteArrToHex(frame,7,13);
        this.sendAddress = ChangeUtils.ByteArrToHex(frame,13,19);
        this.dataField = "";
        if(dataLen!=0)
            dataField = ChangeUtils.ByteArrToHex(frame,22,22+dataLen);
    }



    @Override
    public String toString() {
        return "Frame{" +
                "commandType='" + ChangeUtils.Byte2Hex(commandType) + '\'' +
                ", receiveAddress='" + receiveAddress + '\'' +
                ", sendAddress='" + sendAddress + '\'' +
                ", dataField=" + dataField +'\''+
                ", totalCount='" + totalCount + '\'' +
                ", currentCount='" + currentCount  +
                '}';
    }
}
