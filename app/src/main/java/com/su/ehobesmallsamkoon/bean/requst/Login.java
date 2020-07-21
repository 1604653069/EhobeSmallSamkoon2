package com.su.ehobesmallsamkoon.bean.requst;

public class Login {

    /**
     * account : string
     * fingerprintnum : 0
     * irisimgleft : string
     * irisimgright : string
     * logintype : 0
     * pwd : string
     * termnum : string
     * venadigitalis : string
     */

    private String account;
    private int fingerprintnum;
    private String irisimgleft;
    private String irisimgright;
    private int logintype;
    private String pwd;
    private String termnum;
    private String venadigitalis;

    public Login(String account, int fingerprintnum, String irisimgleft, String irisimgright, int logintype, String pwd, String termnum, String venadigitalis) {
        this.account = account;
        this.fingerprintnum = fingerprintnum;
        this.irisimgleft = irisimgleft;
        this.irisimgright = irisimgright;
        this.logintype = logintype;
        this.pwd = pwd;
        this.termnum = termnum;
        this.venadigitalis = venadigitalis;

    }

    public Login(String termId, String userName, String password, int i) {
        this.termnum = termId;
        this.account =userName;
        this.pwd =password;
        this.logintype = i;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getFingerprintnum() {
        return fingerprintnum;
    }

    public void setFingerprintnum(int fingerprintnum) {
        this.fingerprintnum = fingerprintnum;
    }

    public String getIrisimgleft() {
        return irisimgleft;
    }

    public void setIrisimgleft(String irisimgleft) {
        this.irisimgleft = irisimgleft;
    }

    public String getIrisimgright() {
        return irisimgright;
    }

    public void setIrisimgright(String irisimgright) {
        this.irisimgright = irisimgright;
    }

    public int getLogintype() {
        return logintype;
    }

    public void setLogintype(int logintype) {
        this.logintype = logintype;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getTermnum() {
        return termnum;
    }

    public void setTermnum(String termnum) {
        this.termnum = termnum;
    }

    public String getVenadigitalis() {
        return venadigitalis;
    }

    public void setVenadigitalis(String venadigitalis) {
        this.venadigitalis = venadigitalis;
    }
}
