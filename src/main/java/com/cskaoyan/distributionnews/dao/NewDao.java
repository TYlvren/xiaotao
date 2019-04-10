package com.cskaoyan.distributionnews.dao;

import com.cskaoyan.distributionnews.model.New;

import java.util.List;

public interface NewDao {
    int insertNews(New news);

    List<New> selectAllNew();
}
