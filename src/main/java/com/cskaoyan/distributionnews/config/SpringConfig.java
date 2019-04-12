package com.cskaoyan.distributionnews.config;

import com.cskaoyan.distributionnews.bean.StatusBean;
import com.cskaoyan.distributionnews.bean.StatusBeanUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Configuration
public class SpringConfig {

    /**
     * JedisPool
     * @return
     */
    @Bean
    public JedisPool jedisPool(){
        return new JedisPool();
    }

    /**
     * Jedis，默认索引为0的数据库
     * @param jedisPool
     * @return
     */
    @Bean
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
}
