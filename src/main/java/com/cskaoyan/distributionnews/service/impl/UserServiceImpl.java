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

    @Autowired
    private StatusBeanUser statusBeanUser;

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @Override
    public StatusBeanUser registerUser(User user, HttpSession session) {
        User userByUsername = userDao.selectUserByUsername(user.getUsername());

        if(userByUsername != null){
            statusBeanUser.setCode(1);
            statusBeanUser.setMsgname("用户名已被注册");
            return statusBeanUser;
        }
        int random = (int) (Math.random() * 10 + 1);
        user.setHeadUrl("images/headImg/"+ random +".jpg");
        int i = userDao.insertUser(user);
        if(i != 1){
            statusBeanUser.setCode(2);
            statusBeanUser.setMsgname("注册异常");
            return statusBeanUser;
        }

        statusBeanUser.setCode(0);
        statusBeanUser.setMsgname("注册成功");
        session.setAttribute("user",user);
        return statusBeanUser;
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
        if(userByUsername == null){
            statusBeanUser.setCode(1);
            statusBeanUser.setMsgname("用户名不存在");
            return statusBeanUser;
        }

        User userByUsernameAndPassword = userDao.selectUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (userByUsernameAndPassword == null){
            statusBeanUser.setCode(2);
            statusBeanUser.setMsgname("密码错误");
            return statusBeanUser;
        }

        session.setAttribute("user",userByUsernameAndPassword);
        statusBeanUser.setCode(0);
        statusBeanUser.setMsgname("登录成功");
        return statusBeanUser;

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
