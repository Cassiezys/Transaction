package com.cassiezys.transaction.mapper;

import com.cassiezys.transaction.dto.ProductQueryDTO;
import com.cassiezys.transaction.model.Production;

import java.util.List;

public interface ProductionExtMapper {
    int countByQuery(ProductQueryDTO productQueryDTO);
    List<Production> selectByQuery(ProductQueryDTO productQueryDTO);
}
