package com.cskaoyan.distributionnews.service.impl;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.dao.CommentDao;
import com.cskaoyan.distributionnews.dao.NewDao;
import com.cskaoyan.distributionnews.model.Comment;
import com.cskaoyan.distributionnews.model.New;
import com.cskaoyan.distributionnews.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class NewServiceImpl implements NewService {


    @Autowired
    private NewDao newDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private Jedis jedis;

    @Autowired
    private StatusBean statusBean;

    /**
     * 添加news
     *
     * @param news
     */
    @Override
    public StatusBean addNews(New news) {
        int i = newDao.insertNews(news);
        if (i != 1) {
            statusBean.setCode(2);
            statusBean.setMsg("异常");
            return statusBean;
        }

        statusBean.setCode(0);
        statusBean.setMsg("成功");
        return statusBean;
    }

    /**
     * 查找所有的new
     *
     * @return
     */
    @Override
    public List<New> findNews() {
        return newDao.selectAllNew();
    }

    /**
     * 通过id查找new
     *
     * @param id
     * @return
     */
    @Override
    public New findNew(int id) {
        return newDao.selectNewById(id);
    }


    /**
     * 通过id查找new,并查找user是否为new点赞
     *
     * @param id
     * @return
     */
    @Override
    public New findNew(int id, String userIdString) {
        New news = newDao.selectNewById(id);
        setNewsLike(userIdString, news);
        return news;
    }

    private void setNewsLike(String userIdString, New news) {
        int newId = news.getId();
        //查看该新闻是否被当前用户点赞
        Boolean sismember = jedis.sismember(newId + "_like", userIdString);
        //如果被点赞则like为1
        if (sismember) {
            news.setLike(1);
        }
        //查看该新闻是否被当前用户点踩
        sismember = jedis.sismember(newId + "_dislike", userIdString);
        if (sismember) {
            news.setLike(-1);
        }
    }

    /**
     * 为news添加一条评论
     *
     * @param comment
     */
    @Override
    @Transactional
    public int addComment(Comment comment) {
        int i = newDao.updateCommentCountById(comment.getNewsId());
        return i + commentDao.insertComment(comment);
    }

    /**
     * 通过newsId查找Comment
     *
     * @param id
     * @return
     */
    @Override
    public List<Comment> findComment(int id) {
        return commentDao.selectCommentByNewsId(id);
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


    @Override
    public int findLikeCount(int newsId) {
        return newDao.selectLikeCountById(newsId);
    }

    /**
     * 查找某用户发布过的新闻
     *
     * @param userId
     * @return
     */
    @Override
    public List<New> findNewsByUserId(int userId) {
        return newDao.selectNewsByUserId(userId);
    }


    /**
     * 查找Redis判断用户是否为新闻点赞来更新new的like并返回
     *
     * @param userIdString
     * @return
     */
    @Override
    public List<New> findNews(String userIdString) {
        List<New> news = newDao.selectAllNew();
        for (New aNew : news) {
            setNewsLike(userIdString, aNew);
        }

        return news;
    }
}
