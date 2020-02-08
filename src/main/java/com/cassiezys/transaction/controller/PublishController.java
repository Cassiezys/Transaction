package com.cassiezys.transaction.controller;

import com.cassiezys.transaction.cache.CategoryCache;
import com.cassiezys.transaction.cache.CityCache;
import com.cassiezys.transaction.model.Production;
import com.cassiezys.transaction.model.User;
import com.cassiezys.transaction.service.ProductionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:商品相关
 */
@Controller
public class PublishController {

    @Autowired
    private ProductionService productionService;

    /*打开发布*/
    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("cities", CityCache.get());
        model.addAttribute("categories", CategoryCache.get());
        return "publish";
    }

    /*新建/更新发布*/
    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "city", required = false) String city,
                            @RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "description", required = false) String description,
                            @RequestParam(value = "pic", required = false) MultipartFile pic,
                            @RequestParam(value = "price", required = false) Float price,
                            @RequestParam(value = "origprice", required = false) Float origprice,
                            @RequestParam(value = "tele", required = false) Long tele,
                            @RequestParam(value = "tencent", required = false) Long tencent,
                            @RequestParam(value = "wechat", required = false) String wechat,
                            @RequestParam(value = "category", required = false) String category,
                            @RequestParam(value = "address", required = false) String address,
                            @RequestParam(value = "payway", required = false) String payway,
                            @RequestParam(value = "id", required = false) Long id,
                            @RequestParam Map param,
                            HttpServletRequest request,
                            Model model
    ) {
        model.addAttribute("cities", CityCache.get());
        model.addAttribute("categories", CategoryCache.get());
        model.addAttribute("city", city);
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("category", category);
        model.addAttribute("price", price);
        model.addAttribute("origprice", origprice);
        model.addAttribute("tele", tele);
        model.addAttribute("tencent", tencent);
        model.addAttribute("wechat", wechat);
        model.addAttribute("address", address);
        model.addAttribute("payway", payway);
        model.addAttribute("id",id);
        if (pic.isEmpty()) {
            model.addAttribute("error", "图片不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(title)) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(city)) {
            model.addAttribute("error", "城市不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(description)) {
            model.addAttribute("error", "描述不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(category)) {
            model.addAttribute("error", "类别不能为空");
            return "publish";
        }
        if (price == null) {
            model.addAttribute("error", "价格不能为空");
            return "publish";
        }
        if (origprice == null) {
            model.addAttribute("error", "原价不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(payway)) {
            model.addAttribute("error", "请选择支付方式");
            return "publish";
        }
        if (payway.equals("online")) {
            if(StringUtils.isBlank(wechat)&&tele==null&&tencent==null){
                model.addAttribute("error", "线上交易：QQ,微信，电话至少留下一个联系方式不能为空");
                return "publish";
            }
        }
        if (payway.equals("offline")) {
            if(StringUtils.isBlank(address)){
                model.addAttribute("error", "线下交易，留下期望交易的地址");
                return "publish";
            }
        }
        User user = null;
        user = (User) request.getSession().getAttribute("user");
        if (user == null || "".equals(user.getName())) {
            model.addAttribute("error", "请先登录");
            return "publish";
        }
        String fileName = pic.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //fileName= "zhenbao-"+UUID.randomUUID()+suffixName;
        fileName = user.getAccountId()+"/zhenbao-" + fileName;
        productionService.ProductionService(user.getAccountId());
        String imgUrl = "upload-dir/" + fileName;//重命名的名字
        model.addAttribute("pic", imgUrl);

        Production production = new Production();
        production.setCity(city);
        production.setPicUrl(imgUrl);
        production.setTitle(title);
        production.setDescription(description);
        production.setPrice(price);
        production.setOrigprice(origprice);
        production.setTele(tele);
        production.setTencent(tencent);
        production.setCreator(user.getId());
        production.setWechat(wechat);
        production.setCategory(category);
        production.setAddress(address);
        production.setPayway(payway);
        production.setId(id);
        try {
            productionService.createOrUpdate(pic.getInputStream(), production);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
