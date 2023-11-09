package com.qingzhou.quareat_java.utils;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 请求参数处理工具类
 */
@Slf4j
@Component
public class RequestUtils {

    /**
     * 获取用户id
     *
     * @param request
     * @return
     */
    public long getUserId(HttpServletRequest request) {
        System.out.println("****************************Request工具：获取用户id--RequestUtils.getUserId****************************");
        Object userId = request.getAttribute("userId");
        log.info("用户Object_id：{}", userId);

        if (userId == null) {
            return 0L;
        }

        String userIdString = userId.toString().trim().replaceAll("[^0-9]", "");
        log.info("用户String_id：{}", userIdString);
        System.out.println("****************************Request工具：获取用户id--RequestUtils.getUserId****************************");
        return Long.parseLong(userIdString.toString());
    }


    /**
     * 获取请求的路径,包含controller的映射和方法映射和路径参数
     * @param request
     * @param response
     * @return
     */
    public static String getServletPath(HttpServletRequest request, HttpServletResponse response){
        return request.getServletPath();
    }

    /**
     * 获取真实Url，包含controller的映射和方法映射，去除path的参数
     * @param handler
     * @param url
     * @return
     */
    public static String getRealUrl(Object handler, String url){
        Annotation[][] parameterAnnotations = ((HandlerMethod) handler).getMethod().getParameterAnnotations();
        int i = 0;
        for (Annotation[] annotations : parameterAnnotations) {
            for (Annotation annotation : annotations) {
                if(annotation instanceof PathVariable){
                    i++;
                    break;
                }
            }
        }
        if (i == 0){
            return url;
        }
        List<String> split = Arrays.asList(url.split("\\/"));
        List<String> subList = split.subList(0, split.size() - i);

        String join = "";
        for (String item: subList) {
            join = join + "/" + item;
        }
//        String join = Joiner.on("/").join(subList);
        return join;
    }

    /**
     * 获取请求的路径映射，包含controller的映射和方法映射，失灵时不灵
     * @param request
     * @param response
     * @return
     */
    public static String getServletRequestMapping(HttpServletRequest request, HttpServletResponse response){
        String servletPath = getServletPath(request, response);
        //Map<String, String[]> parameterMap = getParameterMap(request, response);
        Map<String, String[]> parameterMap = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (CollectionUtils.isEmpty(parameterMap)){
            return servletPath;
        }
        Integer paramSize = parameterMap.size();
        int count = StrUtil.count(servletPath, "/");
        int subIndex = StrUtil.ordinalIndexOf(servletPath,"/", count - paramSize + 1);
        String result = servletPath.substring(0, subIndex);
        return result;
    }

    /**
     * 获取请求的路径的所有参数map
     * @param request
     * @param response
     * @return
     */
    public static Map<String, String[]> getParameterMap(HttpServletRequest request, HttpServletResponse response){
        return request.getParameterMap();
    }

//    /**
//     * 获取请求的路径的指定参数的value数组
//     * @param paramKey 请求的路径的参数key
//     * @param request 请求
//     * @param response 响应
//     * @return
//     */
//    public static String[] getParameterArrayByParamKey(@NotNull String paramKey, HttpServletRequest request, HttpServletResponse response){
//        Map<String, String[]> parameterMap = getParameterMap(request, response);
//        for (String key : parameterMap.keySet()) {
//            if (key.equals(paramKey)){
//                return parameterMap.get(key);
//            }
//        }
//        return null;
//    }
//    /**
//     * 获取请求的路径的指定参数的value数组中第index的值
//     * @param paramKey 请求的路径的参数key
//     * @param request 请求
//     * @param response 响应 getFirstParameterByParamKey
//     * @return
//     */
//    public static String getIndexParameterByParamKey(@NotNull String paramKey, int index, HttpServletRequest request, HttpServletResponse response){
//        String[] parameterArray = getParameterArrayByParamKey(paramKey, request, response);
//        for (int i = 0, length = parameterArray.length; i < length; i++ ){
//            if (i == index){
//                return parameterArray[i];
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 获取请求的路径的指定参数的value数组中第index的值
//     * @param paramKey 请求的路径的参数key
//     * @param request 请求
//     * @param response 响应
//     * @return
//     */
//    public static String getFirstParameterByParamKey(@NotNull String paramKey, HttpServletRequest request, HttpServletResponse response){
//        return getIndexParameterByParamKey(paramKey, 0, request, response);
//    }

}
