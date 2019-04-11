package com.cskaoyan.distributionnews.service.impl;

import com.cskaoyan.distributionnews.bean.StatusBeanUser;
import com.cskaoyan.distributionnews.dao.UserDao;
import com.cskaoyan.distributionnews.model.User;
import com.cskaoyan.distributionnews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @Override
    public StatusBeanUser registerUser(User user) {
        User userByUsername = userDao.selectUserByUsername(user.getUsername());
        StatusBeanUser statusBean = new StatusBeanUser();
        if(userByUsername != null){
            statusBean.setCode(1);
            statusBean.setMsgname("用户名已被注册");
            return statusBean;
        }
        int random = (int) (Math.random() * 10 + 1);
        user.setHeadUrl("images/headImg/"+ random +".jpg");
        int i = userDao.insertUser(user);
        if(i != 1){
            statusBean.setCode(2);
            statusBean.setMsgname("注册异常");
            return statusBean;
        }

        statusBean.setCode(0);
        statusBean.setMsgname("注册成功");
        return statusBean;
    }


    /**
     * 登录用户
     *
     * @param user
     * @return
     */
    @Override
    public StatusBeanUser loginUser(User user, HttpSession session) {
        User userByUsername = userDao.selectUserByUsername(user.getUsername());
        StatusBeanUser statusBean = new StatusBeanUser();
        if(userByUsername == null){
            statusBean.setCode(1);
            statusBean.setMsgname("用户名不存在");
            return statusBean;
        }

        User userByUsernameAndPassword = userDao.selectUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (userByUsernameAndPassword == null){
            statusBean.setCode(2);
            statusBean.setMsgname("密码错误");
            return statusBean;
        }

        session.setAttribute("user",userByUsernameAndPassword);
        statusBean.setCode(0);
        statusBean.setMsgname("登录成功");
        return statusBean;

    }

    /**
     * 通过id查找User
     *
     * @param id
     * @return
     */
    @Override
    public User findUser(int id) {
        return userDao.selectUserById(id);
    }

}
