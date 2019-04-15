package com.cskaoyan.distributionnews.config;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.bean.StatusBeanUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Configuration
public class SpringConfig {

    @Autowired
    private Logger logger;

    /**
     * JedisPool
     * @return
     */
    @Bean
    public JedisPool jedisPool(){
        return new JedisPool();
    }

    /**
     * Jedis，默认索引为0的数据库，多例模式调用
     * @param jedisPool
     * @return
     */
    @Bean
    @Scope("prototype")
    public Jedis jedis(JedisPool jedisPool){
        return jedisPool.getResource();
    }

    /**
     * 返回给前端的状态StatusBean
     * @return
     */
    @Bean
    public StatusBean statusBean(){
        return new StatusBean();
    }

    /**
     * 返回给前端的注册登录状态StatusBeanUser
     * @return
     */
    @Bean
    public StatusBeanUser statusBeanUser(){
        return new StatusBeanUser();
    }

    @Bean
    public Logger logger(){
        return LoggerFactory.getLogger(this.getClass());
    }
}
