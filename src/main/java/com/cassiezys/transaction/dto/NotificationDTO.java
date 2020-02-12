package com.cassiezys.transaction.dto;

import lombok.Data;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:通知
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long notifier;
    private Long receiver;
    private Long outerid;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private String outerTitle;
    private String notifierName;
    //将数据库中的Integer 翻译成 中文
    private String typeName;
}
