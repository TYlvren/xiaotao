package com.cskaoyan.distributionnews.service;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.bean.StatusBeanUser;
import com.cskaoyan.distributionnews.model.New;
import com.cskaoyan.distributionnews.model.User;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface NewsService {

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
     * 添加news
     * @param news
     */
    StatusBean addNews(New news);

    /**
     * 查找所有的new
     * @return
     */
    List<New> findNew();

    /**
     * 通过id查找User
     * @param id
     * @return
     */
    User findUser(int id);
}
