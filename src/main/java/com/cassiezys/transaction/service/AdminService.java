package com.cassiezys.transaction.service;

import com.cassiezys.transaction.mapper.UserMapper;
import com.cassiezys.transaction.model.Orders;
import com.cassiezys.transaction.model.User;
import com.cassiezys.transaction.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:管理员服务层
 */
@Service
public class AdminService {
    @Autowired
    UserMapper userMapper;

    public List<User> findAll(Integer page, Integer size) {
        int offset = size * (page -1);
        UserExample userExample = new UserExample();
        List<User> users = userMapper.selectByExampleWithRowbounds(userExample, new RowBounds(offset, size));
        return users;
    }
}
