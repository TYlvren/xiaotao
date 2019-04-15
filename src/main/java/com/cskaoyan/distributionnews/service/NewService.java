package com.cskaoyan.distributionnews.service;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.model.Comment;
import com.cskaoyan.distributionnews.model.New;


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
     * @return
     */
    New findNew(int id,String userIdString);

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
     * @param userIdString
     * @return
     */
    List<New> findNews(String userIdString);
}
