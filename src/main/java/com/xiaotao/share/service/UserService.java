package com.xiaotao.share.service;

import com.xiaotao.share.bean.StatusBeanUser;
import com.xiaotao.share.model.User;


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
