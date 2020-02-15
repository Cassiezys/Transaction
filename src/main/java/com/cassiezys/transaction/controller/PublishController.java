package com.cassiezys.transaction.controller;

import com.cassiezys.transaction.cache.CategoryCache;
import com.cassiezys.transaction.cache.CityCache;
import com.cassiezys.transaction.dto.ProductionDTO;
import com.cassiezys.transaction.model.Production;
import com.cassiezys.transaction.model.User;
import com.cassiezys.transaction.service.ProductionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    /**
     * 打开修改页面
     *
     * @param proId 商品pid
     * @param model 返回productdto
     * @return publish.html
     */
    @GetMapping("/publish/{proId}")
    public String doModify(@PathVariable(name = "proId") Long proId,
                           Model model) {
        ProductionDTO productionDTO = productionService.findByPid(proId);
        model.addAttribute("cities", CityCache.get());
        model.addAttribute("categories", CategoryCache.get());
        model.addAttribute("city", productionDTO.getCity());
        model.addAttribute("title", productionDTO.getTitle());
        model.addAttribute("description", productionDTO.getDescription());
        model.addAttribute("category", productionDTO.getCategory());
        model.addAttribute("price", productionDTO.getPrice());
        model.addAttribute("origprice", productionDTO.getOrigprice());
        model.addAttribute("amount",productionDTO.getAmount());
        model.addAttribute("tele", productionDTO.getTele());
        model.addAttribute("tencent", productionDTO.getTencent());
        model.addAttribute("wechat", productionDTO.getWechat());
        model.addAttribute("address", productionDTO.getAddress());
        model.addAttribute("payway", productionDTO.getPayway());
        model.addAttribute("pictext", productionDTO.getPicUrl());
        String imgUrl = productionDTO.getPicUrl();
        List<String> pics = Arrays.asList(imgUrl.split(";"));
        for (String pic : pics) {
            pic = "/" + pic;
            System.out.println(pic);
        }

        model.addAttribute("pics", pics);
        model.addAttribute("pid", productionDTO.getId());
        return "publish";
    }

    /*新建/更新发布*/
    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "city", required = false) String city,
                            @RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "description", required = false) String description,
                            @RequestParam(value = "amount",required = false,defaultValue = "1")Integer amount,
                            @RequestParam(value = "pics", required = false) MultipartFile[] pics,
                            @RequestParam(value = "price", required = false) Float price,
                            @RequestParam(value = "origprice", required = false) Float origprice,
                            @RequestParam(value = "tele", required = false) Long tele,
                            @RequestParam(value = "tencent", required = false) Long tencent,
                            @RequestParam(value = "wechat", required = false) String wechat,
                            @RequestParam(value = "category", required = false) String category,
                            @RequestParam(value = "address", required = false) String address,
                            @RequestParam(value = "payway", required = false) String payway,
                            @RequestParam(value = "pid", required = false) Long pid,
                            @RequestParam(value = "pictext", required = false) String pictext,
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
        model.addAttribute("amount", amount);
        model.addAttribute("tele", tele);
        model.addAttribute("tencent", tencent);
        model.addAttribute("wechat", wechat);
        model.addAttribute("address", address);
        model.addAttribute("payway", payway);
        model.addAttribute("pid", pid);
        if (pics.length == 0 && StringUtils.isBlank(pictext)) {
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
            if (StringUtils.isBlank(wechat) && tele == null && tencent == null) {
                model.addAttribute("error", "线上交易：QQ,微信，电话至少留下一个联系方式不能为空");
                return "publish";
            }
        }
        if (payway.equals("offline")) {
            if (StringUtils.isBlank(address)) {
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
        String imgUrl = pictext;
        List<String> oldPics = Arrays.asList(pictext.split(";"));
        if (!pics[0].isEmpty()) {
            imgUrl = "";
            oldPics = new ArrayList<>();
            for (MultipartFile pic : pics) {
                String thisPic = pic.getOriginalFilename();
                thisPic = user.getAccountId() + "/zhenbao-" + thisPic;
                productionService.ProductionService(user.getAccountId());
                thisPic = "upload-dir/" + thisPic;//重命名的名字
                imgUrl += thisPic + ";";
                oldPics.add(thisPic);
            }
            model.addAttribute("pics", oldPics);
        }
        model.addAttribute("pictext", imgUrl);
        Production production = new Production();
        production.setCity(city);
        production.setPicUrl(imgUrl);
        production.setTitle(title);
        production.setDescription(description);
        production.setPrice(price);
        production.setOrigprice(origprice);
        production.setAmount(amount);
        production.setTele(tele);
        production.setTencent(tencent);
        production.setCreator(user.getId());
        production.setWechat(wechat);
        production.setCategory(category);
        production.setAddress(address);
        production.setPayway(payway);
        production.setId(pid);
        if (!pics[0].isEmpty()) {
            //pics不是空才重新上传图片
            try {
                int i = 0;
                for (MultipartFile pic : pics) {
                    productionService.uploadPic(pic.getInputStream(), oldPics.get(i));
                    ++i;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productionService.createOrUpdate(production);
        return "redirect:/";
    }
}
