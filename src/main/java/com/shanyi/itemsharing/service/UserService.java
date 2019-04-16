package com.shanyi.itemsharing.service;

import com.shanyi.itemsharing.bean.StatusBeanUser;
import com.shanyi.itemsharing.model.User;


import javax.servlet.http.HttpSession;


public interface UserService {

    /**
     * 注册用户
     * @param user
     * @return
     */
    StatusBeanUser registerUser(User user, HttpSession session);

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
