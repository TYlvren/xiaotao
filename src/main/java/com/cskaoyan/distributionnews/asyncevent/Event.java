package com.cskaoyan.distributionnews.asyncevent;

import com.cskaoyan.distributionnews.asyncevent.enumeration.EventType;
import com.cskaoyan.distributionnews.asyncevent.enumeration.TargetType;

import java.util.Map;

public class Event {

    //事件的类型
    private EventType EVENTTYPE;

    //事件的触发者
    //谁点赞
    private int activeId;

    //事件的作用目标
    //给谁点赞
    private int targetId;

    //目标类型
    //新闻还是评论
    private TargetType targetType;

    //为了以后扩展
    private Map<String,Object> extraData;


    public EventType getEVENTTYPE() {
        return EVENTTYPE;
    }

    public void setEVENTTYPE(EventType EVENTTYPE) {
        this.EVENTTYPE = EVENTTYPE;
    }

    public int getActiveId() {
        return activeId;
    }

    public void setActiveId(int activeId) {
        this.activeId = activeId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    public Map<String, Object> getExtraData() {
        return extraData;
    }

    public void setExtraData(Map<String, Object> extraData) {
        this.extraData = extraData;
    }


    @Override
    public String toString() {
        return "Event{" +
                "EVENTTYPE=" + EVENTTYPE +
                ", activeId=" + activeId +
                ", targetId=" + targetId +
                ", targetType=" + targetType +
                ", extraData=" + extraData +
                '}';
    }
}
