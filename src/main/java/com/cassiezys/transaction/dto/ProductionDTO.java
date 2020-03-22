package com.cassiezys.transaction.dto;

import com.cassiezys.transaction.model.User;
import lombok.Data;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:商品+用户信息
 */
@Data
public class ProductionDTO {
    private Long id;
    private String title;
    private String description;
    private String city;
    private String picUrl;
    private float price;
    private float origprice;
    private Long tele;
    private Long tencent;
    private String wechat;
    private String address;
    private String payway;
    private String category;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer LikeCount;
    private Integer amount;
    //  上方均为 up all contain Prodution production;
    private User user;
    private int Status;//收藏情况
    /*组合用户 首頁 */
}
