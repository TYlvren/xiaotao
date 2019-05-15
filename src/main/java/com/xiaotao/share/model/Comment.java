package com.xiaotao.share.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("comment_t")
@Data
public class Comment {
    private int id;
    @TableField("goods_id")
    private int goodsId;
    @TableField("content")
    private String content;
    @TableField(exist = false)
    private User user;
    @TableField("created_date")
    private Date createdDate;
}
