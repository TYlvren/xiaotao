package com.xiaotao.share.asyncevent.enumeration;

public enum EventType {
    LIKE(1),
    DISLIKE(2),
    COMMENT(3);

    public int type;

    EventType(int type) {
        this.type = type;
    }
}
