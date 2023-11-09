package com.qingzhou.quareat_java.pojo.enums;

public enum ErrorCodeEnums {
    /**
     *
     */

    DEFAULT(0, "success"),
    AUTHORITY_ERROR(40100, "AUTHORITY ERROR"),//AOP鉴权失败
    VIOLATION_ERROR(40200, "VIOLATION ERROR"),//数据校验失败
    ADD_ERR(40300, "ADD ERROR"),
    UPDATE_ERROR(40400, "UPDATE ERROR"),
    DEL_ERROR(40500, "DELETE ERROR"),
    SYSTEM_ERROR(49999, "SYSTEM ERROR");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCodeEnums(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
