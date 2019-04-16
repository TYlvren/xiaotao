package com.shanyi.itemsharing.eventhandler.impl;

import com.shanyi.itemsharing.asyncevent.Event;
import com.shanyi.itemsharing.asyncevent.enumeration.EventType;
import com.shanyi.itemsharing.eventhandler.EventHandler;
import com.shanyi.itemsharing.model.Message;
import com.shanyi.itemsharing.model.New;
import com.shanyi.itemsharing.model.User;
import com.shanyi.itemsharing.service.MessageService;
import com.shanyi.itemsharing.service.NewService;
import com.shanyi.itemsharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentEventHandler implements EventHandler {

    @Autowired
    private NewService newService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;


    private List<EventType> eventTypes;

    {
        eventTypes = new ArrayList<>();
        eventTypes.add(EventType.COMMENT);
    }

    public CommentEventHandler() {
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

        //根据类型进行处理
        if(eventtype.type == EventType.COMMENT.type) {

            //查找出点赞的新闻
            int targetId = event.getTargetId();
            New news = newService.findNew(targetId);
            User user = news.getUser();
            int userId = user.getId();

            //查找出点赞者
            int activeId = event.getActiveId();
            User activeUser = userService.findUser(activeId);

            //自己给自己的新闻评论时不发送站内信，也不增加评论数
            if (userId != activeId) {
                String content = "用户" + activeUser.getUsername() + "评论了你的资讯:" + news.getTitle() + "，路径：" + event.getTargetUri();
                Message message = new Message(0, userId, content);
                messageService.addMessage(message, user.getUsername());

                newService.increaseCommentCount(targetId);
            }
        }
    }
}
