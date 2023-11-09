package com.qingzhou.quareat_java.exception;

/**
 * @author: wangruosong
 * @createTime: 2023/05/05 23:38
 * @company: EasyBoat
 * @description:
 * @project: quareat
 */
public class WechatException extends RuntimeException {
    final int code;

    public WechatException(int code, String errorMsg) {
        super(errorMsg);
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
