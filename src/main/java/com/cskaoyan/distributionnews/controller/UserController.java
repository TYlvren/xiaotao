package com.cskaoyan.distributionnews.controller;

import com.cskaoyan.distributionnews.model.User;
import com.cskaoyan.distributionnews.service.NewsService;
import com.cskaoyan.distributionnews.util.StatusBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
public class UserController {

    @Autowired
    private NewsService newsService;

    @RequestMapping("reg")
    @ResponseBody
    public StatusBean register(User user){
        return newsService.registerUser(user);

    }

    @RequestMapping("login")
    @ResponseBody
    public StatusBean login(User user, HttpSession session){
        return newsService.loginUser(user,session);
    }
}
