package com.cskaoyan.distributionnews.model;

public class User {

    private int id;
    private String username;
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
