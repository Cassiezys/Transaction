package com.cassiezys.transaction.dto;
import com.cassiezys.transaction.model.User;
import lombok.Data;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:评论+用户
 */

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Integer commentCount;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String Content;
    private User user;
}
