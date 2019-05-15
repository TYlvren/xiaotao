package com.xiaotao.share.bean;

public class StatusBeanUser {
    private int code;
    private String msgname;
    private String msgpwd;

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

    public String getMsgpwd() {
        return msgpwd;
    }

    public void setMsgpwd(String msgpwd) {
        this.msgpwd = msgpwd;
    }

    public StatusBeanUser setStatusBeanUser(int code, String msgname,String msgpwd){
        this.code = code;
        this.msgname = msgname;
        this.msgpwd = msgpwd;
        return this;
    }
}
