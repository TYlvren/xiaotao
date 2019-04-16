package com.shanyi.itemsharing.service;

import com.shanyi.itemsharing.bean.StatusBean;
import com.shanyi.itemsharing.model.Message;
import com.shanyi.itemsharing.model.User;

import java.util.List;

public interface MessageService {

    /**
     * 添加一条新私信
     * @param message
     * @return
     */
    StatusBean addMessage(Message message,String toName);

    /**
     * 通过用户名查找用户发站内信
     * @param userId
     * @return
     */
    List<Message> findMessage(int userId);

    /**
     * 用户查看会话中的消息
     * @param conversationId
     * @return
     */
    List<Message> viewMessage(String conversationId, User user);
}
