package com.shanyi.itemsharing.service;

import com.shanyi.itemsharing.model.Conversation;

import java.util.List;


public interface ConversationService {


    List<Conversation> findConversationByUserId(int userId);
}
