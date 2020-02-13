package com.cassiezys.transaction.controller;

import com.cassiezys.transaction.dto.ResultDTO;
import com.cassiezys.transaction.exception.CustomizeCodeException;
import com.cassiezys.transaction.exception.ErrorCodeEnumImp;
import com.cassiezys.transaction.model.User;
import com.cassiezys.transaction.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:订单
 */
@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 删除订单
     * @param id 订单的id
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping("/order/{id}")
    public Object delorder(@PathVariable("id")Long id,
                           HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            throw new CustomizeCodeException(ErrorCodeEnumImp.NO_LOGIN);
        }
        orderService.delOrder(user.getId(),id);
        return ResultDTO.successOf();
    }
}
