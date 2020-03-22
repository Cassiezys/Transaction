package com.cassiezys.transaction.enums;

public enum OperateTypeEnum {
    Favorite(1,"收藏"),
    ThumbsUp(2,"点赞")

    ;
    private int type;
    private String name;

    OperateTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
    public static int typeOf(String name){
        for(OperateTypeEnum e : OperateTypeEnum.values()){
            if(e.getName().equals(name)){
                return e.getType();
            }
        }
        return -1;
    }
    public static String nameOf(int value){
        for(OperateTypeEnum e:OperateTypeEnum.values()){
            if(e.getType()==value){
                return e.getName();
            }
        }
        return "";
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
