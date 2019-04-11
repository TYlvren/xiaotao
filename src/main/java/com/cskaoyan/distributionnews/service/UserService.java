package com.cskaoyan.distributionnews.service;

import com.cskaoyan.distributionnews.bean.StatusBeanUser;
import com.cskaoyan.distributionnews.model.User;


import javax.servlet.http.HttpSession;


public interface UserService {

    /**
     * 注册用户
     * @param user
     * @return
     */
    StatusBeanUser registerUser(User user);

    /**
     * 登录用户
     * @param user
     * @return
     */
    StatusBeanUser loginUser(User user, HttpSession session);

    /**
     * 通过id查找User
     * @param id
     * @return
     */
    User findUser(int id);
}
