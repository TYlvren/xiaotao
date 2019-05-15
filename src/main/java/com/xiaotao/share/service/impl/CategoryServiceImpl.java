package com.xiaotao.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaotao.share.dao.CategoryDao;
import com.xiaotao.share.model.Category;
import com.xiaotao.share.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;
    /**
     * 查找所有的类目，返回Category的List，没有找到返回null
     * @return
     * @throws Exception
     */
    @Override
    public List<Category> findAll(){

        try {
            return categoryDao.selectList(new QueryWrapper<>());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

