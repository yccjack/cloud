package com.mystical.cloud.entrance.bean.response;

public enum CommonResultEnum implements ICommonEnum {
    SUCCESS("200", "成功"),
    FAILED("40000", "失败"),
    FAILED_MISSING_PARAMS("40001", "失败，参数缺失"),
    FAILED_INSUFFICIENT_AUTHORITY("40002", "失败，权限不足"),
    FAILED_404("40004", "失败,资源丢失"),
    SYSTEM_FAIL("99999", "系统错误");

    private String code;
    private String msg;

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    private CommonResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
