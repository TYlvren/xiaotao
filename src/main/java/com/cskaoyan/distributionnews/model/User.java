package com.cskaoyan.distributionnews.model;

import javax.validation.constraints.Size;

public class User {

    private int id;
    @Size(max = 20,message = "用户名长度最大不能超过20")
    private String username;
    @Size(min = 6,max = 16,message = "密码长度应为6-16")
    private String password;
    private String headUrl;

    public User() {
    }

    public User(int id, String username, String password, String headUrl) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.headUrl = headUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
