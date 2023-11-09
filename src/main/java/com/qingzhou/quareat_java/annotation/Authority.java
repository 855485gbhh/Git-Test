package com.qingzhou.quareat_java.annotation;

import com.qingzhou.quareat_java.pojo.enums.AuthorityTypeEnums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AOP用户注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authority {
    AuthorityTypeEnums value() default AuthorityTypeEnums.DEFAULT;
}
