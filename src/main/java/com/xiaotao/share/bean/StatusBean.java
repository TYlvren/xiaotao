package com.xiaotao.share.bean;

import java.io.Serializable;

public class StatusBean implements Serializable {
    private static final long serialVersionUID = 1595357778035108218L;
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
}
