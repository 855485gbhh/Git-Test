package com.qingzhou.quareat_java.aop;


import com.auth0.jwt.exceptions.TokenExpiredException;
import com.qingzhou.quareat_java.annotation.Authority;
import com.qingzhou.quareat_java.pojo.enums.AuthorityTypeEnums;
import com.qingzhou.quareat_java.pojo.enums.ErrorCodeEnums;
import com.qingzhou.quareat_java.utils.AccessTokenUtils;
import com.qingzhou.quareat_java.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;


@Component
@Aspect
@Slf4j
public class AuthorityAop {

    @Autowired
    private AccessTokenUtils accessTokenUtils;

    /**
     * 指定注解切入
     *
     * @param @annotation(xxx)：xxx是自定义注解的全路径
     */
    @Pointcut("@annotation(com.qingzhou.quareat_java.annotation.Authority)")
    public void onAnnotation() {
    }


    /**
     * 检验用户权限
     *
     * @param point
     * @return
     * @throws Throwable
     */
    //环绕通知
    @Around("onAnnotation()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        System.out.println("========================================Aop执行：环绕通知========================================");
        /**
         * 1、获取执行方法对象
         */
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        log.info("Aop执行：获取执行方法对象：{}", method);

        /**
         * 2、判断执行方法是否有注解
         * 优先判断函数权限，若函数无权限注解，则找类权限注解，若无类权限注解，则默认为游客权限
         */
        String annotationText = "";
        int annotationValue = 0;
        if (method.isAnnotationPresent(Authority.class)) {
            //获取方法注解
            Authority methodAnnotation = method.getAnnotation(Authority.class);
            int checkAnnotationValue = methodAnnotation.value().getValue();
            if (checkAnnotationValue != 0) {
                //方法注解名
                annotationText = methodAnnotation.value().getText();
                //方法注解值
                annotationValue = methodAnnotation.value().getValue();
            } else {
                //获取类
                Class<? extends MethodSignature> aClass = signature.getClass();
                //判断类是否有注解
                if (aClass.isAnnotationPresent(Authority.class)) {
                    //获取类注解
                    Authority classAnnotation = aClass.getAnnotation(Authority.class);
                    int classCheckAnnotationValue = classAnnotation.value().getValue();
                    if (classCheckAnnotationValue != 0) {
                        annotationText = classAnnotation.value().getText();
                        annotationValue = classAnnotation.value().getValue();
                    } else {
                        //设置基本权限值
                        annotationText = AuthorityTypeEnums.CUSTOM_BASE.getText();
                        annotationValue = AuthorityTypeEnums.CUSTOM_BASE.getValue();
                    }
                }
            }
        }
        log.info("Aop执行：判断执行方法是否有注解：{}，注解名：{}，注解值：{}", method.isAnnotationPresent(Authority.class), annotationText, annotationValue);

        /**
         * 3、获取当前请求的HttpServletRequest对象
         */
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();

        if (ra == null) {
            return ResponseUtils.error(ErrorCodeEnums.AUTHORITY_ERROR, "系统出错，请稍后重试，无法获取HttpServletRequest对象");
        }

        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        log.info("Aop执行：获取当前请求的HttpServletRequest对象：{}", request);

        /**
         * 4、获取请求头中的AccessToken，并进行验证
         */
        if (annotationValue != 0) {
            //获取请求头中的AccessToken
            String authentication = request.getHeader("AccessToken");
            log.info("Aop执行：获取AccessToken：{}", authentication);

            if (authentication == null) {
                return ResponseUtils.error(ErrorCodeEnums.AUTHORITY_ERROR, "鉴权失败，请检查请求参数，无法获取AccessToken");
            }

            /**
             * 5、验证访问令牌
             */
            try {
                HashMap<String, String> verifyJwtResult = accessTokenUtils.verifyAccessToken(authentication);

                if (verifyJwtResult.equals(new HashMap<String, Object>())) {
                    return ResponseUtils.error(ErrorCodeEnums.AUTHORITY_ERROR, "鉴权失败，请检查请求参数，无法获取AccessToken信息");
                }

                if (Integer.parseInt(verifyJwtResult.get("permission")) < annotationValue) {
                    return ResponseUtils.error(ErrorCodeEnums.AUTHORITY_ERROR, "权限不足");
                }
                log.info("Aop执行：验证访问令牌，当前权限：{}，所需权限：{}", verifyJwtResult.get("permission"), annotationValue);

                /**
                 * 6、写入线程存储
                 */
                ThreadLocal<HashMap<String, Object>> threadLocal = new ThreadLocal<>();
                threadLocal.set(new HashMap<>() {{
                                    this.put("userId", verifyJwtResult.get("userId"));
                                    this.put("permission", verifyJwtResult.get("permission"));
                                }}
                );
                log.info("Aop执行：写入线程存储");

                /**
                 * 7、写入Request存储
                 */
                request.setAttribute("userId", verifyJwtResult.get("userId"));
                request.setAttribute("permission", verifyJwtResult.get("permission"));
                log.info("Aop执行：写入Request存储");
            } catch (TokenExpiredException e) {
                return ResponseUtils.error(ErrorCodeEnums.AUTHORITY_ERROR, "鉴权失败，AccessToken过期，请重新登录");
            } catch (Exception e) {
                return ResponseUtils.error(ErrorCodeEnums.AUTHORITY_ERROR, e.getMessage());
            }
        }

        System.out.println("========================================Aop执行：环绕通知========================================");
        return point.proceed();
    }
}
