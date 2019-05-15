package com.xiaotao.share.service;

import com.xiaotao.share.bean.Page;
import com.xiaotao.share.bean.StatusBean;
import com.xiaotao.share.model.Goods;
import org.springframework.web.multipart.MultipartFile;


public interface GoodsService {

    /**
     * 添加goods
     * @param goods
     */
    StatusBean addGoods(Goods goods);

    /**
     * 通过goodsId查找goods
     *
     * @param id
     * @return
     */
    Goods findGood(int id);


    /**
     * 查找物品关注数
     * @param goodsId
     * @return
     */
    int findLikeCount(int goodsId);

    /**
     * 查找某用户发布过的物品
     * @param userId
     * @return
     */
    Page<Goods> findGoodsByUserIdPage(Page<Goods> page,int userId);

    /**
     * 增加物品评论数
     * @param goodsId
     */
    void increaseCommentCount(int goodsId);

    /**
     * 上传图片到阿里云OSS
     * @param file
     * @return
     */
    StatusBean uploadFileToAliyun(MultipartFile file);

    /**
     * 模糊搜索物品名称进行查找物品，分页
     * @param page
     * @param goodsName
     * @return
     */
    Page<Goods> findGoodsByFuzzyName(Page<Goods> page,String goodsName);

    /**
     * 查找用户关注的物品，分页
     * @param page
     * @param userId
     * @return
     */
    Page<Goods> findGoodsByConcern(Page<Goods> page,int userId);

    /**
     * 分页查找所有goods
     * @param page
     * @return
     */
    Page<Goods> findGoodsPage(Page<Goods> page);

    /**
     * 根据分类id搜索物品
     * @param cid
     * @return
     */
    Page<Goods> findGoodsByCategoryIdPage(Page<Goods> page,int cid);

    /**
     *
     * 将物品标记为已售
     * @param goodsId
     * @return
     */
    boolean soldGoods(int goodsId);

    /**
     * 删除物品
     * @param goodsId
     * @return
     */
    boolean deleteGoods(int goodsId);

    /**
     * 查找用户分享的物品，忽略已售出的物品
     * @param page
     * @param id
     * @return
     */
    Page<Goods> findGoodsByUserIdIgnoreSoldPage(Page<Goods> page, int id);
}
