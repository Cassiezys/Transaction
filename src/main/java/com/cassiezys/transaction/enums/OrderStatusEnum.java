package com.cassiezys.transaction.enums;

public enum OrderStatusEnum {
    UNPAY(0,"未付款"),
    PAID(1,"已付款")
    ;

    private int status;
    private String statusName;

    public int getStatus() { return status;  }
    public static String nameOfStatus(int status) {
        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            if(orderStatusEnum.status == status){
                return orderStatusEnum.statusName;
            }
        }
        return "";
    }

    OrderStatusEnum(int status, String statusName) {
        this.status = status;
        this.statusName=statusName;
    }
}
