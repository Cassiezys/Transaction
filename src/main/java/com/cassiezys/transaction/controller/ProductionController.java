package com.cassiezys.transaction.controller;

import com.cassiezys.transaction.dto.CommentDTO;
import com.cassiezys.transaction.dto.ProductionDTO;
import com.cassiezys.transaction.enums.CommentTypeEnum;
import com.cassiezys.transaction.service.CommentService;
import com.cassiezys.transaction.service.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    /**
     * 查看商品详情
     * @param proid 获取商品id
     * @param model 返回商品信息；与商品的（产地|类型|名称相似）的商品；商品的评论
     * @return product.html 展示productiondto
     */
    @GetMapping("/production/{proid}")
    public String product(@PathVariable(name = "proid")Long proid,
                          Model model){
        ProductionDTO productionDTO = productionService.findByPid(proid);
        List<ProductionDTO> relatedProdtos = productionService.findRelated(productionDTO);
        List<CommentDTO> commentdtos = commentService.findByTargetId(proid, CommentTypeEnum.QUESTION);
        productionService.incView(proid);
        model.addAttribute("commentdtos",commentdtos);
        model.addAttribute("productiondto",productionDTO);
        model.addAttribute("relatedProdtos",relatedProdtos);
        return "product";
    }

}
