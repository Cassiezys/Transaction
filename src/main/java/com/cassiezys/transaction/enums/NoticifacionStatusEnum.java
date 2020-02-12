package com.cassiezys.transaction.enums;

public enum NoticifacionStatusEnum {
    UNREAD(0),
    READ(1)
    ;
    private int status;

    public int getStatus() {
        return status;
    }

    NoticifacionStatusEnum(int status) {
        this.status = status;
    }
}
