package com.cassiezys.transaction.controller;

import com.cassiezys.transaction.dto.PaginationDTO;
import com.cassiezys.transaction.exception.CustomizeCodeException;
import com.cassiezys.transaction.exception.ErrorCodeEnumImp;
import com.cassiezys.transaction.model.User;
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
            model.addAttribute("section","productions");
            model.addAttribute("sectionName","我发布的商品");
            PaginationDTO paginationDTO = productionService.addPaginationByUid(user.getId(), page, size);
            model.addAttribute("paginationdto",paginationDTO);
        }

        return "profile";
    }
}
