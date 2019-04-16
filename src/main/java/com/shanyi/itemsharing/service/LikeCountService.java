package com.shanyi.itemsharing.service;

import com.shanyi.itemsharing.asyncevent.Event;
import com.shanyi.itemsharing.bean.StatusBean;

public interface LikeCountService {

    /**
     * 增加Mysql新闻点赞数
     * @param event
     * @return
     */
    void increaseLikeCount(Event event);

    /**
     * 添加用户like news至Redis
     * @param newsId
     * @param userId
     * @return
     */
    StatusBean addLikeToRedis(int newsId, int userId);

    /**
     * 减少MySql新闻点赞数
     *
     * @param event
     * @return
     */
    void decreaseLikeCount(Event event);

    /**
     * 添加用户dislike news至Redis
     * @param newsId
     * @param userId
     * @return
     */
    StatusBean addDislikeToRedis(int newsId, int userId);
}
