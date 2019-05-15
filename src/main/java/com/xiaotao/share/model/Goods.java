package com.xiaotao.share.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("goods_t")
@Data
public class Goods implements Serializable {
    private static final long serialVersionUID = 3209209671167112573L;
    private int id;
    @TableField("goods_name")
    private String goodsName;
    @TableField("desc")
    private String desc;
    @TableField("image_url")
    private String imageUrl;
    @TableField("concern_count")
    private int concernCount;
    @TableField("comment_count")
    private int commentCount;
    @TableField("expected_price")
    private float expectedPrice;
    @TableField("created_date")
    private Date createdDate;
    @TableField(exist = false)  //该字段不在数据库中
    private User user;
    @TableField(exist = false)  //该字段不在数据库中
    private Category category;

    private int sold;

    @TableField(exist = false)  //该字段不在数据库中
    private int like;

    public Goods() {
    }

    public Goods(int id) {
        this.id = id;
    }
}
