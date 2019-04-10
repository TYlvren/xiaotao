package com.cskaoyan.distributionnews;

import com.cskaoyan.distributionnews.dao.UserDao;
import com.cskaoyan.distributionnews.model.User;
import com.cskaoyan.distributionnews.service.NewsService;
import com.cskaoyan.distributionnews.util.StatusBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistributionnewsApplicationTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private NewsService newsService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testUserDao() {
        User user = new User(1,"admin","admin",null);
        int i = userDao.insertUser(user);
        logger.info("i=" + i);
    }


    @Test
    public void testLogin(){
        User user = new User(1,"admin","admin",null);
        StatusBean statusBean = newsService.loginUser(user,null);
    }

}
