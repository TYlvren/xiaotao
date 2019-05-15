package com.xiaotao.share.config;

import com.xiaotao.share.bean.StatusBean;
import com.xiaotao.share.bean.StatusBeanUser;
import com.xiaotao.share.interceptor.LoginInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    /**
     * 将拦截器注入容器
     * @return
     */
    @Bean
    public LoginInterceptor getLoginInterceptor(){
        return new LoginInterceptor();
    }

    /**
     * 为了使拦截器在容器之后加载，在此引用容器中的拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginInterceptor());
    }


    @Autowired
    private Logger logger;

    /**
     * JedisPool
     *
     * @return
     */
    @Bean
    public JedisPool jedisPool() {
        return new JedisPool();
    }

    /**
     * Jedis，默认索引为0的数据库，多例模式调用
     *
     * @param jedisPool
     * @return
     */
    @Bean
    @Scope("prototype")
    public Jedis jedis(JedisPool jedisPool) {
        return jedisPool.getResource();
    }

    /**
     * 返回给前端的状态StatusBean
     *
     * @return
     */
    @Bean
    public StatusBean statusBean() {
        return new StatusBean();
    }

    /**
     * 返回给前端的注册登录状态StatusBeanUser
     *
     * @return
     */
    @Bean
    public StatusBeanUser statusBeanUser() {
        return new StatusBeanUser();
    }

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }

}
