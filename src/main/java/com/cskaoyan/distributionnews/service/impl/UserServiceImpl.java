package com.cskaoyan.distributionnews.service.impl;

import com.cskaoyan.distributionnews.bean.StatusBeanUser;
import com.cskaoyan.distributionnews.dao.UserDao;
import com.cskaoyan.distributionnews.model.User;
import com.cskaoyan.distributionnews.service.UserService;
import com.cskaoyan.distributionnews.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Random;

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
        Random random = new Random();
        int ran = random.nextInt(10) + 1;
        user.setHeadUrl("https://distributionnews.oss-cn-hangzhou.aliyuncs.com/headImg/"+ ran +".jpg?x-oss-process=image/resize,w_60,h_50");

        //获取密码的md5，盐值为随机数
        int nextInt = random.nextInt(100);
        String md5 = MD5Utils.getMD5(user.getPassword(), nextInt);

        //设置盐值和密码
        user.setSalt(Integer.toHexString(nextInt));
        user.setPassword(md5);

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
        String username = user.getUsername();
        User userByUsername = userDao.selectUserByUsername(username);
        if(userByUsername == null){
            statusBeanUser.setCode(1);
            statusBeanUser.setMsgname("用户名不存在");
            return statusBeanUser;
        }

        //查询salt值
        String salt = userDao.selectSaltByUsername(username);
        Integer value = Integer.valueOf(salt, 16);

        //获取md5HashCode
        String password = user.getPassword();
        String md5 = MD5Utils.getMD5(password, value);

        User userByUsernameAndPassword = userDao.selectUserByUsernameAndPassword(username,md5);
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
