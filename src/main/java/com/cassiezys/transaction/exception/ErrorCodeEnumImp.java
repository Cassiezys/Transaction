package com.cassiezys.transaction.exception;

public enum ErrorCodeEnumImp implements ErrorCode {
    NO_LOGIN(5000,"未登录，请先登录"),
    FAIL_UPLOAD(5001,"上传文件失败"),
    FAIL_INITSTORAGE(5002,"初始化存储区失败"),
    PRODUCTION_NOT_FOUND(5003,"没有该商品"),
    PRODUCTION_CREATOR_NOT_FOUND(5003,"找不到商品的卖家 商品错误"),
    COTENT_IS_EMPTY(5004,"评论一点点也是一点点心意嘛~"),
    TARGET_PARAM_NOT_FOUND(5005,"没有选中问题或回复"),
    TYPE_NOT_FOUND(5006,"欸嘿?评论目的不明确噢~"),
    COMMENT_NOT_FOUND(5007,"该评论已经被删啦~"),
    NOTIFICATION_NOT_FOUND(5008,"该通知已经被删除啦"),
    NOTIFICATION_INFOR_ERROR(5009,"通知信息有误,账号错误")
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
