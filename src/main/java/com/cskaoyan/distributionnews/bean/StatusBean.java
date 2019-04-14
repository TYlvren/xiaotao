package com.cskaoyan.distributionnews.bean;

public class StatusBean {
    private String msg;
    private int code;

    public StatusBean() {
    }

    public StatusBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setCodeAndMsg(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "StatusBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
