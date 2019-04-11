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

import java.util.List;
@Service
public class NewServiceImpl implements NewService {


    @Autowired
    private NewDao newDao;

    @Autowired
    private CommentDao commentDao;

    /**
     * 添加news
     *
     * @param news
     */
    @Override
    public StatusBean addNews(New news) {
        int i = newDao.insertNews(news);
        StatusBean statusBean = new StatusBean();
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
}
