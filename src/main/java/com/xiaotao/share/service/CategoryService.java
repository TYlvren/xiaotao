package com.xiaotao.share.service;


import com.xiaotao.share.model.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 查找所有的类目，返回Category的List，没有找到返回null
     * @return
     */
     List<Category> findAll()  ;
}
