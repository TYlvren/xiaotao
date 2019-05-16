package com.xiaotao.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaotao.share.bean.StatusBeanUser;
import com.xiaotao.share.dao.UserDao;
import com.xiaotao.share.model.User;
import com.xiaotao.share.service.UserService;
import com.xiaotao.share.util.MD5Utils;
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
            return statusBeanUser.setStatusBeanUser(1,"用户名已被注册",null);
        }

        //随机获取一张http://images.nowcoder.com/head/1000t.png上的图片作为用户的头像
        Random random = new Random();
        int ran = random.nextInt(999) + 1;
        user.setHeadUrl("http://images.nowcoder.com/head/"+ ran +"t.png");

        //获取密码的md5，盐值为随机数
        int nextInt = random.nextInt(100);
        String md5 = MD5Utils.getMD5(user.getPassword(), nextInt);

        //设置盐值和密码
        user.setSalt(Integer.toHexString(nextInt));
        user.setPassword(md5);

        int i = userDao.insertUser(user);
        if(i != 1){
            return statusBeanUser.setStatusBeanUser(2,"注册异常",null);
        }

        session.setAttribute("user",user);
        return statusBeanUser.setStatusBeanUser(0,"注册成功",null);
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
        User userByUsername = userDao.selectOne(new QueryWrapper<User>().eq("username",username));
        if(userByUsername == null){
          return statusBeanUser.setStatusBeanUser(1,"用户名不存在",null);
        }

        //查询salt值
        String salt = userDao.select(username);
        Integer value = Integer.valueOf(salt, 16);

        //获取md5HashCode
        String password = user.getPassword();
        String md5 = MD5Utils.getMD5(password, value);

        User userByUsernameAndPassword = userDao.selectOne(new QueryWrapper<User>()
                .eq("username",username)
                .eq("password",md5));
        if (userByUsernameAndPassword == null){
            return statusBeanUser.setStatusBeanUser(2,null,"密码错误");
        }

        //将user放入session中
        session.setAttribute("user",userByUsernameAndPassword);

        return statusBeanUser.setStatusBeanUser(0,"登录成功",null);
    }

    /**
     * 通过id查找User
     *
     * @param id
     * @return
     */
    @Override
    public User findUser(int id) {
        return userDao.selectById(id);
    }

}
