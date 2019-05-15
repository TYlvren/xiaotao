package com.xiaotao.share.service.impl;

import com.aliyun.oss.OSSClient;
import com.xiaotao.share.bean.Page;
import com.xiaotao.share.bean.StatusBean;
import com.xiaotao.share.dao.CommentDao;
import com.xiaotao.share.dao.GoodsDao;
import com.xiaotao.share.model.Goods;
import com.xiaotao.share.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class GoodsServiceImpl implements GoodsService {


    private final GoodsDao goodsDao;

    private final CommentDao commentDao;

    private final Jedis jedis;

    private final StatusBean statusBean;

    @Autowired
    public GoodsServiceImpl(GoodsDao goodsDao, CommentDao commentDao, Jedis jedis,
                          StatusBean statusBean) {
        this.goodsDao = goodsDao;
        this.commentDao = commentDao;
        this.jedis = jedis;
        this.statusBean = statusBean;
    }

    /**
     * 添加goods
     *
     * @param goods
     */
    @Override
    public StatusBean addGoods(Goods goods) {
        int i = goodsDao.insertGoods(goods);
        if (i != 1) {
            statusBean.setCode(2);
            statusBean.setMsg("异常");
            return statusBean;
        }

        statusBean.setCode(0);
        statusBean.setMsg("成功");
        return statusBean;
    }


    /**
     * 通过id查找new
     *
     * @param id
     * @return
     */
    @Override
    public Goods findGood(int id) {
        return goodsDao.selectGoodsById(id);
    }

    @Override
    public int findLikeCount(int goodsId) {
        return goodsDao.selectLikeCountById(goodsId);
    }

    /**
     * 查找某用户发布过的物品,分页
     *
     * @param userId
     * @return
     */
    @Override
    public Page<Goods> findGoodsByUserIdPage(Page<Goods> page,int userId) {
        return goodsDao.selectGoodsByUserId(page,userId);
    }

    @Override
    public void increaseCommentCount(int goodsId) {
       goodsDao.updateCommentCountById(goodsId);
    }



    @Override
    public Page<Goods> findGoodsByFuzzyName(Page<Goods> page,String goodsName) {
        return goodsDao.selectGoodsByFuzzyName(page,goodsName);
    }

    @Override
    public Page<Goods> findGoodsByConcern(Page<Goods> page,int userId) {
        return goodsDao.selectGoodsByConcern(page,userId);
    }


    /**
     * 分页查找所有goods
     *
     * @param page
     * @return
     */
    @Override
    public Page<Goods> findGoodsPage(Page<Goods> page) {
        return goodsDao.selectAllGoods(page);
    }

    /**
     * 根据分类id搜索物品
     *
     * @param cid
     * @return
     */
    @Override
    public Page<Goods> findGoodsByCategoryIdPage(Page<Goods> page,int cid) {
        return goodsDao.selectGoodsByCategoryId(page,cid);
    }

    /**
     * 查找用户分享的物品，忽略已售出的物品
     *
     * @param page
     * @param id
     * @return
     */
    @Override
    public Page<Goods> findGoodsByUserIdIgnoreSoldPage(Page<Goods> page, int id) {
        return goodsDao.selectGoodsByUserIdIgnoreSold(page,id);
    }

    /**
     * 将物品标记为已售
     *
     * @param goodsId
     * @return
     */
    @Override
    public boolean soldGoods(int goodsId) {
        int i = goodsDao.updateSoldById(goodsId);
        return i==1;
    }

    /**
     * 删除物品
     *
     * @param goodsId
     * @return
     */
    @Override
    public boolean deleteGoods(int goodsId) {
        int i = goodsDao.deleteById(goodsId);
        return i==1;
    }

    /**
     * 上传图片到阿里云OSS
     *
     * @param file
     * @return
     */
    @Override
    public StatusBean uploadFileToAliyun(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。
        // 强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAIp7WJZjLwzPwd";
        String accessKeySecret = "f9QDMgVp1jXxDBa4KhTQgH1Ok7SrQW";

        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        /*// 上传文件流。
        String content = "Hello OSS";*/

        //获取当前日期字符串
        long timeMillis = System.currentTimeMillis();
        Date date = new Date(timeMillis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);

        //获取上传文件名
        String originalFilename = file.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        String newFilename = format + "/" + uuid + "_" + originalFilename;

        //OSS中文件夹的概念仅是一个逻辑概念，定义object的key为abc/1.jpg就会在该bucket下创建一个abc的文件夹
        String bucketName = "xiaotaoshare";

        try {
            ossClient.putObject(bucketName, newFilename, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            statusBean.setMsg("上传异常");
            statusBean.setCode(1);
            return statusBean;
        }

        // 关闭OSSClient。
        ossClient.shutdown();

        //以缩略图的形式显示
        String msg = "https://" + bucketName + ".oss-cn-beijing.aliyuncs.com/" + newFilename + "?x-oss-process=image/resize,w_120,h_100";
        statusBean.setMsg(msg);
        statusBean.setCode(0);
        return statusBean;
    }
}
