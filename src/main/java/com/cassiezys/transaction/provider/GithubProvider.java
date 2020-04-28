package com.cassiezys.transaction.provider;

import com.alibaba.fastjson.JSON;
import com.cassiezys.transaction.dto.AccessTokenDTO;
import com.cassiezys.transaction.dto.GitHubUser;
import com.cassiezys.transaction.model.User;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:外接GitHub接口，使用OAuth登录
 */
@Component
public class GithubProvider {
    /**
     * 第二步：利用okhttpPOST发送信息（要将Java类转换为json对象传输），交换code，获得accesstoken
     * @param accessTokenDTO
     * @return
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        //利用OKHTTP 发送 POST 请求 将 accessTokenDTO类转换成数据传输的JSON对象
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            System.out.println(str);
            //发现打印出来的并不是json对象，拆分
            String[] split = str.split("&");
            String tokenStr = split[0];
            String token = tokenStr.split("=")[1];
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 第三步：Get请求 通过okhttp 发 accessToken获取用户信息
     * @param accessToken
     * @return
     */
    public GitHubUser getGitHubUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();

            //自动将json字符串填充到java类中
            GitHubUser githubUser = JSON.parseObject(str, GitHubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
