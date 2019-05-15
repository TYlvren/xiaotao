package com.xiaotao.share.service;

import com.xiaotao.share.bean.Page;
import com.xiaotao.share.bean.StatusBean;
import com.xiaotao.share.model.Conversation;
import com.xiaotao.share.model.Message;
import com.xiaotao.share.model.User;

import java.util.List;

public interface MessageService {

    /**
     * 添加一条新私信
     * @param message
     * @return
     */
    StatusBean addMessage(Message message, String toName);

    /**
     * 通过用户名查找用户发站内信
     * @param userId
     * @return
     */
    List<Message> findMessage(int userId);

    /**
     * 分页查看会话中的消息
     * @param conversationId
     * @return
     */
    Page<Message> viewMessage(Page<Message> page,String conversationId, User user);


    StatusBean replyMessage(Message message, User user);


    /**
     * 删除私信记录
     * @param messageId
     * @return
     */
    StatusBean deleteMessage(int messageId);

    Page<Conversation> findConversationByUserId(Page<Conversation> page, int userId);

    StatusBean deleteConversation(String conversationId);

    int findCountUnread(int userId);
}
