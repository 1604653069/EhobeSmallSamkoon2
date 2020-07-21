package com.su.ehobesmallsamkoon.bean.respond;

import java.util.List;

public class UserInform {

    /**
     * data : [{"DeptNum":"40324","UserName":"管理员","FingerTwo":"","UserFlag":1,"IrisImgLeft":"yes","FingerprintNumTwo":null,"FingerprintNumOne":2,"ID":1,"DeptName":"手术室","FingerOne":"yes","UserAccount":"1","UserType":11,"IrisImgRight":"yes"},{"DeptNum":"40323","UserName":"admin","FingerTwo":"","UserFlag":1,"IrisImgLeft":"","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":4,"DeptName":"麻醉科","FingerOne":"","UserAccount":"0","UserType":11,"IrisImgRight":""},{"DeptNum":"40324","UserName":"阿道夫","FingerTwo":"","UserFlag":1,"IrisImgLeft":"yes","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":5,"DeptName":"手术室","FingerOne":"","UserAccount":"9935","UserType":5,"IrisImgRight":"yes"},{"DeptNum":"40324","UserName":"范德萨","FingerTwo":"","UserFlag":1,"IrisImgLeft":"","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":6,"DeptName":"手术室","FingerOne":"","UserAccount":"9936","UserType":8,"IrisImgRight":""},{"DeptNum":"40324","UserName":"无群","FingerTwo":"","UserFlag":1,"IrisImgLeft":"","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":7,"DeptName":"手术室","FingerOne":"","UserAccount":"9937","UserType":5,"IrisImgRight":""},{"DeptNum":"40324","UserName":"红果果","FingerTwo":"","UserFlag":1,"IrisImgLeft":"","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":8,"DeptName":"手术室","FingerOne":"","UserAccount":"9938","UserType":1,"IrisImgRight":""},{"DeptNum":"40324","UserName":"二二","FingerTwo":"","UserFlag":1,"IrisImgLeft":"","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":9,"DeptName":"手术室","FingerOne":"","UserAccount":"9939","UserType":10,"IrisImgRight":""},{"DeptNum":"40324","UserName":"太热","FingerTwo":"","UserFlag":1,"IrisImgLeft":"","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":10,"DeptName":"手术室","FingerOne":"","UserAccount":"9940","UserType":4,"IrisImgRight":""},{"DeptNum":"40324","UserName":"路径","FingerTwo":"","UserFlag":1,"IrisImgLeft":"","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":11,"DeptName":"手术室","FingerOne":"","UserAccount":"9941","UserType":3,"IrisImgRight":""},{"DeptNum":"40324","UserName":"黄秀花","FingerTwo":"yes","UserFlag":1,"IrisImgLeft":"","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":12,"DeptName":"手术室","FingerOne":"yes","UserAccount":"9955","UserType":6,"IrisImgRight":""},{"DeptNum":"40324","UserName":"aa","FingerTwo":"yes","UserFlag":1,"IrisImgLeft":"yes","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":13,"DeptName":"手术室","FingerOne":"yes","UserAccount":"2","UserType":10,"IrisImgRight":"yes"},{"DeptNum":"40324","UserName":"庄慧玲","FingerTwo":"yes","UserFlag":1,"IrisImgLeft":"","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":14,"DeptName":"手术室","FingerOne":"yes","UserAccount":"9966","UserType":2,"IrisImgRight":""},{"DeptNum":"40324","UserName":"刘惜花","FingerTwo":"yes","UserFlag":1,"IrisImgLeft":"","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":15,"DeptName":"手术室","FingerOne":"yes","UserAccount":"9977","UserType":7,"IrisImgRight":""},{"DeptNum":"40324","UserName":"林元相","FingerTwo":"","UserFlag":1,"IrisImgLeft":"","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":17,"DeptName":"手术室","FingerOne":"","UserAccount":"0265","UserType":6,"IrisImgRight":""},{"DeptNum":"40324","UserName":"林元泽","FingerTwo":"","UserFlag":1,"IrisImgLeft":"","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":24,"DeptName":"手术室","FingerOne":"","UserAccount":"0247","UserType":6,"IrisImgRight":""},{"DeptNum":"40324","UserName":"林艾羽","FingerTwo":"","UserFlag":-1,"IrisImgLeft":"","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":25,"DeptName":"手术室","FingerOne":"","UserAccount":"0083","UserType":1,"IrisImgRight":""},{"DeptNum":"40324","UserName":"许颖颖","FingerTwo":"yes","UserFlag":1,"IrisImgLeft":"","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":27,"DeptName":"手术室","FingerOne":"yes","UserAccount":"47","UserType":11,"IrisImgRight":""},{"DeptNum":"40323","UserName":"root","FingerTwo":"","UserFlag":1,"IrisImgLeft":"","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":28,"DeptName":"麻醉科","FingerOne":"","UserAccount":"root","UserType":11,"IrisImgRight":""},{"DeptNum":"40324","UserName":"学习","FingerTwo":"","UserFlag":1,"IrisImgLeft":"","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":29,"DeptName":"手术室","FingerOne":"","UserAccount":"8742","UserType":1,"IrisImgRight":""},{"DeptNum":"20103","UserName":"林瑶","FingerTwo":"yes","UserFlag":1,"IrisImgLeft":"","FingerprintNumTwo":null,"FingerprintNumOne":null,"ID":30,"DeptName":"设备科","FingerOne":"yes","UserAccount":"2462","UserType":10,"IrisImgRight":""}]
     * code : 0
     * msg : ok
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
         * DeptNum : 40324
         * UserName : 管理员
         * FingerTwo :
         * UserFlag : 1
         * IrisImgLeft : yes
         * FingerprintNumTwo : null
         * FingerprintNumOne : 2
         * ID : 1
         * DeptName : 手术室
         * FingerOne : yes
         * UserAccount : 1
         * UserType : 11
         * IrisImgRight : yes
         */

        private String DeptNum;
        private String UserName;
        private String FingerTwo;
        private int UserFlag;
        private String IrisImgLeft;
        private Object FingerprintNumTwo;
        private int FingerprintNumOne;
        private int ID;
        private String DeptName;
        private String FingerOne;
        private String UserAccount;
        private int UserType;
        private String IrisImgRight;

        public String getDeptNum() {
            return DeptNum;
        }

        public void setDeptNum(String DeptNum) {
            this.DeptNum = DeptNum;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getFingerTwo() {
            return FingerTwo;
        }

        public void setFingerTwo(String FingerTwo) {
            this.FingerTwo = FingerTwo;
        }

        public int getUserFlag() {
            return UserFlag;
        }

        public void setUserFlag(int UserFlag) {
            this.UserFlag = UserFlag;
        }

        public String getIrisImgLeft() {
            return IrisImgLeft;
        }

        public void setIrisImgLeft(String IrisImgLeft) {
            this.IrisImgLeft = IrisImgLeft;
        }

        public Object getFingerprintNumTwo() {
            return FingerprintNumTwo;
        }

        public void setFingerprintNumTwo(Object FingerprintNumTwo) {
            this.FingerprintNumTwo = FingerprintNumTwo;
        }

        public int getFingerprintNumOne() {
            return FingerprintNumOne;
        }

        public void setFingerprintNumOne(int FingerprintNumOne) {
            this.FingerprintNumOne = FingerprintNumOne;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getDeptName() {
            return DeptName;
        }

        public void setDeptName(String DeptName) {
            this.DeptName = DeptName;
        }

        public String getFingerOne() {
            return FingerOne;
        }

        public void setFingerOne(String FingerOne) {
            this.FingerOne = FingerOne;
        }

        public String getUserAccount() {
            return UserAccount;
        }

        public void setUserAccount(String UserAccount) {
            this.UserAccount = UserAccount;
        }

        public int getUserType() {
            return UserType;
        }

        public void setUserType(int UserType) {
            this.UserType = UserType;
        }

        public String getIrisImgRight() {
            return IrisImgRight;
        }

        public void setIrisImgRight(String IrisImgRight) {
            this.IrisImgRight = IrisImgRight;
        }
    }
}
