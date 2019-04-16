package com.cskaoyan.distributionnews.dao;

import com.cskaoyan.distributionnews.model.New;

import java.util.List;

public interface NewDao {
    int insertNews(New news);

    List<New> selectAllNew();

    New selectNewById(int id);

    int updateCommentCountById(int newsId);

    int increaseLikeCountById(int newsId);

    int decreaseLikeCountById(int newsId);

    int selectLikeCountById(int newsId);

    List<New> selectNewsByUserId(int userId);

}
