package com.cskaoyan.distributionnews.controller;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.model.Message;
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
@RequestMapping("user")
public class UserController {

    @Autowired
    private NewsService newsService;

    /**
     * 跳转到发私信页面
     * @return
     */
    @RequestMapping("tosendmsg")
    public String toSendMsg(){
        return "sendmsg";
    }

    /**
     * 添加私信记录
     * @param message
     * @return
     */
    @RequestMapping("msg/addMessage")
    @ResponseBody
    public StatusBean addMessage(Message message){
        return new StatusBean();
    }


    /**
     * 查看个人信息
     * @param id
     * @return
     */
    @RequestMapping("{id}")
    public String userMessage(@PathVariable int id){
        return "personal";
    }


    /**
     * 添加news
     * @param news
     * @return
     */
    @RequestMapping("addNews")
    @ResponseBody
    public StatusBean addNews(New news, HttpSession session){
        User user = (User)session.getAttribute("user");
        news.setUser(user);
        return newsService.addNews(news);
    }
}
