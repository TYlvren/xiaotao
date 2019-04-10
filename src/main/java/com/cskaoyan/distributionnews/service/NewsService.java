package com.cskaoyan.distributionnews.service;

import com.cskaoyan.distributionnews.model.User;
import com.cskaoyan.distributionnews.util.StatusBean;

import javax.servlet.http.HttpSession;

public interface NewsService {

    /**
     * 注册用户
     * @param user
     * @return
     */
    StatusBean registerUser(User user);

    /**
     * 登录用户
     * @param user
     * @return
     */
    StatusBean loginUser(User user, HttpSession session);
}
