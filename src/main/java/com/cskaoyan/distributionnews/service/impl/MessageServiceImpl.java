package com.cskaoyan.distributionnews.service.impl;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.dao.ConversationDao;
import com.cskaoyan.distributionnews.dao.MessageDao;
import com.cskaoyan.distributionnews.dao.UserDao;
import com.cskaoyan.distributionnews.model.Conversation;
import com.cskaoyan.distributionnews.model.Message;
import com.cskaoyan.distributionnews.model.User;
import com.cskaoyan.distributionnews.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private StatusBean statusBean;

    @Autowired
    private ConversationDao conversationDao;

    /**
     * 添加一条新私信
     *
     * @param message
     * @return
     */
    @Override
    public StatusBean addMessage(Message message,String toName){

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
        boolean isConversationExist = setConversationId(message, fromId, toId);

        //添加message和conversation,开启事务
        insertMessageAndConversation(message,isConversationExist);

        statusBean.setCodeAndMsg(0,"发送成功");
        return statusBean;
    }


    /**
     * 判断会话是否存在，根据查询结果设置会话id，返回数据库中是否存在该会话
     * @param message
     * @param fromId
     * @param toId
     * @return
     */
    private boolean setConversationId(Message message, int fromId, int toId) {
        //设置会话id
        String conversationId = fromId + "_" + toId;
        //查询该会话是否存在
        boolean isConversationExist = true;
        Conversation conversation = conversationDao.selectConversationById(conversationId);

        if(conversation == null) {

            //如果不存在，则查询新增会话的另一个名字
            String otherConversationId = toId + "_" + fromId;
            conversation = conversationDao.selectConversationById(otherConversationId);

            if(conversation == null) {

                //会话不存在，设置消息的会话id为conversationId
                isConversationExist = false;
                message.setConversationId(conversationId);
            }else {
                //该用户为会话的接收者，设置下次的会话id为otherConversationId
                message.setConversationId(otherConversationId);
            }

        }else {
            //会话存在，设置消息的会话id为conversationId
            message.setConversationId(conversationId);
        }
        return isConversationExist;
    }

    @Transactional
    void insertMessageAndConversation(Message message,boolean isConversationExist) {

        String conversationId = message.getConversationId();
        if(isConversationExist){
            //如果会话已经存在,则会话的消息数+1
            conversationDao.increaseMessageNum(conversationId);
        }else {
            //会话不存在，新增一个会话
            Conversation conversation = new Conversation(conversationId, message.getFromId(), message.getContent());
            conversationDao.insertConversation(conversation);
        }

        //为会话添加消息,消息标记为未读状态
        message.setHasRead(0);
        messageDao.insertMessage(message);
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
    public List<Message> viewMessage(String conversationId,User user) {
        List<Message> messages = messageDao.selectMessageByConversationId(conversationId);

        //将该会话中用户接收的消息中未读的消息，设置为已读
        int userId = user.getId();
        List<Integer> messageIds = new ArrayList<>();

        for (Message message : messages) {
            int toId = message.getToId();
            if(toId == userId && message.getHasRead()==0){
                int messageId = message.getId();
                messageIds.add(messageId);
            }
        }

        //如果没有未读消息则直接返回messages
        if (messageIds.size() == 0){
            return messages;
        }else { //更新数据库
            messageDao.setHasRead(messageIds);
        }

        return messages;
    }
}
