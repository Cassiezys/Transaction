package com.cassiezys.transaction.service;

import com.cassiezys.transaction.dto.OrderDTO;
import com.cassiezys.transaction.dto.PaginationDTO;
import com.cassiezys.transaction.enums.NoticifacionStatusEnum;
import com.cassiezys.transaction.enums.NotificationTypeEnum;
import com.cassiezys.transaction.enums.OrderStatusEnum;
import com.cassiezys.transaction.exception.CustomizeCodeException;
import com.cassiezys.transaction.exception.ErrorCodeEnumImp;
import com.cassiezys.transaction.mapper.*;
import com.cassiezys.transaction.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:
 */
@Service
public class OrderService {
    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProductionMapper productionMapper;
    @Autowired
    NotificationMapper notificationMapper;

    /**
     * 当前page页的订单(全部订单）
     * @param id uid  notifierId  建立通知者的id
     * @param page 页码
     * @param size 数量
     * @return PaginationDTO<OrderDTO>
     */
    public PaginationDTO<OrderDTO> addPaginationByUid(Long id, Integer page, Integer size) {
        PaginationDTO<OrderDTO> paginationDTO = new PaginationDTO<>();

        OrdersExample ordersExample = new OrdersExample();
        ordersExample.createCriteria()
                .andCreatorEqualTo(id);
        int totalOrders = (int) ordersMapper.countByExample(ordersExample);

        if(totalOrders % size == 0){
            paginationDTO.setTotalPage(totalOrders / size);
        }else{
            paginationDTO.setTotalPage(totalOrders / size +1);
        }
        if(page<1){
            page =1;
        }else if(page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        paginationDTO.setPage(page);
        paginationDTO.setPagination(totalOrders,page,size);
        if(totalOrders == 0){
            List<OrderDTO> orderDTOS = new ArrayList<>();
            paginationDTO.setDataDtos(orderDTOS);
            return paginationDTO;
        }
        int offset = size * (page -1);
        List<Orders> orders = ordersMapper.selectByExampleWithRowbounds(ordersExample, new RowBounds(offset, size));

        //获取 卖家set 转为 list 在转为以id为key的map
        Set<Long> sellerIdSet = orders.stream().map(order -> order.getReceiver()).collect(Collectors.toSet());
        List<Long> selletIdList = new ArrayList<>();
        selletIdList.addAll(sellerIdSet);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(selletIdList);
        List<User> sellers = userMapper.selectByExample(userExample);
        Map<Long, User> sellerMap = sellers.stream().collect(Collectors.toMap(seller -> seller.getId(), seller -> seller));

        //获取 商品set 创建以id为key的map
        Set<Long> proIdSet = orders.stream().map(order -> order.getOuterid()).collect(Collectors.toSet());
        List<Long> proIdList = new ArrayList<>();
        proIdList.addAll(proIdSet);
        ProductionExample productionExample = new ProductionExample();
        productionExample.createCriteria()
                .andIdIn(proIdList);
        List<Production> productions = productionMapper.selectByExample(productionExample);
        Map<Long, Production> productionMap = productions.stream().collect(Collectors.toMap(production -> production.getId(), production -> {
            if (production.getPicUrl().contains(";"))
            production.setPicUrl(production.getPicUrl().substring(0,production.getPicUrl().indexOf(";")));
            return production;
        }));

        List<OrderDTO> orderDTOS = orders.stream().map(order -> {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(order, orderDTO);
            orderDTO.setProduction(productionMap.get(order.getOuterid()));
            orderDTO.setSeller(sellerMap.get(order.getReceiver()));
            orderDTO.setStatusName(OrderStatusEnum.nameOfStatus(orderDTO.getStatus()));
            return orderDTO;
        }).collect(Collectors.toList());
        paginationDTO.setDataDtos(orderDTOS);

        return  paginationDTO;
    }


    /**
     * 我的购物车
     * @param id uid 创建订单creator
     * @return PaginationDTO<OrderDTO>
     */
    public PaginationDTO<OrderDTO> addCartPaginationByUid(Long id) {
        PaginationDTO<OrderDTO> paginationDTO = new PaginationDTO<>();
        paginationDTO.setPage(0);
        OrdersExample ordersExample = new OrdersExample();
        ordersExample.createCriteria()
                .andCreatorEqualTo(id)
                .andStatusEqualTo(OrderStatusEnum.UNPAY.getStatus());
        int totalOrders = (int) ordersMapper.countByExample(ordersExample);
        List<Orders> orders = ordersMapper.selectByExample(ordersExample);

        //获取 卖家set 转为 list 在转为以id为key的map
        Set<Long> sellerIdSet = orders.stream().map(order -> order.getReceiver()).collect(Collectors.toSet());
        List<Long> selletIdList = new ArrayList<>();
        selletIdList.addAll(sellerIdSet);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(selletIdList);
        List<User> sellers = userMapper.selectByExample(userExample);
        Map<Long, User> sellerMap = sellers.stream().collect(Collectors.toMap(seller -> seller.getId(), seller -> seller));

        //获取 商品set 创建以id为key的map
        Set<Long> proIdSet = orders.stream().map(order -> order.getOuterid()).collect(Collectors.toSet());
        List<Long> proIdList = new ArrayList<>();
        proIdList.addAll(proIdSet);
        ProductionExample productionExample = new ProductionExample();
        productionExample.createCriteria()
                .andIdIn(proIdList);
        List<Production> productions = productionMapper.selectByExample(productionExample);
        Map<Long, Production> productionMap = productions.stream().collect(Collectors.toMap(production -> production.getId(), production -> {
            if (production.getPicUrl().contains(";"))
                production.setPicUrl(production.getPicUrl().substring(0,production.getPicUrl().indexOf(";")));
            return production;
        }));

        List<OrderDTO> orderDTOS = orders.stream().map(order -> {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(order, orderDTO);
            orderDTO.setProduction(productionMap.get(order.getOuterid()));
            orderDTO.setSeller(sellerMap.get(order.getReceiver()));
            orderDTO.setStatusName(OrderStatusEnum.nameOfStatus(orderDTO.getStatus()));
            return orderDTO;
        }).collect(Collectors.toList());
        paginationDTO.setDataDtos(orderDTOS);

        return  paginationDTO;
    }

    /**
     * 创建新订单
     * @param uid 买家id
     * @param proId 商品id
     * @param amount 购买的数量
     * @return    OrderDTO 订单：包含卖家和商品
     */
    public OrderDTO createOrder(Long uid, Long proId, Integer amount) {
        Production thisProdt = productionMapper.selectByPrimaryKey(proId);
        if(thisProdt == null){
            throw new CustomizeCodeException(ErrorCodeEnumImp.PRODUCTION_NOT_FOUND);
        }else{
            if (thisProdt.getPicUrl().contains(";"))
            thisProdt.setPicUrl(thisProdt.getPicUrl().substring(0,thisProdt.getPicUrl().indexOf(";")));
        }
        User seller = userMapper.selectByPrimaryKey(thisProdt.getCreator());
        if(seller == null){
            throw new CustomizeCodeException(ErrorCodeEnumImp.PRODUCTION_CREATOR_NOT_FOUND);
        }
        Orders order = new Orders();
        order.setCreator(uid);
        order.setAddress(thisProdt.getAddress());
        order.setAmount(amount);
        order.setGmtCreate(System.currentTimeMillis());
        order.setGmtModified(order.getGmtCreate());
        order.setOuterid(proId);
        order.setOuterTitle(thisProdt.getTitle());
        order.setReceiver(thisProdt.getCreator());
        order.setStatus(OrderStatusEnum.UNPAY.getStatus());
        order.setTele(thisProdt.getTele());
        ordersMapper.insert(order);

        //新增通知
        Notification notification = new Notification();
        notification.setNotifier(uid);
        notification.setStatus(NoticifacionStatusEnum.UNREAD.getStatus());
        notification.setType(NotificationTypeEnum.PRODUCT_SELL.getType());
        notification.setReceiver(thisProdt.getCreator());
        notification.setOuterTitle(thisProdt.getTitle());
        notification.setOuterid(proId);
        notification.setGmtCreate(System.currentTimeMillis());
        notificationMapper.insert(notification);

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order,orderDTO);
        orderDTO.setProduction(thisProdt);
        orderDTO.setSeller(seller);
        orderDTO.setStatusName(OrderStatusEnum.nameOfStatus(orderDTO.getStatus()));

        return orderDTO;
    }

    public void delOrder(Long uid, Long oid) {
        Orders order = ordersMapper.selectByPrimaryKey(oid);
        if (order == null){
            throw new CustomizeCodeException(ErrorCodeEnumImp.ORDER_NOT_FOUND);
        }
        ordersMapper.deleteByPrimaryKey(oid);
    }
}
