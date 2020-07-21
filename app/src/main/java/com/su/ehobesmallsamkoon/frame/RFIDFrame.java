package com.su.ehobesmallsamkoon.frame;


import com.su.ehobesmallsamkoon.util.ChangeUtils;

import java.util.ArrayList;
import java.util.List;

public class RFIDFrame{
    int length = 28;
    public int in = 0;
    public int out = 0;
    public int type = 0;
    public String address="";
    public String data = "";
    private String time = "";
    private int id = 0;  //应急
    private List<String> inList;
    private List<String> outList;


    public RFIDFrame() {
    }

     public void addData(String source){
        this.in = ChangeUtils.HexToInt(source.substring(0, 4));
        this.out = ChangeUtils.HexToInt(source.substring(4, 8));
        this.type = Integer.parseInt( source.substring(8, 9));
        this.data +=source.substring(10);
     }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setInList(List<String> inList) {
        this.inList = inList;
    }

    public void setOutList(List<String> outList) {
        this.outList = outList;
    }

    public List<String> getInList() {
        if(inList!=null)
            return inList;
        List<String> rfCodes = new ArrayList<>();
        for (int i = 0; i <in; i++) {
            String code = data.substring(i*length,(i+1)*length);
            rfCodes.add(code);
        }
        return rfCodes;
    }

    public List<String> getOutList() {
        if(outList!=null)
            return outList;
        List<String> rfCodes = new ArrayList<>();
        for (int i = in; i <(in+out); i++) {
            String code = data.substring(i*length,(i+1)*length);
            rfCodes.add(code);
        }
        return rfCodes;
    }
    public void clearIn(){
        data = data.substring(in*length);
    }
    public void clearOut(){
        data = data.substring(0,in*length);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RFIDFrame{" +
                "in='" + in + '\'' +
                ", out='" + out + '\'' +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                ", time='" + time + '\'' +
                ", id='" + id + '\'' +
                ", data=" + data +
                '}';
    }

}
