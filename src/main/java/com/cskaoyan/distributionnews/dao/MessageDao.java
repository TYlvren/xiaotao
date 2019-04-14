package com.cskaoyan.distributionnews.dao;

import com.cskaoyan.distributionnews.model.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageDao {

    int insertMessage(Message message);

    List<Message> selectMessageByFromId(int fromId);

    List<Message> selectMessageByToId(int toId);

    List<Message> selectMessageByConversationId(String conversationId);

    int setHasRead(@Param("list") List<Integer> messageIds);
}
