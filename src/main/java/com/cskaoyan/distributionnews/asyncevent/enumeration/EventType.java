package com.cskaoyan.distributionnews.asyncevent.enumeration;

public enum EventType {
    LIKE(1),
    DISLIKE(2),
    COMMIT(3);

    public int type;

    EventType(int type) {
        this.type = type;
    }
}
