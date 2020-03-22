package com.cassiezys.transaction.controller;

import com.cassiezys.transaction.dto.PaginationDTO;
import com.cassiezys.transaction.dto.ProductionDTO;
import com.cassiezys.transaction.model.User;
import com.cassiezys.transaction.service.ProductionService;
import com.cassiezys.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    private UserService userService;
    @Autowired
    private ProductionService productionService;

    /**
     * 首页商品显示
     * @param page 页码
     * @param size 每页显示的数量
     * @param model
     * @return index.html
     */
    @GetMapping("/")
    public String index(@RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size,
                        @RequestParam(name="keyword",required = false)String keyword,
                        Model model){
        PaginationDTO<ProductionDTO> paginationDTO = productionService.addPagination(keyword,page,size );

        model.addAttribute("paginationdto",paginationDTO);
        return "index";
    }

    /**
     * 注册
     * @param model
     * @return
     */
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("title","register");
        return "register";
    }
    @PostMapping("/register")
    public String doRegister(@RequestParam(name="accountId",required=false)String accountId,
                           @RequestParam(name="password",required=false)String password,
                           @RequestParam(name="username",required = false)String username,
                           Model model){
        User userInput = new User();
        userInput.setName(username);
        userInput.setAccountId(accountId);
        userInput.setPassword(password);
        int registerCode = userService.register(userInput);
        if(registerCode==1){
            model.addAttribute("msg","创建成功，可以登录");
            return "redirect:/";
        }else{
            model.addAttribute("title","register");
            model.addAttribute("error","用户名已经被使用");
            System.out.println("注册失败");
            return "register";
        }
    }


    /**
     * 登录
     * @param model
     * @return
     */
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("title","login");
        return "register";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam(name="accountId",required=false)String accountId,
                          @RequestParam(name="password",required=false)String password,
                          @RequestParam(name="checkbox",required=false,defaultValue="1")Integer checkbox,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          Model model){
        User userInput = new User();
        userInput.setAccountId(accountId);
        userInput.setPassword(password);
        User user = userService.getUser(userInput);
        if(user !=null){
            response.addCookie(new Cookie("token",user.getToken()));
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else{
            model.addAttribute("title","login");
            model.addAttribute("error","用户名或密码错误");
            System.out.println("登录失败");
            return "register";
        }
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
