package com.xiaotao.share.service;

import com.xiaotao.share.asyncevent.Event;
import com.xiaotao.share.bean.StatusBean;

public interface ConcernService {

    /**
     * 增加Mysql物品关注数
     * @param event
     * @return
     */
    void increaseLikeCount(Event event);

    /**
     * 添加用户like goods至Redis
     * @param goodsId
     * @param userId
     * @return
     */
    StatusBean addLikeToRedis(int goodsId, int userId);

    /**
     * 减少MySql物品关注数
     *
     * @param event
     * @return
     */
    void decreaseLikeCount(Event event);

    /**
     * 添加用户dislike goods至Redis
     * @param goodsId
     * @param userId
     * @return
     */
    StatusBean addDislikeToRedis(int goodsId, int userId);
}
