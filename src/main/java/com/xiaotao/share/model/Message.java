package com.xiaotao.share.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("message_t")
@Data
public class Message {
    private int id;
    private Date createdDate;
    private int fromId;
    private int toId;
    private String content;
    private int hasRead;
    private String conversationId;

    private User user;

    public Message() {
    }

    public Message(int fromId, int toId, String content) {
        this.fromId = fromId;
        this.toId = toId;
        this.content = content;
    }

}
