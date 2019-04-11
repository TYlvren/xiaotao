package com.cskaoyan.distributionnews.service;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.model.New;


import java.util.List;


public interface NewService {

    /**
     * 添加news
     * @param news
     */
    StatusBean addNews(New news);

    /**
     * 查找所有的new
     * @return
     */
    List<New> findNew();

}
