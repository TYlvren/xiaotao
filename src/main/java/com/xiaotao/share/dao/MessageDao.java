package com.xiaotao.share.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaotao.share.bean.Page;
import com.xiaotao.share.model.Conversation;
import com.xiaotao.share.model.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface MessageDao extends BaseMapper<Message> {

    int insertMessage(Message message);

    List<Message> selectMessageByFromId(int fromId);

    List<Message> selectMessageByToId(int toId);

    Page<Message> selectMessageByConversationId(Page<Message> page,@Param("conversationId") String conversationId);

    int setHasRead(@Param("list") List<Integer> messageIds);

    int deleteMessageById(int messageId);

    Page<Conversation> selectConversationByConversationIdSet(Page<Conversation> page,@Param("set") Set<String> set);

    int deleteMessageByConversationId(String conversationId);

    int selectCountUnreadByToName(int userId);
}
