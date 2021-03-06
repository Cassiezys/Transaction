package com.cassiezys.transaction.enums;

public enum NotificationTypeEnum {
    REPLY_PRODUCTION(1,"评论了商品"),
    REPLY_COMMENT(2,"回复了评论"),
    PRODUCT_SELL(3,"购买了商品"),
    PRODUCT_BOOK(4,"下了订单"),
    PRODUCT_EMPTY(5,"买完了商品"),
    PRODUCT_FAVOR(6,"收藏了商品"),
    COMMENT_FAVOR(7,"点赞了评论"),
    ;
    private int type;
    private String name;

    public static String nameOfType(Integer type) {
        for (NotificationTypeEnum value : NotificationTypeEnum.values()) {
            if(type == value.getType()){
                return value.getName();
            }
        }
        return "";
    }

    public int getType() { return type;  }

    public String getName() {  return name;  }

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
