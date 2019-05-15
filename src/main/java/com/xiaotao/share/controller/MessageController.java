package com.xiaotao.share.controller;

import com.xiaotao.share.bean.Page;
import com.xiaotao.share.bean.StatusBean;
import com.xiaotao.share.model.Conversation;
import com.xiaotao.share.model.Message;
import com.xiaotao.share.model.User;
import com.xiaotao.share.service.MessageService;
import com.xiaotao.share.util.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String toLetter(Page<Conversation> page, Model model){
        User user = ThreadLocalUtils.get();

        messageService.findConversationByUserId(page,user.getId());
        model.addAttribute("page",page);
        return "letter";
    }

    /**
     * 删除会话
     * @param conversationId
     * @return
     */
    @RequestMapping("deleteConversation")
    public String deleteConversation(Page<Conversation> page,Model model,String conversationId){
        StatusBean statusBean = messageService.deleteConversation(conversationId);
        model.addAttribute("page",page);
        return "forward:list";
    }

    /**
     *
     * @return
     */
    @RequestMapping("detail")
    public String toLetterDetail(Page<Message> page,String conversationId,Model model){
        User user = ThreadLocalUtils.get();
        messageService.viewMessage(page,conversationId,user);
        model.addAttribute("page",page);
        model.addAttribute("conversationId",conversationId);
        return "letterDetail";
    }


    /**
     * 添加私信记录
     * @param message
     * @return
     */
    @RequestMapping("addMessage")
    @ResponseBody
    public StatusBean addMessage(Message message, String toName){
        User user = ThreadLocalUtils.get();
        message.setFromId(user.getId());
        return messageService.addMessage(message,toName);
    }


    /**
     * 回复私信
     * @param message
     * @return
     */
    @RequestMapping("replyMessage")
    public String addMessage(Message message){
        User user = ThreadLocalUtils.get();
        StatusBean statusBean = messageService.replyMessage(message,user);
        return "forward:detail";
    }

    /**
     * 删除私信
     * @param messageId
     * @return
     */
    @RequestMapping("deleteMessage")
    public String deleteMessage(Page<Message> page,String conversationId,int messageId,Model model){
        StatusBean statusBean = messageService.deleteMessage(messageId);
        model.addAttribute("page",page);
        return "forward:detail";
    }
}
