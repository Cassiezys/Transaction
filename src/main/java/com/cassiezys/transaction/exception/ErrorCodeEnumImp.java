package com.cassiezys.transaction.exception;

public enum ErrorCodeEnumImp implements ErrorCode {
    NO_LOGIN(5000,"未登录，请先登录"),
    FAIL_UPLOAD(5001,"上传文件失败"),
    FAIL_INITSTORAGE(5002,"初始化存储区失败"),
    PRODUCTION_NOT_FOUND(5003,"没有该商品")
    ;

    private Integer code;
    private String message;

    ErrorCodeEnumImp(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
