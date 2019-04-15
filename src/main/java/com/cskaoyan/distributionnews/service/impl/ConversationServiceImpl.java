package com.cskaoyan.distributionnews.service.impl;

import com.cskaoyan.distributionnews.dao.ConversationDao;
import com.cskaoyan.distributionnews.dao.MessageDao;
import com.cskaoyan.distributionnews.model.Conversation;
import com.cskaoyan.distributionnews.model.Message;
import com.cskaoyan.distributionnews.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationDao conversationDao;

    private final MessageDao messageDao;

    @Autowired
    public ConversationServiceImpl(ConversationDao conversationDao, MessageDao messageDao) {
        this.conversationDao = conversationDao;
        this.messageDao = messageDao;
    }


    @Override
    public List<Conversation> findConversationByUserId(int userId) {
        //获取用户发送的消息
        List<Message> sendMessages = messageDao.selectMessageByFromId(userId);

        //保存用户参与的会话Id
        Set<String> conversationIdSet = new HashSet<>();

        //保存用户各个会话中未读消息的数量
        Map<String, Integer> unreadMap = new HashMap<>();

        for (Message sendMessage : sendMessages) {
            //获取用户发送的消息的会话Id
            String conversationId = sendMessage.getConversationId();
            conversationIdSet.add(conversationId);
        }

        //获取用户接收的消息
        List<Message> receiveMessages = messageDao.selectMessageByToId(userId);
        for (Message receiveMessage : receiveMessages) {
            //获取消息的会话Id
            String conversationId = receiveMessage.getConversationId();
            conversationIdSet.add(conversationId);

            //计算用户接收的消息中hasRead为0（未读)的个数
            //每一个会话分别计算
            if (receiveMessage.getHasRead() == 0) {
                Integer integer = unreadMap.get(conversationId);
                integer = integer == null ? 1 : (integer + 1);
                unreadMap.put(conversationId, integer);
            }
        }

        //用户没有消息记录则返回空会话列表
        if(conversationIdSet.size() == 0){
            return new ArrayList<>();
        }

        List<Conversation> conversations = conversationDao.selectConversationByIdSet(conversationIdSet);

        //为当前登入的用户显示未读的消息数

        for (Conversation conversation : conversations) {
            String conversationId = conversation.getConversationId();
            Integer integer = unreadMap.get(conversationId);

            //integer为null，证明该会话中没有接受的消息，则该会话全部消息都是用户发送的
            //没有未读消息
            if(integer == null){
                integer = 0;
            }
            conversation.setUnread(integer);
        }
        return conversations;
    }
}
