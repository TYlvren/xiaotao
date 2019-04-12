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
        if(i != 1){
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
    public List<New> findNew() {
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
    @Transactional
    public int increaseLikeCount(int newsId, int userId) throws Exception {
        String userIdString = String.valueOf(userId);

        //该新闻点赞set添加此userId，如果已经存在则不进行添加操作
        Boolean sismember = jedis.sismember(newsId + "_like", userIdString);
        if(!sismember) {
            Long sadd = jedis.sadd(newsId + "_like", userIdString);
            if (sadd != 1){
                throw new Exception("向" + newsId + "_like添加元素异常");
            }
        }

        //该新闻点踩set删除此userId,如果不存在则不进行删除操作
        sismember = jedis.sismember(newsId + "_dislike", userIdString);
        if(sismember){
            Long srem = jedis.srem(newsId + "_dislike", userIdString);
            if (srem != 1){
                throw new Exception("向" + newsId + "_dislike删除元素异常");
            }
        }
        return newDao.increaseLikeCountById(newsId);
    }


    /**
     * 减少新闻点赞数
     *
     * @param newsId
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public int decreaseLikeCount(int newsId, int userId) throws Exception {
        String userIdString = String.valueOf(userId);

        //该新闻点踩set添加此userId，如果已经存在则不进行添加操作
        Boolean sismember = jedis.sismember(newsId + "_dislike", userIdString);
        if(!sismember) {
            Long sadd = jedis.sadd(newsId + "_dislike", userIdString);
            if (sadd != 1){
                throw new Exception("向" + newsId + "_dislike添加元素异常");
            }
        }

        //该新闻点赞set删除此userId,如果不存在则不进行删除操作
        sismember = jedis.sismember(newsId + "_like", userIdString);
        if(sismember){
            Long srem = jedis.srem(newsId + "_like", userIdString);
            if (srem != 1){
                throw new Exception("向" + newsId + "_like删除元素异常");
            }
        }
        return newDao.decreaseLikeCountById(newsId);
    }


    @Override
    public int findLikeCount(int newsId) {
        return newDao.selectLikeCountById(newsId);
    }
}
