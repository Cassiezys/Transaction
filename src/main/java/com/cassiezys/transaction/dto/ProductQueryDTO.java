package com.cassiezys.transaction.dto;

import lombok.Data;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:商品分页查找并显示
 */
@Data
public class ProductQueryDTO {
    private Integer page;
    private Integer size;
}
