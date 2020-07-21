package com.su.ehobesmallsamkoon.bean.requst;

public class UserParams {

    /**
     * termnum : 02032247
     * operateuserid : 1
     * index : 1
     * pagesize : 20
     */

    private String termnum;
    private int operateuserid;
    private int index;
    private int pagesize;

    public UserParams() {
    }

    public UserParams(String termnum, int operateuserid, int index, int pagesize) {
        this.termnum = termnum;
        this.operateuserid = operateuserid;
        this.index = index;
        this.pagesize = pagesize;
    }

    public String getTermnum() {
        return termnum;
    }

    public void setTermnum(String termnum) {
        this.termnum = termnum;
    }

    public int getOperateuserid() {
        return operateuserid;
    }

    public void setOperateuserid(int operateuserid) {
        this.operateuserid = operateuserid;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
}
