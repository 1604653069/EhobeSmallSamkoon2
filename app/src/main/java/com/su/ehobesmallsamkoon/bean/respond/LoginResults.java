package com.su.ehobesmallsamkoon.bean.respond;

public class LoginResults {

    /**
     * ID : 1
     * DeptNum : B0013
     * UserType : 1
     * UserName : aa
     */

    private int ID;
    private String DeptNum;
    private int UserType;
    private String UserName;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDeptNum() {
        return DeptNum;
    }

    public void setDeptNum(String DeptNum) {
        this.DeptNum = DeptNum;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int UserType) {
        this.UserType = UserType;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
}
