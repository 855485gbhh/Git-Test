package com.qingzhou.quareat_java.utils;


import com.qingzhou.quareat_java.exception.EntityCheckException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class ValidationUtils {

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    /**
     * 校验实体类数据
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> boolean validate(T object) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<T> violation : violations) {
                System.out.println("Entity Error: " + violation.getMessage());
            }
            return false;
        }
        System.out.println("Entity Passed");
        return true;
    }

    /**
     * 校验实体类数据，并添加校验类型
     *
     * @param object
     * @param groups
     * @param <T>
     * @return
     */
    public static <T> boolean validate(T object, Class<?>... groups) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> violations;
        if (groups != null && groups.length > 0) {
            violations = validator.validate(object, groups);
        } else {
            violations = validator.validate(object);
        }
        if (!violations.isEmpty()) {
            for (ConstraintViolation<T> violation : violations) {
//                System.err.println("检验报错信息: " + violation.getMessage());
                throw new EntityCheckException(40999, violation.getMessage());
            }
            return false;
        }
//        System.out.println("Entity Passed");
        return true;
    }
}
