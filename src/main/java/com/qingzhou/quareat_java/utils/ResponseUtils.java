package com.qingzhou.quareat_java.utils;


import com.qingzhou.quareat_java.pojo.enums.ErrorCodeEnums;
import com.qingzhou.quareat_java.pojo.response.JsonResponse;

/**
 * 返回参数处理工具类
 */
public class ResponseUtils {

    public static <T> JsonResponse<T> success(T data) {
        return new JsonResponse<>(0, data, "success");
    }

    public static JsonResponse<Object> success() {
        return new JsonResponse<>(0, null, "success");
    }

    public static JsonResponse<Object> error(ErrorCodeEnums errorCodeEnums, String errorMsg) {
        return new JsonResponse<>(errorCodeEnums, errorMsg);
    }

    public static JsonResponse<Object> error(int errorCode, String errorMsg) {
        return new JsonResponse<>(errorCode, errorMsg);
    }

    public static JsonResponse<Object> error(ErrorCodeEnums errorCodeEnums) {
        return new JsonResponse<>(errorCodeEnums);
    }
}
