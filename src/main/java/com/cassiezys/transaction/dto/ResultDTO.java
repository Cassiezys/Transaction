package com.cassiezys.transaction.dto;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:返回到页面的结果码
 */
public class ResultDTO<T> {
    private Integer code;
    private String message;
    //泛型：传结果
    private T data;


}
