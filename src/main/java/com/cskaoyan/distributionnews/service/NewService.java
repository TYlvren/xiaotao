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
    List<New> findNew();

    /**
     * 通过id查找new
     * @param id
     * @return
     */
    New findNew(int id);

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
}
