package com.xiaotao.share.controller;

import com.xiaotao.share.asyncevent.EventProducer;
import com.xiaotao.share.asyncevent.enumeration.EventType;
import com.xiaotao.share.asyncevent.enumeration.TargetType;
import com.xiaotao.share.bean.Page;
import com.xiaotao.share.bean.StatusBean;
import com.xiaotao.share.model.Category;
import com.xiaotao.share.model.Comment;
import com.xiaotao.share.model.Goods;
import com.xiaotao.share.model.User;
import com.xiaotao.share.service.CommentService;
import com.xiaotao.share.service.GoodsService;
import com.xiaotao.share.util.ThreadLocalUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private Logger logger ;

    @Autowired
    private Jedis jedis;

    /**
     * 查看物品的详细信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("{id}")
    public String toDetail(@PathVariable int id, Model model, Page<Comment> page) {
        commentService.findCommentPage(page,id);
        model.addAttribute("page", page);
        Goods goods = goodsService.findGood(id);
        if (goods == null) {
            return "redirect:/";
        }
        model.addAttribute("goods", goods);
        return "detail";
    }

    /**
     * 评论物品
     * @param comment
     * @param request
     * @return
     */
    @RequestMapping("addComment")
    public String addComment(Comment comment, HttpServletRequest request) {
        logger.info("content = " + comment.getContent());
        User user = (User) request.getSession().getAttribute("user");
        comment.setUser(user);
        int i = commentService.addComment(comment);

        int goodsId = comment.getGoodsId();

        //添加数据库成功再生产事件
        if (i == 1) {
            String uri = request.getScheme() + "://" + request.getServerName() + ":" +
                    request.getServerPort() + request.getContextPath() + "/goods/" + goodsId;

            //生产一个类型为COMMENT的事件
            EventProducer.createEvent(jedis, EventType.COMMENT, user.getId(),
                    goodsId, uri, TargetType.Goods, null);
        }
        return "redirect:/" + goodsId;
    }

    /**
     * 分享物品
     * @param goods
     * @return
     */
    @RequestMapping("addGoods")
    @ResponseBody
    public StatusBean addGoods(Goods goods, Category category, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        List<Category> categories = (List<Category>) request.getServletContext().getAttribute("categories");
        boolean flag = false;
        for (Category c : categories) {
            if (c.getCategoryName().equals(category.getCategoryName())){
                flag = true;
                break;
            }
        }
        if(!flag){
            category.setCategoryName("其他");
        }
        goods.setCategory(category);
        goods.setUser(user);
        return goodsService.addGoods(goods);
    }

    @RequestMapping(value = "sold",method = RequestMethod.POST)
    public String sold(Page<Goods> page, Goods goods,Model model){
        User user = ThreadLocalUtils.get();
        if(goods.getUser().getId() == user.getId()){
            goodsService.soldGoods(goods.getId());
        }
        model.addAttribute("page",page);
        return "forward:/user/"+user.getId();
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public String delete(Page<Goods> page,Goods goods,Model model){
        User user = ThreadLocalUtils.get();
        if(goods.getUser().getId() == user.getId()){
            goodsService.deleteGoods(goods.getId());
        }
        model.addAttribute("page",page);
        return "forward:/user/"+user.getId();
    }
}
