package com.shanyi.itemsharing.controller;

import com.shanyi.itemsharing.bean.StatusBean;
import com.shanyi.itemsharing.bean.StatusBeanUser;
import com.shanyi.itemsharing.model.New;
import com.shanyi.itemsharing.model.User;
import com.shanyi.itemsharing.service.NewService;
import com.shanyi.itemsharing.service.UploadService;
import com.shanyi.itemsharing.service.UserService;
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
    private final NewService newService;

    private final UserService userService;


    private final UploadService uploadService;

    @Autowired
    public MainController(NewService newService, UserService userService, UploadService uploadService) {
        this.newService = newService;
        this.userService = userService;
        this.uploadService = uploadService;
    }

    /**
     * 访问首页
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String toHeadPage(Model model,HttpSession session) {
        Object attribute = session.getAttribute("user");
        if(attribute == null){
            List<New> news = newService.findNews();
            model.addAttribute("news", news);
            model.addAttribute("pop",0);
            return "home";
        }

        User user = (User)attribute;

        List<New> news = newService.findNews(user.getId());
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
    public StatusBeanUser register(User user,HttpSession session){
        return userService.registerUser(user,session);

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
