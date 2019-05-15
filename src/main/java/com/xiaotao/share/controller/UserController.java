package com.xiaotao.share.controller;

import com.xiaotao.share.bean.Page;
import com.xiaotao.share.model.Goods;
import com.xiaotao.share.model.User;
import com.xiaotao.share.service.GoodsService;
import com.xiaotao.share.service.UserService;
import com.xiaotao.share.util.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;


    /**
     * 跳转到发私信页面
     * @return
     */
    @RequestMapping("tosendmsg")
    public String toSendMsg(String toName,Model model){
        model.addAttribute("toName",toName);
        return "sendmsg";
    }

    /**
     * 查看个人信息
     * @param id
     * @return
     */
    @RequestMapping("{id}")
    public String userMessage(@PathVariable int id, Page<Goods> page, Model model){
        User loginUser = ThreadLocalUtils.get();
        User user = userService.findUser(id);
        model.addAttribute("userMessage",user);
        if(user.getId() == loginUser.getId()){
            goodsService.findGoodsByUserIdPage(page,id);
        }else {
            goodsService.findGoodsByUserIdIgnoreSoldPage(page,id);
        }

        model.addAttribute("page", page);
        return "personal";
    }


    /**
     * 查看我的关注，闲置物品注重时效性，若物品已售出则不可见
     * @param page
     * @param model
     * @return
     */
    @RequestMapping("myConcern")
    public String myConcern(Page<Goods> page,Model model){
        User user = ThreadLocalUtils.get();
        goodsService.findGoodsByConcern(page,user.getId());
        model.addAttribute("page", page);
        return "concernHome";
    }

}
