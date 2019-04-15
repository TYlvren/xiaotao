package com.cskaoyan.distributionnews.asyncevent.enumeration;

public enum  TargetType {
    News(1),
    Comment(2)
    ;

    private int type;

    TargetType(int type) {
        this.type = type;
    }
}
