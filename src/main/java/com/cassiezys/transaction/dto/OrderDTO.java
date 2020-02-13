package com.cassiezys.transaction.dto;

import com.cassiezys.transaction.model.Production;
import com.cassiezys.transaction.model.User;
import lombok.Data;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:订单、买家、卖家、地址
 */
@Data
public class OrderDTO {
    private Long id;
    private Long outerid;
    private Long creator;
    private String outerTitle;
    private Long receiver;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer amount;
    private String address;
    private Long tele;
    private Integer status;
    private String statusName;

    /*商品
    private String picUrl;
    private float price;
    private float origprice;
    private String category;*/

    //卖家信息
    private Production production;
    private User seller;
}
