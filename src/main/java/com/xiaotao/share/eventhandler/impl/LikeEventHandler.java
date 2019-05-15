package com.xiaotao.share.eventhandler.impl;

import com.xiaotao.share.asyncevent.Event;
import com.xiaotao.share.asyncevent.enumeration.EventType;
import com.xiaotao.share.eventhandler.EventHandler;
import com.xiaotao.share.model.Message;
import com.xiaotao.share.model.Goods;
import com.xiaotao.share.model.User;
import com.xiaotao.share.service.ConcernService;
import com.xiaotao.share.service.MessageService;
import com.xiaotao.share.service.GoodsService;
import com.xiaotao.share.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Component
public class LikeEventHandler implements EventHandler {

    @Autowired
    private ConcernService concernService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private GoodsService goodsService;

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

            //查找出评论的物品
            Goods goods = goodsService.findGood(event.getTargetId());
            User user = goods.getUser();
            int userId = user.getId();

            //查找出评论者
            int activeId = event.getActiveId();
            User activeUser = userService.findUser(activeId);

            //自己给自己的物品评论时不发送站内信
            //只有第一次关注时才会发送站内信
            Boolean sismember = jedis.sismember(event.getTargetId() + "_like" + "_exist",
                    String.valueOf(activeId));

            if(sismember && userId != activeId) {
                String content = "用户" + activeUser.getUsername() + "关注了你的资讯:" + goods.getGoodsName() + "，路径：" + event.getTargetUri();
                Message message = new Message(0, userId, content);
                messageService.addMessage(message, user.getUsername());
            }

            concernService.increaseLikeCount(event);

        }else if(eventtype.type == EventType.DISLIKE.type){
            concernService.decreaseLikeCount(event);
        }
    }
}
