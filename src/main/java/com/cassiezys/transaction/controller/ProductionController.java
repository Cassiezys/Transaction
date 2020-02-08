package com.cassiezys.transaction.controller;

import com.cassiezys.transaction.dto.ProductionDTO;
import com.cassiezys.transaction.service.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:商品
 */
@Controller
public class ProductionController {

    @Autowired
    private ProductionService productionService;

    @GetMapping("/production/{proid}")
    public String product(@PathVariable(name = "proid")Long proid,
                          Model model){
        ProductionDTO productionDTO = productionService.findByPid(proid);
        model.addAttribute("productiondto",productionDTO);
        return "product";
    }

}
