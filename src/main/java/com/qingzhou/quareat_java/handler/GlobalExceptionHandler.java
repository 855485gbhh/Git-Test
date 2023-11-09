package com.qingzhou.quareat_java.handler;

import com.qingzhou.quareat_java.exception.WechatException;

import com.qingzhou.quareat_java.pojo.enums.ErrorCodeEnums;
import com.qingzhou.quareat_java.pojo.response.JsonResponse;
import com.qingzhou.quareat_java.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



/**
 * 全局异常处理器
 * */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(WechatException.class)
    public JsonResponse<Object> wechatExceptionHandler(WechatException e) {
        log.error("businessException: " + e.getMessage(), e);
        return ResponseUtils.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public JsonResponse<Object> runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException", e.getMessage(), e);
        return ResponseUtils.error(ErrorCodeEnums.SYSTEM_ERROR, e.getMessage());
    }



}
