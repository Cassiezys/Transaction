package com.cassiezys.transaction.controller;

import com.cassiezys.transaction.dto.NotificationDTO;
import com.cassiezys.transaction.enums.NotificationTypeEnum;
import com.cassiezys.transaction.exception.CustomizeCodeException;
import com.cassiezys.transaction.exception.ErrorCodeEnumImp;
import com.cassiezys.transaction.model.User;
import com.cassiezys.transaction.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:
 */
@Controller
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @RequestMapping("/notification/{id}")
    public String doread(@PathVariable("id") Long notiId,
                         HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomizeCodeException(ErrorCodeEnumImp.NO_LOGIN);
        }
        NotificationDTO notificationDTO = notificationService.doRead(user, notiId);

        if (notificationDTO.getType() == NotificationTypeEnum.REPLY_PRODUCTION.getType()
                || notificationDTO.getType() == NotificationTypeEnum.REPLY_COMMENT.getType()
                || notificationDTO.getType() == NotificationTypeEnum.COMMENT_FAVOR.getType()
                || notificationDTO.getType() == NotificationTypeEnum.PRODUCT_FAVOR.getType()) {
            return "redirect:/production/details/" + notificationDTO.getOuterid();
        } else
            return "redirect:/";
    }
}
