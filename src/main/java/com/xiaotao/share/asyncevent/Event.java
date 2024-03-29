package com.xiaotao.share.asyncevent;

import com.xiaotao.share.asyncevent.enumeration.EventType;
import com.xiaotao.share.asyncevent.enumeration.TargetType;

import java.util.Map;

public class Event {

    //事件的类型
    private EventType EVENTTYPE;

    //事件的触发者
    //谁关注
    private int activeId;

    //事件的作用目标
    //给谁关注
    private int targetId;

    //目标类型
    //物品还是评论
    private TargetType targetType;

    //目标的URI
    private String targetUri;

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

    public String getTargetUri() {
        return targetUri;
    }

    public void setTargetUri(String targetUri) {
        this.targetUri = targetUri;
    }

    @Override
    public String toString() {
        return "Event{" +
                "EVENTTYPE=" + EVENTTYPE +
                ", activeId=" + activeId +
                ", targetId=" + targetId +
                ", targetType=" + targetType +
                ", targetUrI='" + targetUri + '\'' +
                ", extraData=" + extraData +
                '}';
    }
}
