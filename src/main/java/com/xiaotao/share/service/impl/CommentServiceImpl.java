package com.xiaotao.share.service.impl;

import com.xiaotao.share.bean.Page;
import com.xiaotao.share.dao.CommentDao;
import com.xiaotao.share.model.Comment;
import com.xiaotao.share.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    /**
     * 通过goodsId分页查找Comment
     *
     * @param page
     * @param goodsId
     * @return
     */
    @Override
    public Page<Comment> findCommentPage(Page<Comment> page, int goodsId) {
        return commentDao.selectCommentByGoodsId(page,goodsId);
    }



    /**
     * 为goods添加一条评论
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
}
