package com.mystical.cloud.auth.bean;

/**
 * @author ycc
 * @time 15:47
 */
public enum RoleConstant {
    MANAGER("0001", "ADMIN"),
    USER_GENERAL("0010", "GENERAL"),
    USER_VIP("0011", "VIP");


    private String code;
    private String msg;

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

     RoleConstant(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
