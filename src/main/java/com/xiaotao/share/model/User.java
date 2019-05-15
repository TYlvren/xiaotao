package com.xiaotao.share.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Size;

@TableName("user_t")
@Data
public class User {

    private int id;
    @Size(min=2,max = 10,message = "用户名长度应该为2-10位")
    private String username;
    @Size(min = 6,max = 16,message = "密码长度应为6-16位")
    @TableField("password")
    private String password;
    @TableField("head_url")
    private String headUrl;
    @TableField("salt")
    private String salt;
    public User() {
    }

    public User(int id, String username, String password, String headUrl) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.headUrl = headUrl;
    }
}
