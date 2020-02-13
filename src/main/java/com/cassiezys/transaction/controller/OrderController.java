package com.cassiezys.transaction.controller;

import com.cassiezys.transaction.exception.CustomizeCodeException;
import com.cassiezys.transaction.exception.ErrorCodeEnumImp;
import com.cassiezys.transaction.model.User;
import com.cassiezys.transaction.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping("/order")
    public String order(@RequestParam("proId")Long proId,
                        @RequestParam("amount")Integer amount,
                        HttpServletRequest request,
                        Model model){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            throw new CustomizeCodeException(ErrorCodeEnumImp.NO_LOGIN);
        }
        orderService.createOrder(user.getId(),proId,amount);

        model.addAttribute("section","order");
        model.addAttribute("sectionName","建立订单");
        return "product";
    }
}
