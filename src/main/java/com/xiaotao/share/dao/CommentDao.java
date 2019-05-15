package com.xiaotao.share.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaotao.share.bean.Page;
import com.xiaotao.share.model.Comment;

public interface CommentDao extends BaseMapper<Comment> {
    int insertComment(Comment comment);

    Page<Comment> selectCommentByGoodsId(Page<Comment> page,int goodsId);
}
