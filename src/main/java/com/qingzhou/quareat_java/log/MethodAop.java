package com.qingzhou.quareat_java.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Aspect
@Slf4j
public class MethodAop {
    private static String getFormatLogString(String content, int colour, int type) {
        boolean hasType = type != 1 && type != 3 && type != 4;
        if (hasType) {
            return String.format("\033[%dm%s\033[0m", colour, content);
        } else {
            return String.format("\033[%d;%dm%s\033[0m", colour, type, content);
        }
    }

    /**
     * 前置通知
     *
     * @param joinPoint
     */
    @Before("execution(* com.qingzhou.quareat_java.controller.*.*(..))")
    public void beforeController(JoinPoint joinPoint) {
        System.out.println(getFormatLogString("----------------------------------------------------------------------------------controller前置通知-----------------------------------------------------------------------------------", 31, 0));
        System.out.println(" ");
        beforeDetail(joinPoint);
        System.out.println(" ");
        System.out.println(getFormatLogString("----------------------------------------------------------------------------------controller前置通知-----------------------------------------------------------------------------------", 31, 0));
        System.out.println(" ");
    }

    @Before("execution(* com.qingzhou.quareat_java.service.*.*(..))")
    public void beforeService(JoinPoint joinPoint) {
        System.out.println(getFormatLogString("----------------------------------------------------------------------------------service前置通知-----------------------------------------------------------------------------------", 32, 0));
        System.out.println(" ");
        beforeDetail(joinPoint);
        System.out.println(" ");
        System.out.println(getFormatLogString("----------------------------------------------------------------------------------service前置通知-----------------------------------------------------------------------------------", 32, 0));
        System.out.println(" ");
    }

    @Before("execution(* com.qingzhou.quareat_java.dao.*.*(..))")
    public void beforeMapper(JoinPoint joinPoint) {
        System.out.println(getFormatLogString("----------------------------------------------------------------------------------mapper前置通知-----------------------------------------------------------------------------------", 33, 0));
        System.out.println(" ");
        beforeDetail(joinPoint);
        System.out.println(" ");
        System.out.println(getFormatLogString("----------------------------------------------------------------------------------mapper前置通知-----------------------------------------------------------------------------------", 33, 0));
        System.out.println(" ");
    }

    @Before("execution(* com.qingzhou.quareat_java.utils.*.*(..))")
    public void beforeUtils(JoinPoint joinPoint) {
        System.out.println("----------------------------------------------------------------------------------utils前置通知-----------------------------------------------------------------------------------");
        System.out.println(" ");
        beforeDetail(joinPoint);
        System.out.println(" ");
        System.out.println("----------------------------------------------------------------------------------utils前置通知-----------------------------------------------------------------------------------");
        System.out.println(" ");
    }

    /**
     * 获取方法参数值
     *
     * @param joinPoint
     * @return
     */
    private String[] getMethodParameterNames(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        beforeAdvice(method);

        Parameter[] parameters = method.getParameters();

        String[] parameterNames = new String[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            parameterNames[i] = parameters[i].getName();
        }

        return parameterNames;
    }

    /**
     * 获取注解信息
     *
     * @param method
     */
    public void beforeAdvice(Method method) {
        // 获取方法上的所有注解
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            // 输出注解类型和值
            System.out.println("方法注解: " + annotation.annotationType().getSimpleName());

            // 如果注解是你想要的类型，可以进行相应的处理
            if (annotation instanceof GetMapping getAnnotation) {
                System.out.println("注解值  : " + Arrays.toString(getAnnotation.value()));
            }
            if (annotation instanceof PostMapping getAnnotation) {
                System.out.println("注解值  : " + Arrays.toString(getAnnotation.value()));
            }
        }
    }

    /**
     * 前置方法
     *
     * @param joinPoint
     */
    public void beforeDetail(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodPath = className + "." + methodName;
        System.out.println("方法路径：" + methodPath);

        Object[] args = joinPoint.getArgs();
        String[] parameterNames = getMethodParameterNames(joinPoint);

        for (int i = 0; i < args.length; i++) {
            String paramName = parameterNames[i];
            Object paramValue = args[i];
            System.out.println("参数名：" + paramName + "，参数值：" + paramValue);
        }
    }

    /**
     * 后置通知
     *
     * @param joinPoint
     * @param result
     */
    @AfterReturning(pointcut = "execution(* com.qingzhou.quareat_java.controller.*.*(..))", returning = "result")
    public void afterController(JoinPoint joinPoint, Object result) {
        System.out.println(getFormatLogString("----------------------------------------------------------------------------------controller后置通知-----------------------------------------------------------------------------------", 31, 0));
        System.out.println(" ");
        afterDetail(joinPoint, result);
        System.out.println(" ");
        System.out.println(getFormatLogString("----------------------------------------------------------------------------------controller后置通知-----------------------------------------------------------------------------------", 31, 0));
        System.out.println(" ");
    }

    @AfterReturning(pointcut = "execution(* com.qingzhou.quareat_java.service.*.*(..))", returning = "result")
    public void afterService(JoinPoint joinPoint, Object result) {
        System.out.println(getFormatLogString("----------------------------------------------------------------------------------service后置通知-----------------------------------------------------------------------------------", 32, 0));
        System.out.println(" ");
        afterDetail(joinPoint, result);
        System.out.println(" ");
        System.out.println(getFormatLogString("----------------------------------------------------------------------------------service后置通知-----------------------------------------------------------------------------------", 32, 0));
        System.out.println(" ");
    }

    @AfterReturning(pointcut = "execution(* com.qingzhou.quareat_java.dao.*.*(..))", returning = "result")
    public void afterMapper(JoinPoint joinPoint, Object result) {
        System.out.println(getFormatLogString("----------------------------------------------------------------------------------mapper后置通知-----------------------------------------------------------------------------------", 33, 0));
        System.out.println(" ");
        afterDetail(joinPoint, result);
        System.out.println(" ");
        System.out.println(getFormatLogString("----------------------------------------------------------------------------------mapper后置通知-----------------------------------------------------------------------------------", 33, 0));
        System.out.println(" ");
    }

    @AfterReturning(pointcut = "execution(* com.qingzhou.quareat_java.utils.*.*(..))", returning = "result")
    public void afterUtils(JoinPoint joinPoint, Object result) {
        System.out.println("----------------------------------------------------------------------------------utils后置通知-----------------------------------------------------------------------------------");
        System.out.println(" ");
        afterDetail(joinPoint, result);
        System.out.println(" ");
        System.out.println("----------------------------------------------------------------------------------utils后置通知-----------------------------------------------------------------------------------");
        System.out.println(" ");
    }

    /**
     * 后置方法
     *
     * @param joinPoint
     * @param result
     */
    public void afterDetail(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodPath = className + "." + methodName;
        System.out.println("方法路径：" + methodPath);
        System.out.println("返回值：" + result);
    }
}
