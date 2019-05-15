package com.xiaotao.share.asyncevent.enumeration;

public enum  TargetType {
    Goods(1),
    Comment(2)
    ;

    private int type;

    TargetType(int type) {
        this.type = type;
    }
}
