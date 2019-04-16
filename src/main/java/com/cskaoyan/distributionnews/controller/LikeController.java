package com.cskaoyan.distributionnews.controller;

import com.cskaoyan.distributionnews.asyncevent.EventProducer;
import com.cskaoyan.distributionnews.asyncevent.enumeration.EventType;
import com.cskaoyan.distributionnews.asyncevent.enumeration.TargetType;
import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.model.User;
import com.cskaoyan.distributionnews.service.LikeCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LikeController {

    private final LikeCountService likeCountService;

    private final StatusBean statusBean;

    @Autowired
    public LikeController(LikeCountService likeCountService, StatusBean statusBean, Jedis jedis) {
        this.likeCountService = likeCountService;
        this.statusBean = statusBean;
        this.jedis = jedis;
    }

    private final Jedis jedis;

    /**
     * 点赞
     *
     * @param newsId
     * @return
     */
    @RequestMapping("like")
    @ResponseBody
    public StatusBean like(int newsId, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        statusBean.setCode(0);

        if (user == null) {
            statusBean.setMsg("无效");
            return statusBean;
        }

        String uri = request.getScheme() + "://" + request.getServerName() + ":" +
                request.getServerPort() + request.getContextPath() + "/news/" + newsId;

        //生产一个类型为Like的事件
        EventProducer.createEvent(jedis,EventType.LIKE,user.getId(),
                newsId, uri,TargetType.News,null);


        return likeCountService.addLikeToRedis(newsId, user.getId());
    }

    /**
     * 点踩
     *
     * @param newsId
     * @return
     */
    @RequestMapping("dislike")
    @ResponseBody
    public StatusBean dislike(int newsId,HttpServletRequest request) {
        statusBean.setCode(0);
        User user = (User)request.getSession().getAttribute("user");

        if (user == null) {
            statusBean.setMsg("无效");
            return statusBean;
        }



        //生产一个类型为Dislike的事件
        EventProducer.createEvent(jedis,EventType.DISLIKE,user.getId(),
                newsId, null,TargetType.News,null);

        return likeCountService.addDislikeToRedis(newsId, user.getId());
    }
}