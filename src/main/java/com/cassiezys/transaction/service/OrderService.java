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
import org.springframework.transaction.annotation.Transactional;

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
        if (orders.size() ==0 ){
            throw new CustomizeCodeException(ErrorCodeEnumImp.ORDERS_NOT_EXIST);
        }

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
        User thisUser = userMapper.selectByPrimaryKey(uid);
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
        OrdersExample ordersExample = new OrdersExample();
        ordersExample.createCriteria()
                .andCreatorEqualTo(uid)
                .andOuteridEqualTo(proId)
                .andStatusEqualTo(0);
        List<Orders> preOrders = ordersMapper.selectByExample(ordersExample);
        Orders order = new Orders();
        if(preOrders.size()!=0){
            order = preOrders.get(0);
            amount = order.getAmount()+amount;
            order.setAmount(amount);
            ordersMapper.updateByPrimaryKey(order);
        }else{
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
        }

        //新增通知
        Notification notification = addNotification(thisUser, thisProdt, NotificationTypeEnum.PRODUCT_BOOK.getType());
        notificationMapper.insert(notification);

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order,orderDTO);
        orderDTO.setProduction(thisProdt);
        orderDTO.setSeller(seller);
        orderDTO.setStatusName(OrderStatusEnum.nameOfStatus(orderDTO.getStatus()));

        return orderDTO;
    }

    /**
     * 删除订单
     * @param oid
     */
    public void delOrder(Long oid) {
        Orders order = ordersMapper.selectByPrimaryKey(oid);
        if (order == null){
            throw new CustomizeCodeException(ErrorCodeEnumImp.ORDER_NOT_FOUND);
        }
        ordersMapper.deleteByPrimaryKey(oid);
    }

    /**
     * 更新要支付的订单数量
     * @param id 订单id
     * @param count 更新的数量
     * @return 订单
     */
    public Orders updateOrderById(Long id, Integer count) {
        Orders thisOrder = ordersMapper.selectByPrimaryKey(id);
        if (thisOrder == null){
            throw new CustomizeCodeException(ErrorCodeEnumImp.ORDER_NOT_FOUND);
        }
        if (count != thisOrder.getAmount()){
            thisOrder.setAmount(count);
            ordersMapper.updateByPrimaryKey(thisOrder);
        }
        return thisOrder;
    }

    /**
     * 支付成功
     * @param oid
     * 更新订单付款状态 减少库存 通知卖家 通知买家
     */
    @Transactional
    public void payOK(Long oid) {
        Orders thisOrder = ordersMapper.selectByPrimaryKey(oid);
        User buyer = userMapper.selectByPrimaryKey(thisOrder.getCreator());
        //更新订单状态
        thisOrder.setStatus(OrderStatusEnum.PAID.getStatus());
        ordersMapper.updateByPrimaryKey(thisOrder);
        //减少库存
        Production thisProdt = productionMapper.selectByPrimaryKey(thisOrder.getOuterid());
        int kucun = thisProdt.getAmount()-thisOrder.getAmount();
        if(kucun == 0){
            notificationMapper.insert(addNotification(buyer,thisProdt,NotificationTypeEnum.PRODUCT_EMPTY.getType()));
        }
        thisProdt.setAmount(thisProdt.getAmount()-thisOrder.getAmount());
        productionMapper.updateByPrimaryKey(thisProdt);

        //通知卖家
        Notification notification = addNotification(buyer, thisProdt, NotificationTypeEnum.PRODUCT_SELL.getType());
        notificationMapper.insert(notification);

        //通知买家
        notification = addNotification(buyer, thisProdt, NotificationTypeEnum.PRODUCT_SELL.getType());
        notificationMapper.insert(notification);
    }

    /** A B C
     * @param notifier A B
     * @param thisProdt C
     * @param notiType
     */
    private Notification addNotification(User notifier, Production thisProdt, int notiType) {
        Notification notification = new Notification();
        notification.setNotifier(notifier.getId());
        notification.setNotifierName(notifier.getName());
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setOuterid(thisProdt.getId());
        notification.setOuterTitle(thisProdt.getTitle());
        notification.setReceiver(thisProdt.getCreator());
        notification.setType(notiType);
        notification.setStatus(NoticifacionStatusEnum.UNREAD.getStatus());
        return notification;
    }
}
