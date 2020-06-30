package com.su.ehobesmallsamkoon.bean;

public class UserBean {
    String userName;
    String id;

    public UserBean(String userName, String id) {
        this.userName = userName;
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
