package com.cassiezys.transaction.exception;

/* 自定义异常 一定要继承RuntimeException：不然要抛异常 */
public class CustomizeCodeException extends RuntimeException {
    private Integer code;
    private String message;

    public CustomizeCodeException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
