package com.cskaoyan.distributionnews.controller;

import com.cskaoyan.distributionnews.model.Conversation;
import com.cskaoyan.distributionnews.model.Message;
import com.cskaoyan.distributionnews.model.User;
import com.cskaoyan.distributionnews.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("msg")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 跳转到站内信页面
     * @return
     */
    @RequestMapping("list")
    public String toLetter(Model model){
        List<Conversation> conversations = new ArrayList<>();
        Conversation conversation = new Conversation();
        conversations.add(conversation);
        model.addAttribute("conversations",conversations);
        return "letter";
    }

    /**
     *
     * @return
     */
    @RequestMapping("detail")
    public String toLetterDetail(Model model, HttpSession session){
        User user = (User)session.getAttribute("user");
        List<Message> messages = messageService.findMessage(user.getUsername());
        model.addAttribute("messages",messages);
        return "letterDetail";
    }

}
