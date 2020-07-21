package com.su.ehobesmallsamkoon.bean.respond;

import java.util.List;

public class LoginRespon {

    /**
     * code : 0
     * msg : ok
     * data : [{"ID":1,"DeptNum":"40324","UserType":11,"UserName":"管理员","DeptName":"手术室"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ID : 1
         * DeptNum : 40324
         * UserType : 11
         * UserName : 管理员
         * DeptName : 手术室
         */

        private int ID;
        private String DeptNum;
        private int UserType;
        private String UserName;
        private String DeptName;

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

        public String getDeptName() {
            return DeptName;
        }

        public void setDeptName(String DeptName) {
            this.DeptName = DeptName;
        }
    }
}
