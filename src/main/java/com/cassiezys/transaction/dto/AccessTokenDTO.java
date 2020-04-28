package com.cassiezys.transaction.dto;

import lombok.Data;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:要通过okhttp发送的这里面的信息（json）信息来exchangecode以此获得token
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
