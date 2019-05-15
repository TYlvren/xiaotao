package com.xiaotao.share.model;

import lombok.Data;

import java.util.Date;
@Data
public class Conversation {

    private String conversationId;
    private User user = new User();
    private String content;
    private Date createdDate;
    private int unread;
    private int messageNum;

    public Conversation() {
    }

    public Conversation(String conversationId, int fromId,String content) {
        this.conversationId = conversationId;
        this.content = content;
        user.setId(fromId);
    }
}
