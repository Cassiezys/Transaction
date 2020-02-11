package com.cassiezys.transaction.service;

import com.cassiezys.transaction.dto.PaginationDTO;
import com.cassiezys.transaction.dto.ProductQueryDTO;
import com.cassiezys.transaction.dto.ProductionDTO;
import com.cassiezys.transaction.exception.CustomizeCodeException;
import com.cassiezys.transaction.exception.ErrorCodeEnumImp;
import com.cassiezys.transaction.mapper.ProductionExtMapper;
import com.cassiezys.transaction.mapper.ProductionMapper;
import com.cassiezys.transaction.mapper.UserMapper;
import com.cassiezys.transaction.model.Production;
import com.cassiezys.transaction.model.ProductionExample;
import com.cassiezys.transaction.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:商品相关
 */
@Service
public class ProductionService {
    @Autowired
    private ProductionMapper productionMapper;
    @Autowired
    private ProductionExtMapper productionExtMapper;
    @Autowired
    private UserMapper userMapper;

    private String filePath = "src/main/resources/static/upload-dir";
    private String realPath = "src/main/resources/static/upload-dir";

    private Path rootLocation;

    /**
     * 创建上传图片的文件夹
     * @param account_Id 用户Id 每个用户拥有单独的文件夹
     */
    public void ProductionService(String account_Id) {
        if(StringUtils.isBlank(account_Id)){
            throw new CustomizeCodeException(ErrorCodeEnumImp.NO_LOGIN);
        }
        if(realPath.contains(account_Id)){
            realPath=realPath;
        }else{
            realPath=filePath+"/"+account_Id;
        }
        this.rootLocation = Paths.get(realPath);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new CustomizeCodeException(ErrorCodeEnumImp.FAIL_INITSTORAGE);
        }
    }

    /**
     * 添加商品
     *
     * @param production  商品model
     */
    public void createOrUpdate(Production production) {
        if (production.getId() == null) {
            //create an item
            production.setGmtCreate(System.currentTimeMillis());
            production.setGmtModified(production.getGmtCreate());
            production.setViewCount(0);
            production.setLikeCount(0);
            production.setCommentCount(0);
            productionMapper.insert(production);
        } else {
            //modify an item
            Production updatePro = new Production();
            updatePro.setCity(production.getCity());
            updatePro.setPicUrl(production.getPicUrl());
            updatePro.setTitle(production.getTitle());
            updatePro.setDescription(production.getDescription());
            updatePro.setPrice(production.getPrice());
            updatePro.setOrigprice(production.getOrigprice());
            updatePro.setTele(production.getTele());
            updatePro.setTencent(production.getTencent());
            updatePro.setCreator(production.getCreator());
            updatePro.setWechat(production.getWechat());
            updatePro.setCategory(production.getCategory());
            updatePro.setAddress(production.getAddress());
            updatePro.setPayway(production.getPayway());
            updatePro.setGmtCreate(production.getGmtCreate());
            updatePro.setGmtModified(System.currentTimeMillis());
            updatePro.setViewCount(production.getViewCount());
            updatePro.setLikeCount(production.getLikeCount());
            updatePro.setCommentCount(production.getCommentCount());
            ProductionExample productionExample = new ProductionExample();
            productionExample.createCriteria()
                    .andIdEqualTo(production.getId());
            productionMapper.updateByExampleSelective(updatePro, productionExample);

        }
    }

    /**
     * 单独图片上传
     * @param inputStream
     * @param picUrl 圖片路徑
     */
    public void uploadPic(InputStream inputStream, String picUrl) {
        String fileName = picUrl.substring(picUrl.lastIndexOf("/") + 1);
        try {
            Files.copy(inputStream, this.rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new CustomizeCodeException(ErrorCodeEnumImp.FAIL_UPLOAD);
        }
    }


    /**
     * 所有商品的page页
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO addPagination(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        ProductQueryDTO productQueryDTO = new ProductQueryDTO();

        int totalPro = productionExtMapper.countByQuery(productQueryDTO);
        if (totalPro % size ==0){
            paginationDTO.setTotalPage(totalPro / size);
        }else{
            paginationDTO.setTotalPage(totalPro /size +1);
        }
        if (page <1){
            page =1;
        }else if(page>paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        paginationDTO.setPage(page);
        paginationDTO.setPagination(totalPro,page,size);
        /*当前页面显示的商品*/
        Integer offset = size *(page -1);
        productQueryDTO.setPage(offset);
        productQueryDTO.setSize(size);

        List<ProductionDTO> productionDTOS = new ArrayList<>();
        List<Production> productions = productionExtMapper.selectByQuery(productQueryDTO);
        for (Production production : productions) {
            User proUser = userMapper.selectByPrimaryKey(production.getCreator());
            ProductionDTO productionDTO = new ProductionDTO();
            BeanUtils.copyProperties(production, productionDTO);
            if(productionDTO.getPicUrl().contains(";")){
                productionDTO.setPicUrl(productionDTO.getPicUrl().substring(0,productionDTO.getPicUrl().indexOf(";")));
            }
            productionDTO.setUser(proUser);
            productionDTOS.add(productionDTO);
        }
        paginationDTO.setProductionDTOS(productionDTOS);

        return paginationDTO;
    }


    /**
     * 获取商品的全部信息
     * @param proid 商品id
     * @return 商品以及其卖家信息
     */
    public ProductionDTO findByPid(Long proid) {
        ProductionDTO productionDTO = new ProductionDTO();
        Production thisPrdt = productionMapper.selectByPrimaryKey(proid);
        if(thisPrdt == null){
            throw new CustomizeCodeException(ErrorCodeEnumImp.PRODUCTION_NOT_FOUND);
        }
        User thisUser = userMapper.selectByPrimaryKey(thisPrdt.getCreator());
        BeanUtils.copyProperties(thisPrdt,productionDTO);
        productionDTO.setUser(thisUser);
        return productionDTO;
    }

    /**
     * 当前用户的所有商品的page页
     * @param id 用户id
     * @param page
     * @param size
     * @return 该页面显示的内容
     */
    public PaginationDTO addPaginationByUid(Long id, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        ProductQueryDTO productQueryDTO = new ProductQueryDTO();

        ProductionExample productionExample1 = new ProductionExample();
        productionExample1.createCriteria()
                .andCreatorEqualTo(id);
        int totalPro = (int) productionMapper.countByExample(productionExample1);
        List<ProductionDTO> productionDTOS = new ArrayList<>();
        if (totalPro % size ==0){
            paginationDTO.setTotalPage(totalPro / size);
        }else{
            paginationDTO.setTotalPage(totalPro /size +1);
        }
        if (page <1){
            page =1;
        }else if(page>paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        paginationDTO.setPage(page);
        paginationDTO.setPagination(totalPro,page,size);
        /*当前页面显示的商品*/
        Integer offset = size *(page -1);
        productQueryDTO.setPage(offset);
        productQueryDTO.setSize(size);

        ProductionExample productionExample2 = new ProductionExample();
        productionExample2.createCriteria()
                .andCreatorEqualTo(id);
        List<Production> productions = productionMapper.selectByExampleWithBLOBsWithRowbounds(productionExample2, new RowBounds(offset, size));
        for (Production production : productions) {
            User proUser = userMapper.selectByPrimaryKey(production.getCreator());
            ProductionDTO productionDTO = new ProductionDTO();
            BeanUtils.copyProperties(production, productionDTO);
            if(productionDTO.getPicUrl().contains(";")){
                productionDTO.setPicUrl(productionDTO.getPicUrl().substring(0,productionDTO.getPicUrl().indexOf(";")));
            }
            productionDTO.setUser(proUser);
            productionDTOS.add(productionDTO);
        }
        paginationDTO.setProductionDTOS(productionDTOS);

        return paginationDTO;
    }

    /**
     * 返回与该商品相关的商品集合
     * @param productionDTO （同Category）
     * @return List<ProductionDTO></ProductionDTO>
     */
    public List<ProductionDTO> findRelated(ProductionDTO productionDTO) {

        ProductionExample productionExample = new ProductionExample();
        productionExample.or().andCategoryEqualTo(productionDTO.getCategory());
        productionExample.or().andCityEqualTo(productionDTO.getCity());
        productionExample.or().andTitleLike("%"+productionDTO.getTitle()+"%");
        List<Production> productions = productionMapper.selectByExample(productionExample);

        List<ProductionDTO> productionDTOS = productions.stream().map(pros -> {
            ProductionDTO thisProDto = new ProductionDTO();
            BeanUtils.copyProperties(pros, thisProDto);
            return thisProDto;
        }).collect(Collectors.toList());

        return  productionDTOS;
    }

    /**
     * 自增商品阅读量
     * @param proid
     */
    public void incView(Long proid) {
        Production production = new Production();
        production.setId(proid);
        production.setViewCount(1);
        productionExtMapper.incViewCount(production);
    }
}




















