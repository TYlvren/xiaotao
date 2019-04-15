package com.cskaoyan.distributionnews.service.impl;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.dao.NewDao;
import com.cskaoyan.distributionnews.service.LikeCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class LikeCountServiceImpl implements LikeCountService {

    private final NewDao newDao;

    private final Jedis jedis;

    private final StatusBean statusBean;

    @Autowired
    public LikeCountServiceImpl(NewDao newDao, Jedis jedis, StatusBean statusBean) {
        this.newDao = newDao;
        this.jedis = jedis;
        this.statusBean = statusBean;
    }


    /**
     * 增加新闻点赞数
     *
     * @param newsId
     * @param userId
     * @return
     */
    @Override
    public StatusBean increaseLikeCount(int newsId, int userId) {
        String userIdString = String.valueOf(userId);

        //在Redis中newsId_like set添加此userId
        Long sadd = jedis.sadd(newsId + "_like", userIdString);
        if (sadd != 1) {
            statusBean.setCodeAndMsg(1, "异常");
            return statusBean;
        }

        //在Redis中newsId_dislike set删除此userId,如果不存在则不进行删除操作
        Boolean sismember = jedis.sismember(newsId + "_dislike", userIdString);
        if (sismember) {
            Long srem = jedis.srem(newsId + "_dislike", userIdString);
            if (srem != 1) {
                statusBean.setCodeAndMsg(1, "异常");
                return statusBean;
            }
        }

        //增加数据库中news的点赞数
        int i = newDao.increaseLikeCountById(newsId);

        int msg = newDao.selectLikeCountById(newsId);
        if (i == 1) {
            statusBean.setCodeAndMsg(0, String.valueOf(msg));
        } else {
            statusBean.setCodeAndMsg(1, "异常");
        }

        return statusBean;
    }


    /**
     * 减少新闻点赞数
     *
     * @param newsId
     * @param userId
     * @return
     */
    @Override
    public StatusBean decreaseLikeCount(int newsId, int userId) {
        String userIdString = String.valueOf(userId);

        //查询数据库当前的点赞数
        int msg = newDao.selectLikeCountById(newsId);

        //点赞数为0时不能点踩
        if (msg == 0) {
            statusBean.setMsg("0");
            return statusBean;
        }

        //如果没有点赞则不能点踩
        Boolean sismember = jedis.sismember(newsId + "_like", userIdString);
        if (!sismember) {
            statusBean.setCodeAndMsg(0, String.valueOf(msg));
            return statusBean;
        }

        //在Redis中newsId_dislike set添加此userId
        Long sadd = jedis.sadd(newsId + "_dislike", userIdString);
        if (sadd != 1) {
            statusBean.setCodeAndMsg(1, "异常");
            return statusBean;
        }

        //在Redis中newsId_like set删除此userId
        Long srem = jedis.srem(newsId + "_like", userIdString);
        if (srem != 1) {
            statusBean.setCodeAndMsg(1, "异常");
            return statusBean;
        }

        //数据库中点赞数减1
        int i = newDao.decreaseLikeCountById(newsId);
        if (i == 1) {
            statusBean.setCodeAndMsg(0, String.valueOf(msg - 1));
        } else {
            statusBean.setCodeAndMsg(1, "异常");
        }
        return statusBean;
    }
}
