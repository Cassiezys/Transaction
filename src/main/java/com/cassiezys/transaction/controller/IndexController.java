package com.cassiezys.transaction.controller;

import com.cassiezys.transaction.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:首页
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(
            Model model){
        return "index";
    }

    @GetMapping("/register")
    public String register(){
        return "login";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request){
        User user = new User();
        user.setName("znn");
        request.getSession().setAttribute("user",user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

}
