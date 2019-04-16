package com.shanyi.itemsharing.dao;

import com.shanyi.itemsharing.model.Comment;

import java.util.List;

public interface CommentDao {
    int insertComment(Comment comment);

    List<Comment> selectCommentByNewsId(int newsId);
}
