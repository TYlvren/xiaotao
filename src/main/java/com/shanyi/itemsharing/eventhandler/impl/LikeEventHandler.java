package com.shanyi.itemsharing.eventhandler.impl;

import com.shanyi.itemsharing.asyncevent.Event;
import com.shanyi.itemsharing.asyncevent.enumeration.EventType;
import com.shanyi.itemsharing.eventhandler.EventHandler;
import com.shanyi.itemsharing.model.Message;
import com.shanyi.itemsharing.model.New;
import com.shanyi.itemsharing.model.User;
import com.shanyi.itemsharing.service.LikeCountService;
import com.shanyi.itemsharing.service.MessageService;
import com.shanyi.itemsharing.service.NewService;
import com.shanyi.itemsharing.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

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


    @Autowired
    private Jedis jedis;

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

            //查找出评论的新闻
            New news = newService.findNew(event.getTargetId());
            User user = news.getUser();
            int userId = user.getId();

            //查找出评论者
            int activeId = event.getActiveId();
            User activeUser = userService.findUser(activeId);

            //自己给自己的新闻评论时不发送站内信
            //只有第一次点赞时才会发送站内信
            Boolean sismember = jedis.sismember(event.getTargetId() + "_like" + "_exist",
                    String.valueOf(activeId));

            if(sismember && userId != activeId) {
                String content = "用户" + activeUser.getUsername() + "点赞了你的资讯:" + news.getTitle() + "，路径：" + event.getTargetUri();
                Message message = new Message(0, userId, content);
                messageService.addMessage(message, user.getUsername());
            }

            likeCountService.increaseLikeCount(event);

        }else if(eventtype.type == EventType.DISLIKE.type){
            likeCountService.decreaseLikeCount(event);
        }
    }
}
