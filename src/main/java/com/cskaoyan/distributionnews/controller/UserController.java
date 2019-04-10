package com.cskaoyan.distributionnews.controller;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.bean.StatusBeanUser;
import com.cskaoyan.distributionnews.model.New;
import com.cskaoyan.distributionnews.model.User;
import com.cskaoyan.distributionnews.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
public class UserController {

    @Autowired
    private NewsService newsService;

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping("reg")
    @ResponseBody
    public StatusBeanUser register(User user){
        return newsService.registerUser(user);

    }

    /**
     * 登录
     * @param user
     * @param session
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public StatusBeanUser login(User user, HttpSession session){
        return newsService.loginUser(user,session);
    }

    /**
     * 注销
     * @param session
     */
    @RequestMapping("logout")
    public String login(HttpSession session){
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }

    /**
     * 发私信
     * @return
     */
    @RequestMapping("user/tosendmsg")
    public String toSendMsg(){
        return "sendmsg";
    }

    /**
     * 查看个人信息
     * @param id
     * @return
     */
    @RequestMapping("user/{id}")
    public String userMessage(@PathVariable int id){
        return "personal";
    }


    /**
     * 添加news
     * @param news
     * @return
     */
    @RequestMapping("user/addNews")
    @ResponseBody
    public StatusBean addNews(New news, HttpSession session){
        User user = (User)session.getAttribute("user");
        news.setUser(user);
        return newsService.addNews(news);
    }
}
