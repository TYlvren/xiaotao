package com.shanyi.itemsharing.dao;

import com.shanyi.itemsharing.model.Conversation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ConversationDao {
    int insertConversation(Conversation conversation);

    Conversation selectConversationById(String conversationId);

    List<Conversation> selectConversationByIdSet(@Param("set") Set<String> set);

    int increaseMessageNum(String conversationId);
}
