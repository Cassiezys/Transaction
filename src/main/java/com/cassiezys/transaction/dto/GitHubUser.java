package com.cassiezys.transaction.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:第三步Get请求获得的User信息
 */
@Data
public class GitHubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;
}
