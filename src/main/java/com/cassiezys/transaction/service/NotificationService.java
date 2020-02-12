package com.cassiezys.transaction.service;

import com.cassiezys.transaction.dto.NotificationDTO;
import com.cassiezys.transaction.dto.PaginationDTO;
import com.cassiezys.transaction.enums.NoticifacionStatusEnum;
import com.cassiezys.transaction.enums.NotificationTypeEnum;
import com.cassiezys.transaction.exception.CustomizeCodeException;
import com.cassiezys.transaction.exception.ErrorCodeEnumImp;
import com.cassiezys.transaction.mapper.NotificationMapper;
import com.cassiezys.transaction.model.Notification;
import com.cassiezys.transaction.model.NotificationExample;
import com.cassiezys.transaction.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:
 */
@Service
public class NotificationService {
    @Autowired
    NotificationMapper notificationMapper;

    /**
     * 当前page页的通知
     * @param id uid  notifierId  建立通知者的id
     * @param page 页码
     * @param size 数量
     * @return PaginationDTO<NotificationDTO>
     */
    public PaginationDTO<NotificationDTO> addPaginationByUid(Long id, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(id);
        int totalNoti = (int) notificationMapper.countByExample(notificationExample);
        if(totalNoti% size ==0){
            paginationDTO.setTotalPage(totalNoti / size);
        }else {
            paginationDTO.setTotalPage(totalNoti / size+1);
        }
        if(page<1){
            page =1;
        }else if(page>paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        paginationDTO.setPage(page);
        paginationDTO.setPagination(totalNoti,page,size);

        /*查找当前页面的通知*/
        Integer offset = size *(page-1);

        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(offset, size));
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setDataDtos(notificationDTOS);
        System.out.println();
        return paginationDTO;
    }

    /**
     * 将通知改为已读
     * @param user 当前用户
     * @param notiId 通知的id
     * @return 该通知的Dto
     */
    public NotificationDTO doRead(User user, Long notiId) {
        Notification thisNoti = notificationMapper.selectByPrimaryKey(notiId);
        if (thisNoti == null){
            throw new CustomizeCodeException(ErrorCodeEnumImp.NOTIFICATION_NOT_FOUND);
        }
        if(!thisNoti.getReceiver().equals( user.getId())){
            throw new CustomizeCodeException(ErrorCodeEnumImp.NOTIFICATION_INFOR_ERROR);
        }
        thisNoti.setStatus(NoticifacionStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(thisNoti);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(thisNoti,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(thisNoti.getType()));
        return notificationDTO;
    }

    /**
     * 找到有多少未读的消息
     * @param user 当前用户
     * @return 未读消息的数目
     */
    public Long findUnreadCount(User user) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(user.getId())
                .andStatusEqualTo(NoticifacionStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }
}
