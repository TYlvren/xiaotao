package com.cskaoyan.distributionnews;

import com.cskaoyan.distributionnews.bean.StatusBean;
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
    private StatusBean statusBean;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testSpringBean(){

        logger.info("引用前： " + statusBean.toString());

        StatusBean statusBean1 = this.statusBean;
        statusBean1.setCode(2);
        logger.info("引用时改变值： " + statusBean1.toString());

        statusBean1 = new StatusBean();

        logger.info("引用后： " + statusBean.toString());
    }

}

