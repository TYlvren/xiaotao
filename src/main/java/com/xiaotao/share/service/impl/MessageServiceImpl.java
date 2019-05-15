package com.xiaotao.share.service.impl;

import com.xiaotao.share.bean.Page;
import com.xiaotao.share.bean.StatusBean;
import com.xiaotao.share.dao.MessageDao;
import com.xiaotao.share.dao.UserDao;
import com.xiaotao.share.model.Conversation;
import com.xiaotao.share.model.Message;
import com.xiaotao.share.model.User;
import com.xiaotao.share.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private StatusBean statusBean;



    /**
     * 添加一条新私信
     *
     * @param message
     * @return
     */
    @Override
    public StatusBean addMessage(Message message, String toName){

        //如果发送的用户名不存在则返回错误码
        User user = userDao.selectUserByUsername(toName);
        if(user == null){
            statusBean.setCodeAndMsg(1,"用户不存在");
            return statusBean;
        }

        int fromId = message.getFromId();
        int toId = user.getId();
        if(fromId == toId){
            statusBean.setCodeAndMsg(1,"不能给自己发私信");
            return statusBean;
        }

        //设置接收用户的id
        message.setToId(toId);

        //设置会话的id
        String conversationId = fromId < toId ? (fromId + "_" + toId) : (toId + "_" + fromId);
        message.setConversationId(conversationId);

        //添加message,消息标记为未读状态
        message.setHasRead(0);
        messageDao.insertMessage(message);

        statusBean.setCodeAndMsg(0,"发送成功");
        return statusBean;
    }

    /**
     * 通过用户id查找用户发站内信
     *
     * @param userId
     * @return
     */
    @Override
    public List<Message> findMessage(int userId) {
        return messageDao.selectMessageByFromId(userId);
    }

    /**
     * 通过会话id查询Message
     *
     * @param conversationId
     * @return
     */
    @Override
    public Page<Message> viewMessage(Page<Message> page,String conversationId,User user) {
        messageDao.selectMessageByConversationId(page,conversationId);

        //将该会话中用户接收的消息中,未读的消息设置为已读
        int userId = user.getId();
        List<Integer> messageIds = new ArrayList<>();

        for (Message message : page.getRecords()) {
            int toId = message.getToId();
            if(toId == userId && message.getHasRead()==0){
                int messageId = message.getId();
                messageIds.add(messageId);
            }
        }

        //如果没有未读消息则直接返回messages
        if (messageIds.size() == 0){
            return page;
        }else { //更新数据库
            messageDao.setHasRead(messageIds);
        }

        return page;
    }


    @Override
    public StatusBean replyMessage(Message message, User user) {
        String[] userIds = message.getConversationId().split("_");
        int toId = 0;
        for (String u : userIds) {
            int userId = Integer.valueOf(u);
            if(userId != user.getId()){
                toId = userId;
            }
        }
        message.setFromId(user.getId());
        message.setToId(toId);
        int i = messageDao.insertMessage(message);
        if(i == 1){
            return new StatusBean(0,"ok");
        }else {
            return new StatusBean(1, "error");
        }
    }

    /**
     * 删除私信记录
     *
     * @param messageId
     * @return
     */
    @Override
    public StatusBean deleteMessage(int messageId) {
        int i = messageDao.deleteMessageById(messageId);
        if(i == 1){
            return new StatusBean(0,"ok");
        }else {
            return new StatusBean(1, "error");
        }
    }


    @Override
    public Page<Conversation> findConversationByUserId(Page<Conversation> page,int userId) {
        //获取用户发送的消息
        List<Message> sendMessages = messageDao.selectMessageByFromId(userId);

        //用于保存用户参与的会话Id
        Set<String> conversationIdSet = new HashSet<>();

        //用于保存用户各个会话中未读消息的数量
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
            return page;
        }

         messageDao.selectConversationByConversationIdSet(page,conversationIdSet);

        //为当前登入的用户显示未读的消息数
        for (Conversation conversation : page.getRecords()) {
            String conversationId = conversation.getConversationId();
            Integer integer = unreadMap.get(conversationId);

            //integer为null，证明该会话中没有接收的消息，则该会话全部消息都是用户发送的
            //没有未读消息
            if(integer == null){
                integer = 0;
            }
            conversation.setUnread(integer);
        }
        return page;
    }


    @Override
    public StatusBean deleteConversation(String conversationId) {
        int i = messageDao.deleteMessageByConversationId(conversationId);
        if(i == 1){
            return new StatusBean(0,"ok");
        }else {
            return new StatusBean(1, "error");
        }
    }

    @Override
    public int findCountUnread(int userId) {
        return messageDao.selectCountUnreadByToName(userId);
    }
}
