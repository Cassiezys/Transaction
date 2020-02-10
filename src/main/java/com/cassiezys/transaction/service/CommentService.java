package com.cassiezys.transaction.service;

import com.cassiezys.transaction.dto.CommentDTO;
import com.cassiezys.transaction.enums.CommentTypeEnum;
import com.cassiezys.transaction.exception.CustomizeCodeException;
import com.cassiezys.transaction.exception.ErrorCodeEnumImp;
import com.cassiezys.transaction.mapper.*;
import com.cassiezys.transaction.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:与评论相关的操作
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductionMapper productionMapper;
    @Autowired
    private ProductionExtMapper productionExtMapper;

    /**
     * 返回该商品的评论 或者 是评论的回复
     * @param targetId 商品id 或者 评论id
     * @param commentTypeEnum （1)级问题评论 或(2)级回复评论
     * @return 评论/回复
     */
    public List<CommentDTO> findByTargetId(Long targetId, CommentTypeEnum commentTypeEnum) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(targetId)
                .andTypeEqualTo(commentTypeEnum.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        if(commentList.size() ==0){
            return new ArrayList<>();
        }
        /*先找所有需要查找的用户*/
        Set<Long> userIdSet = commentList.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIdList = new ArrayList<>();
        userIdList.addAll(userIdSet);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIdList);
        List<User> userList = userMapper.selectByExample(userExample);

        /*匹配评论与用户*/
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDTO> commentDTOS = commentList.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(commentDTO.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }

    /**
     * 添加评论，自增评论数++-》事务
     * @param comment 新评论
     * @param user 评论人
     */
    @Transactional
    public void addComment(Comment comment, User user) {
        if (comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeCodeException(ErrorCodeEnumImp.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null ||!CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeCodeException((ErrorCodeEnumImp.TYPE_NOT_FOUND));
        }
        if(comment.getType() == CommentTypeEnum.QUESTION.getType()){
            //评论商品
            Production thisProdt = productionMapper.selectByPrimaryKey(comment.getParentId());
            if(thisProdt == null){
                throw new CustomizeCodeException(ErrorCodeEnumImp.PRODUCTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            thisProdt.setCommentCount(1);
            productionExtMapper.incCommentCount(thisProdt);
        }else if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment thisComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(thisComment == null){
                throw new CustomizeCodeException(ErrorCodeEnumImp.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            Production thisProdt = productionMapper.selectByPrimaryKey(thisComment.getParentId());
            if(thisProdt == null){
                throw new CustomizeCodeException(ErrorCodeEnumImp.PRODUCTION_NOT_FOUND);
            }
            /*只能增加评论数：所以新建一个评论*/
            Comment tempComment = new Comment();
            tempComment.setId(thisComment.getId());
            tempComment.setCommentCount(1);
            commentExtMapper.incCommentCount(tempComment);
        }
    }
}
