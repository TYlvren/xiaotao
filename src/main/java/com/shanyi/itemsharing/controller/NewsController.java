package com.shanyi.itemsharing.controller;

import com.shanyi.itemsharing.asyncevent.EventProducer;
import com.shanyi.itemsharing.asyncevent.enumeration.EventType;
import com.shanyi.itemsharing.asyncevent.enumeration.TargetType;
import com.shanyi.itemsharing.model.Comment;
import com.shanyi.itemsharing.model.New;
import com.shanyi.itemsharing.model.User;
import com.shanyi.itemsharing.service.NewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class NewsController {

    private final NewService newService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Jedis jedis;

    @Autowired
    public NewsController(NewService newService) {
        this.newService = newService;
    }

    @RequestMapping("news/{id}")
    public String toDetail(@PathVariable int id, Model model, HttpSession session) {
        List<Comment> comments = newService.findComment(id);
        model.addAttribute("comments", comments);

        Object attribute = session.getAttribute("user");
        if (attribute == null) {
            New aNew = newService.findNew(id);
            if (aNew == null) {
                return "redirect:/";
            }
            model.addAttribute("news", aNew);
            return "detail";
        }

        User user = (User) session.getAttribute("user");
        New news = newService.findNew(id, user.getId());
        if (news == null) {
            return "redirect:/";
        }
        model.addAttribute("news", news);
        return "detail";
    }

    @RequestMapping("addComment")
    public String addComment(Comment comment, HttpServletRequest request) {
        logger.info("content = " + comment.getContent());
        User user = (User) request.getSession().getAttribute("user");
        comment.setUser(user);
        int i = newService.addComment(comment);

        int newsId = comment.getNewsId();

        //添加数据库成功再生产事件
        if (i == 1) {
            String uri = request.getScheme() + "://" + request.getServerName() + ":" +
                    request.getServerPort() + request.getContextPath() + "/news/" + newsId;

            //生产一个类型为COMMENT的事件
            EventProducer.createEvent(jedis, EventType.COMMENT, user.getId(),
                    newsId, uri, TargetType.News, null);
        }
        return "redirect:news/" + newsId;
    }
}
