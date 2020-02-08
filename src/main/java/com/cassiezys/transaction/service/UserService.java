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

}
