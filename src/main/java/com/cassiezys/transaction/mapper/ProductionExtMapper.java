package com.cassiezys.transaction.mapper;

import com.cassiezys.transaction.dto.ProductQueryDTO;
import com.cassiezys.transaction.model.Production;

import java.util.List;

public interface ProductionExtMapper {
    /**
     * 自增阅读量
     * @param production
     * @return
     */
    int incViewCount(Production production);

    /**
     * 自增评论数
     * @param production 目标商品
     * @return
     */
    int incCommentCount(Production production);

    /**
     * 总数量：查找符合要求的商品总数量
     * @param productQueryDTO 查询条件
     * @return 符合条件的商品数量
     */
    int countByQuery(ProductQueryDTO productQueryDTO);

    /**
     * 商品集合：查找符合查询条件的商品集合
     * @param productQueryDTO 查询条件
     * @return 符合条件的商品集合
     */
    List<Production> selectByQuery(ProductQueryDTO productQueryDTO);
}
