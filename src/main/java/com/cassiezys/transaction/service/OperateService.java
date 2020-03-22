package com.cassiezys.transaction.service;

import com.cassiezys.transaction.enums.OperateTypeEnum;
import com.cassiezys.transaction.mapper.CommentExtMapper;
import com.cassiezys.transaction.mapper.OperateMapper;
import com.cassiezys.transaction.mapper.ProductionExtMapper;
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
    ProductionExtMapper productionExtMapper;
    @Autowired
    CommentExtMapper commentExtMapper;

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
            }else{
                //修改而已
                operates.get(0).setGmtModified(System.currentTimeMillis());
                operates.get(0).setStatus(1);
                operateMapper.updateByPrimaryKeySelective(operates.get(0));
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
