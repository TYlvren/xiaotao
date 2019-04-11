package com.cskaoyan.distributionnews.controller;

import com.cskaoyan.distributionnews.model.Conversation;
import com.cskaoyan.distributionnews.model.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("msg")
public class MassageController {

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
    public String toLetterDetail(Model model){
        List<Message> messages = new ArrayList<>();
        model.addAttribute("messages",messages);
        return "letterDetail";
    }


}
