package com.xiaotao.share.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaotao.share.bean.Page;
import com.xiaotao.share.model.Goods;

public interface GoodsDao extends BaseMapper<Goods> {
    int insertGoods(Goods goods);
    Goods selectGoodsById(int id);

    int updateCommentCountById(int goodsId);

    int increaseLikeCountById(int goodsId);

    int decreaseLikeCountById(int goodsId);

    int selectLikeCountById(int goodsId);

    Page<Goods> selectGoodsByUserId(Page<Goods> page,int userId);

    Page<Goods> selectGoodsByCategoryId(Page<Goods> page,int cid);

    Page<Goods> selectGoodsByFuzzyName(Page<Goods> page,String goodsName);

    Page<Goods> selectGoodsByConcern(Page<Goods> page,int userId);

    Page<Goods> selectAllGoods(Page<Goods> page);

    int updateSoldById(int goodsId);

    Page<Goods> selectGoodsByUserIdIgnoreSold(Page<Goods> page, int userId);
}
