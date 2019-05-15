package com.xiaotao.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xiaotao.share.asyncevent.Event;
import com.xiaotao.share.bean.StatusBean;
import com.xiaotao.share.dao.ConcernDao;
import com.xiaotao.share.dao.GoodsDao;
import com.xiaotao.share.model.Concern;
import com.xiaotao.share.service.ConcernService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

@Service
public class ConcernServiceImpl implements ConcernService {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private ConcernDao concernDao;

    @Autowired
    private Jedis jedis;

    @Autowired
    private StatusBean statusBean;


    /**
     * 增加物品关注数
     *
     * @param event
     * @return
     */
    @Override
    @Transactional
    public void increaseLikeCount(Event event) {
        int goodsId = event.getTargetId();
        //增加数据库中goods的关注数
        int i = goodsDao.increaseLikeCountById(goodsId);
        //增加数据库中用户关注的物品
        int userId = event.getActiveId();
        i += concernDao.insert(new Concern(userId,goodsId));
    }


    /**
     * 减少物品关注数
     *
     * @param event
     * @return
     */
    @Override
    public void decreaseLikeCount(Event event) {
        int goodsId = event.getTargetId();
        int concernCount = goodsDao.selectLikeCountById(goodsId);

        //关注数大于0数据库才减1
        if (concernCount > 0) {
            //数据库中关注数减1
            int i = goodsDao.decreaseLikeCountById(goodsId);
            int userId = event.getActiveId();
            i += concernDao.delete(new UpdateWrapper<Concern>().eq("user_id",userId).eq("goods_id",goodsId));

        }
    }

    /**
     * 添加用户like goods至Redis
     *
     * @param goodsId
     * @param userId
     * @return
     */
    @Override
    public StatusBean addLikeToRedis(int goodsId, int userId) {
        String userIdString = String.valueOf(userId);

        //在Redis中goodsId_like set添加此userId
        Long sadd = jedis.sadd(goodsId + "_like", userIdString);
        /*if (sadd != 1) {
            statusBean.setCodeAndMsg(1, "异常");
            return statusBean;
        }*/

        //在Redis中goodsId_like_exist set添加此userId,记录该用户已经点过赞
        sadd = jedis.sadd(goodsId + "_like" + "_exist", userIdString);
       /* if (sadd != 1) {
            statusBean.setCodeAndMsg(1, "异常");
            return statusBean;
        }*/

        //在Redis中goodsId_dislike set删除此userId,如果不存在则不进行删除操作
        Boolean sismember = jedis.sismember(goodsId + "_dislike", userIdString);
        if (sismember) {
            Long srem = jedis.srem(goodsId + "_dislike", userIdString);
          /*  if (srem != 1) {
                statusBean.setCodeAndMsg(1, "异常");
                return statusBean;
            }*/
        }

        //查询Redis中关注总数
        Long scard = jedis.scard(goodsId + "_like");

        statusBean.setCodeAndMsg(0, String.valueOf(scard));

        jedis.close();
        return statusBean;
    }

    /**
     * 添加用户dislike goods至Redis
     *
     * @param goodsId
     * @param userId
     * @return
     */
    @Override
    public StatusBean addDislikeToRedis(int goodsId, int userId) {
        String userIdString = String.valueOf(userId);

        //查询Redis当前的关注数
        Long scard = jedis.scard(goodsId + "_like");

        //关注数为0时不能点踩
        if (scard == 0) {
            statusBean.setMsg("0");
            return statusBean;
        }

        //如果没有关注则不能点踩
        Boolean sismember = jedis.sismember(goodsId + "_like", userIdString);
        if (!sismember) {
            statusBean.setCodeAndMsg(0, String.valueOf(scard));
            return statusBean;
        }

        //在Redis中goodsId_dislike set添加此userId
        Long sadd = jedis.sadd(goodsId + "_dislike", userIdString);
      /*  if (sadd != 1) {
            statusBean.setCodeAndMsg(1, "异常");
            return statusBean;
        }*/

        //在Redis中goodsId_like set删除此userId
        Long srem = jedis.srem(goodsId + "_like", userIdString);
     /*   if (srem != 1) {
            statusBean.setCodeAndMsg(1, "异常");
            return statusBean;
        }*/

        scard = jedis.scard(goodsId + "_like");
        statusBean.setCodeAndMsg(0, String.valueOf(scard));
        jedis.close();
        return statusBean;
    }
}
