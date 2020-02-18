package com.cassiezys.transaction.advice;

import com.cassiezys.transaction.exception.CustomizeCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:2.定义带有ControllerAdvice注解的类来定制JSON来返回到error页面
 * @ResponseBody：自动序列化成json
 * 需要返回ModelAndView（controller默认写return "index";）所以这里返回ModelAndView自动渲染到的那个error.html页面
 * 通过Model将message放到页面
 * 统一异常处理
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request, Throwable ex, Model model) {
        if(ex instanceof CustomizeCodeException){
            model.addAttribute("message",ex.getMessage());
        }else{
            model.addAttribute("message","发现异于常识的问题！");
        }
        return new ModelAndView("error");
    }

}
