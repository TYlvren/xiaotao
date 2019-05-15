package com.xiaotao.share.model;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

//指定表名，默认与实体类名一致
@TableName(value = "category_t")
@Data
public class Category  implements Serializable {
    private static final long serialVersionUID = 7702118134408126599L;

    private int id;  //@TableId(type = IdType.AUTO)  //指定主键策略，如果实体与数据库中主键名不一致需要加value注解

    @TableField("category_name")
    private String categoryName;

    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

}
