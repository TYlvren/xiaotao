package com.cskaoyan.distributionnews.model;

import java.util.Date;

public class Message {
    private User user = new User();
    private Date createdDate = new Date(System.currentTimeMillis());
    private String fromName;
    private String toName;
    private String content;

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
