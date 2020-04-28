package com.cassiezys.transaction.controller;

import com.cassiezys.transaction.dto.AccessTokenDTO;
import com.cassiezys.transaction.dto.GitHubUser;
import com.cassiezys.transaction.model.User;
import com.cassiezys.transaction.provider.GithubProvider;
import com.cassiezys.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:GitHub登录后的redirect_uri
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserService userService;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletResponse response,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);

        //第二步：Post交换这里获得的code以此获得accessToken
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);

        //第三步：Use the access token to access the API
        GitHubUser githubUser = githubProvider.getGitHubUser(accessToken);

        if(githubUser!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            /*唯一的是accounid*/
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatar_url());
            user = userService.createOrUpdate(user);
            request.getSession().setAttribute("user",user);
            response.addCookie(new Cookie("token",user.getToken()));
            return "redirect:/";
        }else{
            //fail to login try again
            return "redirect:/";
        }
    }
}
