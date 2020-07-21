package com.su.ehobesmallsamkoon.bean;

public class GoodMessage {
    private String goodName;
    private String specification;
    private String supplier;
    private String date;
    private String count;
    private int type;
    private int state;

    public GoodMessage() {
    }

    public GoodMessage(String goodName, String specification, String supplier, String date, String count, int type, int state) {
        this.goodName = goodName;
        this.specification = specification;
        this.supplier = supplier;
        this.date = date;
        this.count = count;
        this.type = type;
        this.state = state;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
