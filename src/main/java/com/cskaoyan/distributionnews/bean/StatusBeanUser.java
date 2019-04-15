package com.cskaoyan.distributionnews.bean;

public class StatusBeanUser {
    private int code;
    private String msgname;

    public StatusBeanUser() {
    }

    public StatusBeanUser(int code, String msgname) {
        this.code = code;
        this.msgname = msgname;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsgname() {
        return msgname;
    }

    public void setMsgname(String msgname) {
        this.msgname = msgname;
    }

    public void setCodeAndeMsgname(int code, String msgname){
        this.code = code;
        this.msgname = msgname;
    }
}
