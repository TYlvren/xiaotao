package com.cskaoyan.distributionnews.controller;

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

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class NewsController {

    private final NewService newService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public NewsController(NewService newService) {
        this.newService = newService;
    }

    @RequestMapping("news/{id}")
    public String toDetail(@PathVariable int id, Model model,HttpSession session) {
        List<Comment> comments = newService.findComment(id);
        model.addAttribute("comments", comments);

        Object attribute = session.getAttribute("user");
        if(attribute == null){
            New aNew = newService.findNew(id);
            model.addAttribute("news", aNew);
            return "detail";
        }

        User user = (User)session.getAttribute("user");
        String userIdString = String.valueOf(user.getId());
        New news = newService.findNew(id,userIdString);
        model.addAttribute("news", news);
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
}
