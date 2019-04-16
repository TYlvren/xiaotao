package com.shanyi.itemsharing.service.impl;

import com.shanyi.itemsharing.bean.StatusBean;
import com.shanyi.itemsharing.dao.CommentDao;
import com.shanyi.itemsharing.dao.NewDao;
import com.shanyi.itemsharing.model.Comment;
import com.shanyi.itemsharing.model.New;
import com.shanyi.itemsharing.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class NewServiceImpl implements NewService {


    private final NewDao newDao;

    private final CommentDao commentDao;

    private final Jedis jedis;

    private final StatusBean statusBean;



    @Autowired
    public NewServiceImpl(NewDao newDao, CommentDao commentDao, Jedis jedis,
                          StatusBean statusBean) {
        this.newDao = newDao;
        this.commentDao = commentDao;
        this.jedis = jedis;
        this.statusBean = statusBean;
    }

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
    public New findNew(int id, int userId) {
        New news = newDao.selectNewById(id);
        String userIdString = String.valueOf(userId);
        setNewsLike(userIdString, news);
        return news;
    }

    private void setNewsLike(String userIdString, New news) {
        if(news == null){
            return;
        }
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
        jedis.close();
    }

    /**
     * 为news添加一条评论
     *
     * @param comment
     */
    @Override
    @Transactional
    public int addComment(Comment comment) {
        if(comment.getContent() == null || comment.getContent().equals("")){
            return 0;
        }
        return commentDao.insertComment(comment);
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
        String userIdString = String.valueOf(userId);
        List<New> news = newDao.selectNewsByUserId(userId);
        for (New aNew : news) {
            setNewsLike(userIdString, aNew);
        }
        return news;
    }


    /**
     * 查找Redis判断用户是否为新闻点赞来更新new的like并返回
     *
     * @param userId
     * @return
     */
    @Override
    public List<New> findNews(int userId) {
        String userIdString = String.valueOf(userId);

        List<New> news = newDao.selectAllNew();
        for (New aNew : news) {
            setNewsLike(userIdString, aNew);
        }

        return news;
    }


    @Override
    public void increaseCommentCount(int newsId) {
       newDao.updateCommentCountById(newsId);
    }
}
