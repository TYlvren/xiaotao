package com.shanyi.itemsharing.asyncevent.enumeration;

public enum EventType {
    LIKE(1),
    DISLIKE(2),
    COMMENT(3);

    public int type;

    EventType(int type) {
        this.type = type;
    }
}
