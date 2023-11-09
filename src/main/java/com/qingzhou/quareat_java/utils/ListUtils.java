package com.qingzhou.quareat_java.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * 集合判空工具类
 */
public class ListUtils {

    /**
     * 判断集合为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty() || collection.size() <= 0;
    }

    /**
     * 判断集合非空
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }


    /**
     * 分页查询
     *
     * @param dataList，需要分页的列表
     * @param page，页数
     * @param pageSize，一页有多少条数据
     * @param <T>，泛型
     * @return
     */
    public static <T> List<T> getPageData(List<T> dataList, int page, int pageSize) {
        int totalSize = dataList.size();
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalSize);

        if (startIndex >= totalSize || startIndex < 0) {
            return new ArrayList<>();
        }

        return dataList.subList(startIndex, endIndex);
    }

    /**
     * 随机生成UUID
     *
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}
