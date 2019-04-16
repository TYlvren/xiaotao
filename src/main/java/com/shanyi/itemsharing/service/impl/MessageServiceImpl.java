package com.shanyi.itemsharing.service.impl;

import com.shanyi.itemsharing.bean.StatusBean;
import com.shanyi.itemsharing.dao.ConversationDao;
import com.shanyi.itemsharing.dao.MessageDao;
import com.shanyi.itemsharing.dao.UserDao;
import com.shanyi.itemsharing.model.Conversation;
import com.shanyi.itemsharing.model.Message;
import com.shanyi.itemsharing.model.User;
import com.shanyi.itemsharing.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageDao messageDao;

    private final UserDao userDao;

    private final StatusBean statusBean;

    private final ConversationDao conversationDao;

    @Autowired
    public MessageServiceImpl(MessageDao messageDao, UserDao userDao,
                              StatusBean statusBean, ConversationDao conversationDao) {
        this.messageDao = messageDao;
        this.userDao = userDao;
        this.statusBean = statusBean;
        this.conversationDao = conversationDao;
    }

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
        String conversationId = fromId < toId ? (fromId + "_" + toId) : (toId + "_" + fromId);
        message.setConversationId(conversationId);

        //添加message和conversation,开启事务
        insertMessageAndConversation(message);

        statusBean.setCodeAndMsg(0,"发送成功");
        return statusBean;
    }

    @Transactional
    void insertMessageAndConversation(Message message) {

        String conversationId = message.getConversationId();
        //查询该会话是否存在
        Conversation conversation = conversationDao.selectConversationById(conversationId);


        if(conversation == null) {
            //会话不存在，新增一个会话
            conversation = new Conversation(conversationId, message.getFromId(), message.getContent());
            conversationDao.insertConversation(conversation);
        }else {
            //如果会话已经存在,则会话的消息数+1
            conversationDao.increaseMessageNum(conversationId);
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
