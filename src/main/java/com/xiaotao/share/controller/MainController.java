package com.xiaotao.share.controller;


import com.xiaotao.share.asyncevent.EventProducer;
import com.xiaotao.share.asyncevent.enumeration.EventType;
import com.xiaotao.share.asyncevent.enumeration.TargetType;
import com.xiaotao.share.bean.Page;
import com.xiaotao.share.bean.StatusBean;
import com.xiaotao.share.bean.StatusBeanUser;
import com.xiaotao.share.model.Goods;
import com.xiaotao.share.model.User;
import com.xiaotao.share.service.ConcernService;
import com.xiaotao.share.service.GoodsService;
import com.xiaotao.share.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class MainController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConcernService concernService;

    @Autowired
    private StatusBean statusBean;

    @Autowired
    private Jedis jedis;

    @Autowired
    private Logger logger;

    /**
     * 访问首页
     *
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String toHeadPage(Page<Goods> page, Model model) {
        goodsService.findGoodsPage(page);
        model.addAttribute("page", page);
        return "home";
    }

    /**
     * 根据分类查找物品
     *
     * @param cid
     * @param model
     * @return
     */
    @RequestMapping("categorySearch")
    public String categorySearch(Page<Goods> page,int cid, Model model) {
        page=goodsService.findGoodsByCategoryIdPage(page,cid);
        model.addAttribute("page", page);
        model.addAttribute("cid",cid);
        return "categoryHome";
    }

    /**
     * 模糊搜索查找物品
     *
     * @param goodsName
     * @return
     */
    @RequestMapping("fuzzySearch")
    public String fuzzySearch(Page<Goods> page,String goodsName, Model model) {
        goodsService.findGoodsByFuzzyName(page,goodsName);
        model.addAttribute("page", page);
        model.addAttribute("goodsName",goodsName);
        return "fuzzyHome";
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @RequestMapping("reg")
    @ResponseBody
    public StatusBeanUser register(User user, HttpSession session) {
        return userService.registerUser(user, session);

    }

    /**
     * 登录
     *
     * @param user
     * @param session
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public StatusBeanUser login(@Valid User user, BindingResult bindingResult,
                                HttpSession session, Model model) {

        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            assert fieldError != null;
            return new StatusBeanUser(3, fieldError.getDefaultMessage());
        }

        model.addAttribute("pop", 0);
        return userService.loginUser(user, session);
    }

    /**
     * 注销
     *
     * @param session
     */
    @RequestMapping("logout")
    public void login(HttpSession session, HttpServletResponse response) throws IOException {
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("/");
    }

    /**
     * 上传goods图片到阿里云
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public StatusBean upaloadAliyun(MultipartFile file) {
        return goodsService.uploadFileToAliyun(file);
    }

    /**
     * 关注
     *
     * @param goodsId
     * @return
     */
    @RequestMapping("like")
    @ResponseBody
    public StatusBean like(int goodsId, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        statusBean.setCode(0);

        if (user == null) {
            statusBean.setMsg("无效");
            return statusBean;
        }

        StatusBean statusBean = concernService.addLikeToRedis(goodsId, user.getId());
        if (statusBean.getCode() == 0) {

            String uri = request.getScheme() + "://" + request.getServerName() + ":" +
                    request.getServerPort() + request.getContextPath() + "/goods/" + goodsId;

            //生产一个类型为Like的事件
            EventProducer.createEvent(jedis, EventType.LIKE, user.getId(),
                    goodsId, uri, TargetType.Goods, null);
        }

        return statusBean;
    }

    /**
     * 点踩
     *
     * @param goodsId
     * @return
     */
    @RequestMapping("dislike")
    @ResponseBody
    public StatusBean dislike(int goodsId, HttpServletRequest request) {
        statusBean.setCode(0);
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            statusBean.setMsg("无效");
            return statusBean;
        }

        StatusBean statusBean = concernService.addDislikeToRedis(goodsId, user.getId());
        if (statusBean.getCode() == 0) {

            //生产一个类型为Dislike的事件
            EventProducer.createEvent(jedis, EventType.DISLIKE, user.getId(),
                    goodsId, null, TargetType.Goods, null);
        }
        return statusBean;
    }

   /* @RequestMapping("error")
    public String error(){
        return "error";
    }*/
}
