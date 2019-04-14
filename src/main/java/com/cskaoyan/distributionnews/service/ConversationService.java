package com.cskaoyan.distributionnews.service;

import com.cskaoyan.distributionnews.model.Conversation;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ConversationService {


    List<Conversation> findConversationByUserId(int userId);
}
