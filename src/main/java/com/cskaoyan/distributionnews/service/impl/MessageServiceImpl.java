package com.cskaoyan.distributionnews.service.impl;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.bean.StatusBeanUser;
import com.cskaoyan.distributionnews.dao.MessageDao;
import com.cskaoyan.distributionnews.dao.UserDao;
import com.cskaoyan.distributionnews.model.Message;
import com.cskaoyan.distributionnews.model.User;
import com.cskaoyan.distributionnews.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public StatusBean addMessage(Message message){

        //如果发送的用户名不存在则返回错误码
        User user = userDao.selectUserByUsername(message.getToName());
        if(user == null){
            statusBean.setCode(1);
            statusBean.setMsg("用户不存在");
            return statusBean;
        }

        //添加消息
        int i = messageDao.insertMessage(message);
        if(i != 1){
            statusBean.setCode(2);
            statusBean.setMsg("发送异常");
            return statusBean;
        }

        statusBean.setCode(0);
        statusBean.setMsg("发送成功");
        return statusBean;
    }


    /**
     * 通过用户名查找用户发站内信
     *
     * @param username
     * @return
     */
    @Override
    public List<Message> findMessage(String username) {
        return messageDao.selectMessageByFromName(username);
    }
}
