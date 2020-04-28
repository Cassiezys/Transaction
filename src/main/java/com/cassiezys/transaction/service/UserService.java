package com.cassiezys.transaction.service;

import com.cassiezys.transaction.mapper.UserMapper;
import com.cassiezys.transaction.model.User;
import com.cassiezys.transaction.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    /**
     * 创建用户
     * @param userInput
     * @return 1 创建成功
     * @return 0 创建失败
     */
    public int register(User userInput) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(userInput.getAccountId());
        List<User> users =userMapper.selectByExample(userExample);
        if(users.size()==0){
            String token = UUID.randomUUID().toString();
            userInput.setToken(token);
            userInput.setGmtCreate(System.currentTimeMillis());
            userInput.setGmtModified(userInput.getGmtCreate());
            userMapper.insert(userInput);
            return 1;
        }else{
            //用户名已存在，可以登录
            return 0;
        }
    }

    /**
     * 根据用户名和密码获取用户
     * @param userInput
     */
    public User getUser(User userInput) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(userInput.getAccountId())
                .andPasswordEqualTo(userInput.getPassword());
        List<User> userList = userMapper.selectByExample(userExample);
        if(userList.size()!=0){
            //登录成功
            return userList.get(0);
        }else{
            //登录失败
            return null;
        }
    }


    /**
     * 新建或更新用户:
     * 新用户token保持新的
     * 老用户获得原来的token
     * @param user
     */
    public User createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size()==0){
            user.setPassword(user.getAccountId());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            userMapper.insert(user);
        }else{
            User oldUser = users.get(0);
            User dbUser = new User();
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            /* 不为空的时候就更新 在usermapper.xml里面找到的使用方法*/
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(oldUser.getId());
            userMapper.updateByExampleSelective(dbUser, example);
            user.setToken(oldUser.getToken());
        }
        return user;
    }
}
