package com.cskaoyan.distributionnews.eventhandler.impl;

import com.cskaoyan.distributionnews.asyncevent.Event;
import com.cskaoyan.distributionnews.asyncevent.enumeration.EventType;
import com.cskaoyan.distributionnews.eventhandler.EventHandler;
import com.cskaoyan.distributionnews.model.Message;
import com.cskaoyan.distributionnews.model.New;
import com.cskaoyan.distributionnews.model.User;
import com.cskaoyan.distributionnews.service.LikeCountService;
import com.cskaoyan.distributionnews.service.MessageService;
import com.cskaoyan.distributionnews.service.NewService;
import com.cskaoyan.distributionnews.service.UserService;
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
    private MessageService messageService;

    @Autowired
    private NewService newService;

    @Autowired
    private UserService userService;

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

        logger.info(this.getClass().getName() + ",event :" + event);

        //根据类型进行处理
        if(eventtype.type == EventType.LIKE.type){

            //查找出点赞的新闻
            New news = newService.findNew(event.getTargetId());
            User user = news.getUser();

            //查找出点赞者
            User activeUser = userService.findUser(event.getActiveId());

            logger.info(this.getClass().getName() + ",activeUser:" + activeUser.toString());

            String content = "用户" + activeUser.getUsername() + "点赞了你的资讯" + news.getTitle() + "，路径：" + event.getTargetUri();
            Message message = new Message(0, user.getId(),content);
            messageService.addMessage(message,user.getUsername());

            likeCountService.increaseLikeCount(event);
        }else if(eventtype.type == EventType.DISLIKE.type){
            likeCountService.decreaseLikeCount(event);
        }
    }
}
