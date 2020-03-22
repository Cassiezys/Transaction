package com.cassiezys.transaction.controller;

import com.cassiezys.transaction.dto.CommentDTO;
import com.cassiezys.transaction.dto.OrderDTO;
import com.cassiezys.transaction.dto.ProductionDTO;
import com.cassiezys.transaction.dto.ResultDTO;
import com.cassiezys.transaction.enums.CommentTypeEnum;
import com.cassiezys.transaction.exception.CustomizeCodeException;
import com.cassiezys.transaction.exception.ErrorCodeEnumImp;
import com.cassiezys.transaction.model.User;
import com.cassiezys.transaction.service.CommentService;
import com.cassiezys.transaction.service.OperateService;
import com.cassiezys.transaction.service.OrderService;
import com.cassiezys.transaction.service.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.security.jgss.HttpCaller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:商品
 */
@Controller
public class ProductionController {

    @Autowired
    private ProductionService productionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OperateService operateService;

    /**
     * 修改收藏状态
     * @param pid 商品id
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping("/production/modifyfavor/{pid}/{status}")
    public Object modifyfavor(@PathVariable(name = "pid")Long pid,
                              @PathVariable(name = "status")int status,
            HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(ErrorCodeEnumImp.NO_LOGIN);
        }
        operateService.modifyFavor(pid,user,status);
        return ResultDTO.successOf();
    }


    /**  * 查看商品详情 / 建立订单
     * @param proid 获取商品id
     * @param model 返回商品信息；与商品的（产地|类型|名称相似）的商品；商品的评论
     * @return product.html 展示productiondto
     * @param action details  / orders
     * @param proid 商品的id
     * @param model 商品详情(地区/类型/名称相似的推荐；商品的评论) // 查看購物車
     * @return product.html
     */
    @GetMapping("/production/{action}/{proid}")
    public String product( @PathVariable("action")String action,
                           @PathVariable(name = "proid")Long proid,
                          @RequestParam(name = "amount",required = false,defaultValue = "1")Integer amount,
                          HttpServletRequest request,
                          Model model){
        User user = (User) request.getSession().getAttribute("user");
        if ("details".equals(action)){
            ProductionDTO productionDTO = productionService.findByPid(user,proid);
            List<String> pics = Arrays.asList(productionDTO.getPicUrl().split(";"));
            List<ProductionDTO> relatedProdtos = productionService.findRelated(productionDTO);
            List<CommentDTO> commentdtos = commentService.findByTargetId(user, proid, CommentTypeEnum.QUESTION);
            productionService.incView(proid);
            model.addAttribute("pics",pics);
            model.addAttribute("commentdtos",commentdtos);
            model.addAttribute("productiondto",productionDTO);
            model.addAttribute("relatedProdtos",relatedProdtos);
            model.addAttribute("section","details");
            model.addAttribute("sectionName","商品详情");
        }else if ("order".equals(action)){
            if(user == null){
                throw new CustomizeCodeException(ErrorCodeEnumImp.NO_LOGIN);
            }
            OrderDTO orderDTO = orderService.createOrder(user.getId(), proid, amount);

            model.addAttribute("orderdto",orderDTO);
            model.addAttribute("section","order");
            model.addAttribute("sectionName","建立订单");
        }
        return "product";
    }

}
