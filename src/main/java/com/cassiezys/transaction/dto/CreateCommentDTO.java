package com.cassiezys.transaction.dto;

import lombok.Data;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:Javascript ajax 传输的 data:传输RequestBody：ProId和CommentType
 */
@Data
public class CreateCommentDTO {
    /**
     * 回复的对象的id：商品的id或者是商品评论的id
     */
    private Long parentId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论类型
     */
    private Integer type;
}
