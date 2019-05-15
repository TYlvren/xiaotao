package com.xiaotao.share.eventhandler.impl;

import com.xiaotao.share.asyncevent.Event;
import com.xiaotao.share.asyncevent.enumeration.EventType;
import com.xiaotao.share.eventhandler.EventHandler;
import com.xiaotao.share.model.Message;
import com.xiaotao.share.model.Goods;
import com.xiaotao.share.model.User;
import com.xiaotao.share.service.MessageService;
import com.xiaotao.share.service.GoodsService;
import com.xiaotao.share.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentEventHandler implements EventHandler {

    @Autowired
    private GoodsService goodsService;

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

            //查找出关注的物品
            int targetId = event.getTargetId();
            Goods goods = goodsService.findGood(targetId);
            User user = goods.getUser();
            int userId = user.getId();

            //查找出关注者
            int activeId = event.getActiveId();
            User activeUser = userService.findUser(activeId);

            //自己给自己的物品评论时不发送站内信，也不增加评论数
            if (userId != activeId) {
                String content = "用户" + activeUser.getUsername() + "评论了你的资讯:" + goods.getGoodsName() + "，路径：" + event.getTargetUri();
                Message message = new Message(0, userId, content);
                messageService.addMessage(message, user.getUsername());

                goodsService.increaseCommentCount(targetId);
            }
        }
    }
}
