package com.cassiezys.transaction.service;

import com.cassiezys.transaction.dto.CommentDTO;
import com.cassiezys.transaction.enums.CommentTypeEnum;
import com.cassiezys.transaction.enums.NotificationTypeEnum;
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
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private OperateMapper operateMapper;

    /**
     * 返回该商品的评论 或者 是评论的回复
     * @param targetId 商品id 或者 评论id
     * @param commentTypeEnum （1)级问题评论 或(2)级回复评论
     * @return 评论/回复
     */
    public List<CommentDTO> findByTargetId(User loginuser, Long targetId, CommentTypeEnum commentTypeEnum) {
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

        if(commentTypeEnum==CommentTypeEnum.QUESTION){
            OperateExample operateExample = new OperateExample();
            operateExample.createCriteria()
                    .andCreatorEqualTo(loginuser.getId())
                    .andTypeEqualTo(2)
                    .andStatusEqualTo(1);
            List<Operate> operates= operateMapper.selectByExample(operateExample);
            List<Long> comments=operates.stream().map(operate -> operate.getParentId()).collect(Collectors.toList());
            commentDTOS = commentDTOS.stream().map(commentDto -> {
                if(comments.contains(commentDto.getId()))
                    commentDto.setStatus(1);
                else commentDto.setStatus(0);
                return commentDto;
            }).collect(Collectors.toList());
        }
        return commentDTOS;
    }

    /**
     * 添加评论，自增评论数++-》事务
     * 添加 通知
     * @param comment 新评论
     * @param commentator 评论创建者
     */
    @Transactional
    public void addComment(Comment comment, User commentator) {
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
            //添加 有人评论商品的 通知
            Notification notification = createNotification(comment, commentator, thisProdt.getId(), thisProdt.getCreator(), thisProdt.getTitle(), NotificationTypeEnum.REPLY_PRODUCTION.getType());
            notificationMapper.insert(notification);
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
            Comment tempComment = new Comment();
            tempComment.setId(thisComment.getId());
            tempComment.setCommentCount(1);
            commentExtMapper.incCommentCount(tempComment);
            //添加有人评论 你的回复的通知
            Notification notification = createNotification(comment, commentator, thisProdt.getId(), comment.getCommentator(),comment.getContent() , NotificationTypeEnum.REPLY_COMMENT.getType() );
            notificationMapper.insert(notification);
        }
    }

    /**
     * 创建通知
     * @param comment 新创的评论
     * @param commentator 新评论的创建者User
     * @param outerId 商品的id
     * @param receiver  接收者id  商品的创建者/某评论的建立者
     * @param outerTitle 商品名/某评论
     * @param type 回复商品/回复评论
     * @return
     */
    private Notification createNotification(Comment comment, User commentator, Long outerId, Long receiver, String outerTitle, int type) {
        Notification notification = new Notification();
        notification.setNotifier(comment.getCommentator());
        notification.setNotifierName(commentator.getName());
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setOuterid(outerId);
       /* 商品名   评论 */
        notification.setOuterTitle(outerTitle);
        /* 商品的创建者；  评论的发出者 */
        notification.setReceiver(receiver);
        notification.setStatus(0);
        notification.setType(type);
        return notification;
    }
}
