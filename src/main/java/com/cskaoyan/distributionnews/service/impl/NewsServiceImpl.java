package com.cskaoyan.distributionnews.service.impl;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.bean.StatusBeanUser;
import com.cskaoyan.distributionnews.dao.NewDao;
import com.cskaoyan.distributionnews.dao.UserDao;
import com.cskaoyan.distributionnews.model.New;
import com.cskaoyan.distributionnews.model.User;
import com.cskaoyan.distributionnews.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private NewDao newsDao;

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
     * 添加news
     *
     * @param news
     */
    @Override
    public StatusBean addNews(New news) {
        int i = newsDao.insertNews(news);
        StatusBean statusBean = new StatusBean();
        if(i != 1){
            statusBean.setCode(2);
            statusBean.setMsg("异常");
            return statusBean;
        }

        statusBean.setCode(0);
        statusBean.setMsg("成功");
        return statusBean;
    }

    /**
     * 查找所有的new
     *
     * @return
     */
    @Override
    public List<New> findNew() {
        return newsDao.selectAllNew();
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
