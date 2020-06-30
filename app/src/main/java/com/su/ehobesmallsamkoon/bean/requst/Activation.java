package com.su.ehobesmallsamkoon.bean.requst;

public class Activation {

    /**
     * termflag : 1
     * termtype : 2
     * termnum : 02032247
     * deptnum : B0017
     * termipaddress : 192.168.1.2
     * termotherinfo : ddd
     */

    private int termflag;
    private int termtype;
    private String termnum;
    private String deptnum;
    private String termipaddress;
    private String termotherinfo;

    public Activation(int termflag, int termtype, String termnum, String deptnum, String termipaddress) {
        this.termflag = termflag;
        this.termtype = termtype;
        this.termnum = termnum;
        this.deptnum = deptnum;
        this.termipaddress = termipaddress;
    }

    public int getTermflag() {
        return termflag;
    }

    public void setTermflag(int termflag) {
        this.termflag = termflag;
    }

    public int getTermtype() {
        return termtype;
    }

    public void setTermtype(int termtype) {
        this.termtype = termtype;
    }

    public String getTermnum() {
        return termnum;
    }

    public void setTermnum(String termnum) {
        this.termnum = termnum;
    }

    public String getDeptnum() {
        return deptnum;
    }

    public void setDeptnum(String deptnum) {
        this.deptnum = deptnum;
    }

    public String getTermipaddress() {
        return termipaddress;
    }

    public void setTermipaddress(String termipaddress) {
        this.termipaddress = termipaddress;
    }

    public String getTermotherinfo() {
        return termotherinfo;
    }

    public void setTermotherinfo(String termotherinfo) {
        this.termotherinfo = termotherinfo;
    }
}
