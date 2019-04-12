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
     * @param id
     * @return
     */
    New findNews(int id);

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
     * 增加新闻点赞数
     * @param newsId
     * @param userId
     * @return
     */
    StatusBean increaseLikeCount(int newsId, int userId);


    /**
     * 减少新闻点赞数
     *
     * @param newsId
     * @param userId
     * @return
     */
    StatusBean decreaseLikeCount(int newsId, int userId);

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
}
