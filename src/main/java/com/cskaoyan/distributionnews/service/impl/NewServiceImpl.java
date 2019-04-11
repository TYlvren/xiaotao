package com.cskaoyan.distributionnews.service.impl;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.dao.NewDao;
import com.cskaoyan.distributionnews.model.New;
import com.cskaoyan.distributionnews.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class NewServiceImpl implements NewService {


    @Autowired
    private NewDao newDao;

    /**
     * 添加news
     *
     * @param news
     */
    @Override
    public StatusBean addNews(New news) {
        int i = newDao.insertNews(news);
        StatusBean statusBean = new StatusBean();
        if(i != 1){
            statusBean.setCode(2);
            statusBean.setMsg("异常");
            return statusBean;
        }

        statusBean.setCode(0);
        statusBean.setMsg("成功");
        return statusBean;
    }

    /**
     * 查找所有的new
     *
     * @return
     */
    @Override
    public List<New> findNew() {
        return newDao.selectAllNew();
    }

}
