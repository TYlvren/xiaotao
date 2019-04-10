package com.cskaoyan.distributionnews.util;

public class StatusBean {
    private int code;
    private String msgname;

    public StatusBean() {
    }

    public StatusBean(int code) {
        this.code = code;
        if(code == 1 ){
            msgname = "用户名已经被注册";
        }else if(code == 2){
            msgname = "注册成功";
        }
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
}
