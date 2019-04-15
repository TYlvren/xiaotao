package com.cskaoyan.distributionnews.eventhandler.impl;

import com.cskaoyan.distributionnews.asyncevent.Event;
import com.cskaoyan.distributionnews.asyncevent.enumeration.EventType;
import com.cskaoyan.distributionnews.eventhandler.EventHandler;
import com.cskaoyan.distributionnews.service.LikeCountService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LikeEventHandler implements EventHandler {

    @Autowired
    private LikeCountService likeCountService;

    @Autowired
    private Logger logger;

    private List<EventType> eventTypes;

    {
        eventTypes = new ArrayList<>();
        eventTypes.add(EventType.LIKE);
        eventTypes.add(EventType.DISLIKE);
    }

    public LikeEventHandler() {
    }

    /**
     * 获取事件处理器处理的事件类型
     *
     * @return
     */
    @Override
    public List<EventType> getHandleEventType() {
        return eventTypes;
    }

    /**
     * 处理事件
     *
     * @param event
     */
    @Override
    public void handleEvent(Event event) {

        EventType eventtype = event.getEVENTTYPE();

        logger.info("event :" + event);

        //根据类型进行处理
        if(eventtype.type == EventType.LIKE.type){
            likeCountService.increaseLikeCount(event);
        }else if(eventtype.type == EventType.DISLIKE.type){
            likeCountService.decreaseLikeCount(event);
        }
    }
}
