package com.cskaoyan.distributionnews.controller;

import com.cskaoyan.distributionnews.bean.StatusBeanUser;
import com.cskaoyan.distributionnews.model.New;
import com.cskaoyan.distributionnews.model.User;
import com.cskaoyan.distributionnews.service.NewService;
import com.cskaoyan.distributionnews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private NewService newsService;

    @Autowired
    private UserService userService;

    /**
     * 访问首页
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String toHeadPage(Model model) {
        List<New> news = newsService.findNew();
        model.addAttribute("news", news);
        return "home";
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping("reg")
    @ResponseBody
    public StatusBeanUser register(User user){
        return userService.registerUser(user);

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
        return userService.loginUser(user,session);
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

}
