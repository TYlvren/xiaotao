package com.cskaoyan.distributionnews.dao;

import com.cskaoyan.distributionnews.model.Message;

import java.util.List;

public interface MessageDao {

    int insertMessage(Message message);

    List<Message> selectMessageByFromName(String fromName);
}
