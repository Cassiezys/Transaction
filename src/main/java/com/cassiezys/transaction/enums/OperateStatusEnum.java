package com.cassiezys.transaction.enums;

public enum OperateStatusEnum {
    LIGHT_UP(1,"点亮"),
    LIGHT_DOWN(0,"熄灭");
    ;
    private int status;
    private String statusName;

    public static String nameOfStatus(int status){
        for(OperateStatusEnum e:OperateStatusEnum.values()){
            if(e.getStatus()==status){
                return e.getStatusName();
            }
        }
        return "";
    }

    OperateStatusEnum(int status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusName() {
        return statusName;
    }
}
