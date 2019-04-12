package com.cskaoyan.distributionnews.controller;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.model.Comment;
import com.cskaoyan.distributionnews.model.New;
import com.cskaoyan.distributionnews.model.User;
import com.cskaoyan.distributionnews.service.NewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class NewsController {

    @Autowired
    private NewService newService;

    @Autowired
    private StatusBean statusBean;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("news/{id}")
    public String toDetail(@PathVariable int id, Model model) {
        New news = newService.findNews(id);
        model.addAttribute("news", news);
        List<Comment> comments = newService.findComment(id);
        model.addAttribute("comments", comments);
        return "detail";
    }

    @RequestMapping("addComment")
    public String addComment(Comment comment, HttpSession session) {
        logger.info("content = " + comment.getContent());
        User user = (User) session.getAttribute("user");
        comment.setUser(user);
        newService.addComment(comment);
        return "redirect:news/" + comment.getNewsId();
    }

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

        return newService.increaseLikeCount(newsId, user.getId());

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

        int msg = newService.findLikeCount(newsId);
        if (msg == 0) {
            statusBean.setMsg("0");
            return statusBean;
        }

        return newService.decreaseLikeCount(newsId, user.getId());
    }
}
