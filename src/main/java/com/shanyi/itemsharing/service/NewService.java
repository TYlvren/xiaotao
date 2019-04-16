package com.shanyi.itemsharing.service;

import com.shanyi.itemsharing.bean.StatusBean;
import com.shanyi.itemsharing.model.Comment;
import com.shanyi.itemsharing.model.New;

import java.util.List;


public interface NewService {

    /**
     * 添加news
     * @param news
     */
    StatusBean addNews(New news);

    /**
     * 查找所有的new
     * @return
     */
    List<New> findNews();



    /**
     * 通过id查找new
     *
     * @param id
     * @return
     */
    New findNew(int id);

    /**
     * 通过id查找new,并查找user是否为new点赞
     * @param id
     * @param userId
     * @return
     */
    New findNew(int id,int userId);

    /**
     * 为news添加一条评论
     * @param comment
     */
    int addComment(Comment comment);

    /**
     * 通过newsId查找Comment
     * @param id
     * @return
     */
    List<Comment> findComment(int id);

    /**
     * 查找新闻点赞数
     * @param newsId
     * @return
     */
    int findLikeCount(int newsId);

    /**
     * 查找某用户发布过的新闻
     * @param userId
     * @return
     */
    List<New> findNewsByUserId(int userId);

    /**
     * 查找Redis判断用户是否为新闻点赞来更新new的like并返回
     * @param userId
     * @return
     */
    List<New> findNews(int userId);

    /**
     * 增加新闻评论数
     * @param newsId
     */
    void increaseCommentCount(int newsId);
}
