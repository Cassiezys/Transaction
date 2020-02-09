package com.cassiezys.transaction.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:路径错误异常的处理
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomizeErrorController implements ErrorController {
    /**
     * Returns the path of the error page.
     * @return the error path
     */
    @Override
    public String getErrorPath() {
        return "error";
    }

    /**
     * 看到RequestMapping即为请求
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request,
                                  Model model) {
        HttpStatus status = getStatus(request);
        if(status.is4xxClientError()){
            //客户端的响应
            model.addAttribute("message","hey，当我站在高楼~~你的请求有漏~~");
        }else if(status.is5xxServerError()){
            //是服务器的问题
            model.addAttribute("message","Yohoo~~ 你有当检测员的潜质噢");
        }
        return new ModelAndView("error");
    }
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        }
        catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
