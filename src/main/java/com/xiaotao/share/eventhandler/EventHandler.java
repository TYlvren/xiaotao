package com.xiaotao.share.eventhandler;

import com.xiaotao.share.asyncevent.Event;
import com.xiaotao.share.asyncevent.enumeration.EventType;

import java.util.List;

public interface EventHandler {

    /**
     * 获取事件处理器处理的事件类型
     * @return
     */
    List<EventType> getHandleEventType();


    /**
     * 处理事件
     * @param event
     */
    void handleEvent(Event event);
}
