package com.cskaoyan.distributionnews.controller;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.bean.StatusBeanUser;
import com.cskaoyan.distributionnews.model.New;
import com.cskaoyan.distributionnews.model.User;
import com.cskaoyan.distributionnews.service.NewService;
import com.cskaoyan.distributionnews.service.UploadService;
import com.cskaoyan.distributionnews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private NewService newsService;

    @Autowired
    private UserService userService;


    @Autowired
    private UploadService uploadService;

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
    public StatusBeanUser login(@Valid User user, int rember, BindingResult bindingResult,
                                HttpSession session, HttpServletResponse response){
        if(rember == 1) {
            response.addCookie(new Cookie("rember",user.getPassword()));
        }
        if(bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            assert fieldError != null;
            return new StatusBeanUser(3,fieldError.getDefaultMessage());
        }
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

    /**
     * 上传news图片到阿里云
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public StatusBean upaloadAliyun(MultipartFile file) throws IOException {
        return uploadService.uploadFileToAliyun(file);
    }

}
