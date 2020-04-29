package com.cassiezys.transaction.controller;

import com.cassiezys.transaction.dto.NotificationDTO;
import com.cassiezys.transaction.dto.OrderDTO;
import com.cassiezys.transaction.dto.PaginationDTO;
import com.cassiezys.transaction.dto.ProductionDTO;
import com.cassiezys.transaction.exception.CustomizeCodeException;
import com.cassiezys.transaction.exception.ErrorCodeEnumImp;
import com.cassiezys.transaction.mapper.NotificationMapper;
import com.cassiezys.transaction.model.User;
import com.cassiezys.transaction.service.NotificationService;
import com.cassiezys.transaction.service.OrderService;
import com.cassiezys.transaction.service.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:与我相关的：我发布的商品，我的收藏...
 */
@Controller
public class ProfileController {
    @Autowired
    ProductionService productionService;
    @Autowired
    NotificationService notificationService;
    @Autowired
    OrderService orderService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "5")Integer size,
                          Model model){
        User user = null;
        user = (User) request.getSession().getAttribute("user");
        if(user==null){
            throw new CustomizeCodeException(ErrorCodeEnumImp.NO_LOGIN);
        }
        if("productions".equals(action)){
            //查看我发布的商品
            model.addAttribute("section","productions");
            model.addAttribute("sectionName","我发布的商品");
            PaginationDTO<ProductionDTO> paginationDTO = productionService.addPaginationByUid(user.getId(), page, size);
            model.addAttribute("paginationdto",paginationDTO);
        }else if("replies".equals(action)){
            //查找通知
            System.out.println("");
            PaginationDTO<NotificationDTO> paginationDTO = notificationService.addPaginationByUid(user.getId(), page, size);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("paginationdto",paginationDTO);
        }else if("orders".equals(action)){
            //查找全部订单
            PaginationDTO<OrderDTO> paginationDTO = orderService.addPaginationByUid(user.getId(), page, size);
            model.addAttribute("section","orders");
            model.addAttribute("sectionName","购买记录");
            model.addAttribute("paginationdto",paginationDTO);
        }else if("cart".equals(action)){
            //我的购物车
            PaginationDTO<OrderDTO> paginationDTO = orderService.addCartPaginationByUid(user.getId());
            model.addAttribute("section","cart");
            model.addAttribute("sectionName","我的购物车");
            model.addAttribute("paginationdto",paginationDTO);
        }else if("favorites".equals(action)){
            PaginationDTO<ProductionDTO> paginationDTO = productionService.addFavoritePaginationByUid(user.getId());
            model.addAttribute("section","favorites");
            model.addAttribute("sectionName","我的收藏");
            model.addAttribute("paginationdto",paginationDTO);
        }

        return "profile";
    }
}
