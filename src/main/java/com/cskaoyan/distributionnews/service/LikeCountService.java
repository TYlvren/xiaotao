package com.cskaoyan.distributionnews.service;

import com.cskaoyan.distributionnews.bean.StatusBean;

public interface LikeCountService {

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
}
