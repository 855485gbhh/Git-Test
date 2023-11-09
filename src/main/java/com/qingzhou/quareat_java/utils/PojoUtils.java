package com.qingzhou.quareat_java.utils;

import com.github.yitter.idgen.YitIdHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * 初始化对象工具类
 * @param <T>
 */
@Slf4j
@Component
public class PojoUtils<T> {

    /**
     * 初始化对象
     * <p>
     * 初始化属性：id，createTime， updateTime
     *
     * @param entity
     * @return
     */
    public T processPojo(T entity) {
        try {
            //1、获取对象的类型
            Class<?> clazz = entity.getClass();

            //2、获取属性名为id的属性并进行初始化操作
            Field idField = clazz.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, YitIdHelper.nextId());

            //3、获取当前时间戳
            Date date = new Date();
            long timestamp = date.getTime() / 1000;

            //4、获取属性名为createTime的属性并进行初始化操作
            Field createTimeField = clazz.getDeclaredField("createTime");
            createTimeField.setAccessible(true);
            createTimeField.set(entity, timestamp);

            //5、获取属性名为updateTime的属性并进行初始化操作
            Field updateTimeField = clazz.getDeclaredField("updateTime");
            updateTimeField.setAccessible(true);
            updateTimeField.set(entity, timestamp);

            //6、返回数据
            return entity;
        } catch (NoSuchFieldException | IllegalAccessException e) {

            //7、抛出异常
            throw new IllegalArgumentException("PojoUtils--初始化对象失败", e);
        }
    }

    /**
     * 更新对象
     * <p>
     * 更新属性：updateTime
     *
     * @param entity
     * @return
     */
    public T setPojo(T entity) {
        try {
            //1、获取对象的类型
            Class<?> clazz = entity.getClass();

            //2、获取属性名为updateTime的属性并进行初始化操作
            Field updateTimeField = clazz.getDeclaredField("updateTime");
            updateTimeField.setAccessible(true);
            updateTimeField.set(entity, System.currentTimeMillis() / 1000);

            //3、返回数据
            return entity;
        } catch (NoSuchFieldException | IllegalAccessException e) {

            //4、抛出异常
            throw new IllegalArgumentException("PojoUtils--更新对象失败", e);
        }
    }

}
