package com.cassiezys.transaction.controller;

import com.cassiezys.transaction.dto.ResultDTO;
import com.cassiezys.transaction.exception.ErrorCodeEnumImp;
import com.cassiezys.transaction.model.User;
import com.cassiezys.transaction.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:后台管理:
 * 要添加@RestController 不然就添加@ResponseBody+@controller 因为大部分都是json，所以直接用restController
 */
@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @CrossOrigin
    @GetMapping("/city/find")
    public Map<String,String> searchCity(@RequestParam(name="name")String name){
        Map<String,String> mp = new HashMap<>();
        String weather=getWeather(name);
        mp.put("message",weather);
        return mp;
    }
    public String getWeather(String name){
        Map<String,String> mp = new HashMap<>();
        mp.put("北京","天气晴");
        mp.put("天津","天气晴");
        mp.put("成都","天气晴");
        mp.put("上海","天气晴");
        return mp.get(name);
    }


    @GetMapping("/goindex")
    public String goindex()
    {
        return "Xadmin/adminindex";
    }

    @ResponseBody
    @CrossOrigin
    @GetMapping("/adminlogin")
    public Object login(@RequestParam(name="username")String username,
                           @RequestParam(name="password")String password)
    {
        System.out.println(username+" "+password);
        if("admin".equals(username) && "admin".equals(password))
            return ResultDTO.successOf();
        else{
            return ResultDTO.errorOf(ErrorCodeEnumImp.INCORRECT);
        }
    }
    /**
     * 查找所有用户
     * @return
     */
    @CrossOrigin//跨域
    @GetMapping("/admin/findAll")
    @ResponseBody
    public List<User> findAll(@RequestParam(name = "page",defaultValue = "1")Integer page,
                              @RequestParam(name = "size",defaultValue = "5")Integer size){
        List<User> users = adminService.findAll(page,size);
        return users;
    }
}
