package com.xiaotao.share.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName(value = "concern_t")
@Data
public class Concern implements Serializable {
    private static final long serialVersionUID = -1733674841702164295L;

    private int id;

    @TableField("user_id")
    private int userId;
    @TableField("goods_id")
    private int goodsId;

    public Concern() {
    }

    public Concern(int userId, int goodsId) {
        this.userId = userId;
        this.goodsId = goodsId;
    }
}
