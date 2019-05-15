package com.xiaotao.share.service;

import com.xiaotao.share.bean.Page;
import com.xiaotao.share.model.Comment;

public interface CommentService {

    /**
     * 通过goodsId分页查找Comment
     * @param goodsId
     * @return
     */
    Page<Comment> findCommentPage(Page<Comment> page, int goodsId);

    /**
     * 为goods添加一条评论
     * @param comment
     */
    int addComment(Comment comment);
}
