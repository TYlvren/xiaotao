package com.cskaoyan.distributionnews.controller;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.model.User;
import com.cskaoyan.distributionnews.service.LikeCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LikeController {

    @Autowired
    private LikeCountService likeCountService;

    @Autowired
    private StatusBean statusBean;

    /**
     * 点赞
     *
     * @param newsId
     * @param session
     * @return
     */
    @RequestMapping("like")
    @ResponseBody
    public StatusBean like(int newsId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        statusBean.setCode(0);

        if (user == null) {
            statusBean.setMsg("无效");
            return statusBean;
        }
        return likeCountService.increaseLikeCount(newsId, user.getId());
    }

    /**
     * 点踩
     *
     * @param newsId
     * @param session
     * @return
     */
    @RequestMapping("dislike")
    @ResponseBody
    public StatusBean dislike(int newsId, HttpSession session) {
        statusBean.setCode(0);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            statusBean.setMsg("无效");
            return statusBean;
        }
        return likeCountService.decreaseLikeCount(newsId, user.getId());
    }
}
