package com.xiaotao.share.aop;

import com.xiaotao.share.bean.Page;
import com.xiaotao.share.model.Goods;
import com.xiaotao.share.model.User;
import com.xiaotao.share.util.ThreadLocalUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;

@Aspect
@Component
public class GoodsLikeAop {

    @Autowired
    private Jedis jedis;

    @Pointcut("execution(public * com.xiaotao.share.service.GoodsService.findGoods*(..))")
    public void findGoods() {
    }

    @Pointcut("execution(public * com.xiaotao.share.service.GoodsService.findGood(..))")
    public void findGood() {
    }

    @Around("findGoods()")
    public Object findGoodsAround(ProceedingJoinPoint joinPoint) throws Throwable {
        User user = ThreadLocalUtils.get();
        if (user == null) {
            return joinPoint.proceed();
        } else {

            Page<Goods> page = (Page<Goods>) joinPoint.proceed();
            String userIdString = String.valueOf(user.getId());
            List<Goods> goodsList = page.getRecords();
            for (Goods goods : goodsList) {
                setGoodsLike(userIdString, goods);
            }
            return page;
        }
    }

    @Around("findGood()")
    public Object findGoodAround(ProceedingJoinPoint joinPoint) throws Throwable {
        User user = ThreadLocalUtils.get();
        if (user == null) {
            return joinPoint.proceed();
        } else {
            Goods goods = (Goods) joinPoint.proceed();
            String userIdString = String.valueOf(user.getId());
            setGoodsLike(userIdString, goods);
            return goods;
        }
    }

    private void setGoodsLike(String userIdString, Goods goods) {
        if (goods == null) {
            return;
        }
        int newId = goods.getId();
        //查看该物品是否被当前用户关注
        Boolean sismember = jedis.sismember(newId + "_like", userIdString);
        //如果被关注则like为1
        if (sismember) {
            goods.setLike(1);
        }
        //查看该物品是否被当前用户点踩
        sismember = jedis.sismember(newId + "_dislike", userIdString);
        if (sismember) {
            goods.setLike(-1);
        }
        jedis.close();
    }
}
