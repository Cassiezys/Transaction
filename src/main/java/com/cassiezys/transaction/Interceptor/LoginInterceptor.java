package com.cassiezys.transaction.Interceptor;

import com.cassiezys.transaction.mapper.NotificationMapper;
import com.cassiezys.transaction.mapper.UserMapper;
import com.cassiezys.transaction.model.User;
import com.cassiezys.transaction.model.UserExample;
import com.cassiezys.transaction.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:登录拦截：计算有多少未读通知
 */
@Service
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //处理前
        Cookie[] cookies = request.getCookies();
        if(cookies !=null && cookies.length!=0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token=cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    if(users.size()!=0){
                        //这个token已经存在
                        request.getSession().setAttribute("user",users.get(0));
                        Long unreadCount = notificationService.findUnreadCount(users.get(0));
                        request.getSession().setAttribute("unreadCount",unreadCount);
                    }else{
                        System.out.println("无法登陆");
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //处理后
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //请求后
    }
}
