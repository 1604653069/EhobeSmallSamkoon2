package com.su.ehobesmallsamkoon.bean.respond;

public class Dept {
    /**
     * DeptName : 神经内科分诊台
     * DeptNum : F000001
     * DeptType : 3
     */

    private String DeptName;
    private String DeptNum;
    private String DeptType;

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String DeptName) {
        this.DeptName = DeptName;
    }

    public String getDeptNum() {
        return DeptNum;
    }

    public void setDeptNum(String DeptNum) {
        this.DeptNum = DeptNum;
    }

    public String getDeptType() {
        return DeptType;
    }

    public void setDeptType(String DeptType) {
        this.DeptType = DeptType;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "DeptName='" + DeptName + '\'' +
                ", DeptNum='" + DeptNum + '\'' +
                ", DeptType='" + DeptType + '\'' +
                '}';
    }
}
