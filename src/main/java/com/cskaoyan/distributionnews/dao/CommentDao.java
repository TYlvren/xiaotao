package com.cskaoyan.distributionnews.dao;

import com.cskaoyan.distributionnews.model.Comment;

import java.util.List;

public interface CommentDao {
    int insertComment(Comment comment);

    List<Comment> selectCommentByNewsId(int newsId);
}
