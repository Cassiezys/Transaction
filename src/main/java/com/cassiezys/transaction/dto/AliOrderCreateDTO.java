package com.cassiezys.transaction.dto;

import lombok.Data;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:创建沙箱订单
 */
@Data
public class AliOrderCreateDTO {
    private Long id;
    private Float total;
    private Integer amount;
}
