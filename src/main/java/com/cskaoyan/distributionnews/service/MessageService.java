package com.cskaoyan.distributionnews.service;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.model.Message;

import java.util.List;

public interface MessageService {

    /**
     * 添加一条新私信
     * @param message
     * @return
     */
    StatusBean addMessage(Message message);

    /**
     * 通过用户名查找用户发站内信
     * @param username
     * @return
     */
    List<Message> findMessage(String username);
}
