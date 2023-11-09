package com.qingzhou.quareat_java.exception;

public class EntityCheckException extends RuntimeException{
    final int code;

    public EntityCheckException(int code, String errorMsg) {
        super(errorMsg);
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
