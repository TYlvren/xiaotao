package com.cskaoyan.distributionnews.model;

import java.util.Date;

public class Conversation {

    private String conversationId;
    private User user = new User();
    private String content;
    private Date createdDate;
    private int unread;
    private int messageNum;

    public int getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(int messageNum) {
        this.messageNum = messageNum;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Conversation() {
    }

    public Conversation(String conversationId, int fromId,String content) {
        this.conversationId = conversationId;
        this.content = content;
        user.setId(fromId);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
