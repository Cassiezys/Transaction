package com.cassiezys.transaction.mapper;

import com.cassiezys.transaction.model.Comment;

public interface CommentExtMapper {
    /**
     * 自增评论的回复数
     * @param comment
     * @return
     */
    int incCommentCount(Comment comment);
}
