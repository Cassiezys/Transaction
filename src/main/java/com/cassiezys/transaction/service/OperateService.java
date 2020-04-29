package com.cassiezys.transaction.service;

import com.cassiezys.transaction.enums.NoticifacionStatusEnum;
import com.cassiezys.transaction.enums.NotificationTypeEnum;
import com.cassiezys.transaction.enums.OperateTypeEnum;
import com.cassiezys.transaction.exception.CustomizeCodeException;
import com.cassiezys.transaction.exception.ErrorCode;
import com.cassiezys.transaction.exception.ErrorCodeEnumImp;
import com.cassiezys.transaction.mapper.*;
import com.cassiezys.transaction.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:收藏，点赞，警告操作
 */
@Service
public class OperateService {
    @Autowired
    OperateMapper operateMapper;
    @Autowired
    ProductionMapper productionMapper;
    @Autowired
    ProductionExtMapper productionExtMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentExtMapper commentExtMapper;
    @Autowired
    NotificationMapper notificationMapper;

    public void modifyFavor(Long pid, User user,int status)
    {
        if(status==1){
            //增加商品的欢迎度
            Production production=new Production();
            production.setId(pid);
            production.setLikeCount(1);
            productionExtMapper.incLikeCount(production);

            OperateExample operateExample = new OperateExample();
            operateExample.createCriteria()
                    .andCreatorEqualTo(user.getId())
                    .andParentIdEqualTo(pid);
            List<Operate> operates = operateMapper.selectByExample(operateExample);
            if(operates.size()==0){
                //添加数据库
                Operate operate=new Operate();
                operate.setParentId(pid);
                operate.setType(OperateTypeEnum.typeOf("收藏"));
                operate.setCreator(user.getId());
                operate.setGmtCreate(System.currentTimeMillis());
                operate.setGmtModified(operate.getGmtCreate());
                operate.setStatus(1);
                operateMapper.insert(operate);
            }else{
                //修改而已
                operates.get(0).setGmtModified(System.currentTimeMillis());
                operates.get(0).setStatus(1);
                operateMapper.updateByPrimaryKeySelective(operates.get(0));
            }
        }
        else{
            //删除商品欢迎度
            Production production=new Production();
            production.setId(pid);
            production.setLikeCount(-1);
            productionExtMapper.incLikeCount(production);

            //更改数据库状态
            Operate operate= new Operate();
            operate.setGmtModified(System.currentTimeMillis());
            operate.setStatus(0);
            OperateExample operateExample = new OperateExample();
            operateExample.createCriteria()
                    .andCreatorEqualTo(user.getId())
                    .andParentIdEqualTo(pid);

            operateMapper.updateByExampleSelective(operate,operateExample);
        }


    }

    /**
     * 更改评论的点赞状态
     * @param cid
     * @param user
     * @param status
     */
    public void modifyThumbs(Long cid, User user, int status) {
        if(status==1){
            //增加评论的点赞数
            Comment comment=new Comment();
            comment.setId(cid);
            comment.setLikeCount(1);
            commentExtMapper.incLikeCount(comment);

            OperateExample operateExample = new OperateExample();
            operateExample.createCriteria()
                    .andCreatorEqualTo(user.getId())
                    .andParentIdEqualTo(cid);
            List<Operate> operates = operateMapper.selectByExample(operateExample);

            Comment thisComment = commentMapper.selectByPrimaryKey(cid);
            Production thisProduct = productionMapper.selectByPrimaryKey(thisComment.getParentId());
            if(operates.size()==0){
                //添加数据库
                Operate operate=new Operate();
                operate.setParentId(cid);
                operate.setType(OperateTypeEnum.typeOf("点赞"));
                operate.setCreator(user.getId());
                operate.setGmtCreate(System.currentTimeMillis());
                operate.setGmtModified(operate.getGmtCreate());
                operate.setStatus(1);
                operateMapper.insert(operate);

                //添加通知
                Notification notification = new Notification();
                notification.setStatus(NoticifacionStatusEnum.UNREAD.getStatus());
                notification.setType(NotificationTypeEnum.COMMENT_FAVOR.getType());
                notification.setReceiver(thisComment.getCommentator());
                notification.setOuterid(thisProduct.getId());
                notification.setOuterTitle(thisProduct.getTitle());
                notification.setGmtCreate(System.currentTimeMillis());
                notification.setNotifier(user.getId());
                notification.setNotifierName(user.getName());
                notificationMapper.insert(notification);
            }else{
                //修改而已
                operates.get(0).setGmtModified(System.currentTimeMillis());
                operates.get(0).setStatus(1);
                operateMapper.updateByPrimaryKeySelective(operates.get(0));

                //修改而已
                NotificationExample notificationExample = new NotificationExample();
                notificationExample.createCriteria()
                        .andReceiverEqualTo(thisComment.getCommentator())
                        .andOuteridEqualTo(thisProduct.getId())
                        .andNotifierEqualTo(user.getId());
                List<Notification> notifications = notificationMapper.selectByExample(notificationExample);
                if(notifications.size()==0){
                    throw new CustomizeCodeException(ErrorCodeEnumImp.NOTIFICATION_NOT_FOUND);
                }if(notifications.get(0).getStatus()==NoticifacionStatusEnum.READ.getStatus()){
                    notifications.get(0).setStatus(NoticifacionStatusEnum.UNREAD.getStatus());
                    notificationMapper.updateByPrimaryKey(notifications.get(0));
                }
            }
        }
        else{
            //删除评论欢迎度
            Comment comment=new Comment();
            comment.setId(cid);
            comment.setLikeCount(-1);
            commentExtMapper.incLikeCount(comment);

            //更改数据库状态
            Operate operate= new Operate();
            operate.setGmtModified(System.currentTimeMillis());
            operate.setStatus(0);
            OperateExample operateExample = new OperateExample();
            operateExample.createCriteria()
                    .andCreatorEqualTo(user.getId())
                    .andParentIdEqualTo(cid);

            operateMapper.updateByExampleSelective(operate,operateExample);
        }
    }
}
