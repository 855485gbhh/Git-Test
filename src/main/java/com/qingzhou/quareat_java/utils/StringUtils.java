package com.qingzhou.quareat_java.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

/**
 * 字符串处理工具类
 */
@Slf4j
@Component
public class StringUtils {
    /**
     * 根据传入的字符串和每个字符串item的length，切割成字符串数组
     *
     * @param src
     * @param length
     * @return
     */
    public static String[] stringToStringArray(String src, int length) {
        // 检查参数是否合法
        if (null == src || src.equals("")) {
            return null;
        }

        if (length <= 0) {
            return null;
        }
        // 获取整个字符串可以被切割成字符子串的个数
        int n = (src.length() + length - 1) / length;
        String[] split = new String[n];
        for (int i = 0; i < n; i++) {
            if (i < (n - 1)) {
                split[i] = src.substring(i * length, (i + 1) * length);
            } else {
                split[i] = src.substring(i * length);
            }
        }
        return split;
    }

    /**
     * 获取长度为size的随机字符串
     *
     * @param size
     * @return
     */
    public static String randomStr(int size) {
        String dictionary = "abcdefghigklmnopqrstuvwxyz0123456789";
        char[] dicArr = dictionary.toCharArray();
        int arrSize = dicArr.length;
        StringBuilder randomResult = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int selectRand = new Random().nextInt(arrSize);
            randomResult.append(dicArr[selectRand]);
        }

        return randomResult.toString();
    }

    /**
     * 获取长度为size的随机数字字符串
     *
     * @param size
     * @return
     */
    public static String randomNum(int size) {
        String dictionary = "0123456789";
        char[] dicArr = dictionary.toCharArray();
        int arrSize = dicArr.length;
        StringBuilder randomResult = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int selectRand = new Random().nextInt(arrSize);
            randomResult.append(dicArr[selectRand]);
        }

        return randomResult.toString();
    }

    /**
     * 获取长度为size的随机字符串，withoutCapital为是否存在大写英文字母
     *
     * @param size
     * @param withoutCapital
     * @return
     */
    public static String randomStr(int size, boolean withoutCapital) {
        String dictionary = "";
        if (withoutCapital) {
            dictionary = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        } else {
            dictionary = "abcdefghijklmnopqrstuvwxyz0123456789";
        }

        char[] dicArr = dictionary.toCharArray();
        int arrSize = dicArr.length;
        StringBuilder randomResult = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int selectRand = new Random().nextInt(arrSize);
            randomResult.append(dicArr[selectRand]);
        }

        return randomResult.toString();
    }

    /**
     * 通过传入的字符串和当前时间戳生成一个新的随机字符串
     *
     * @param inputString
     * @return
     */
    public static String generateRandomString(String inputString) {
        long timestamp = System.currentTimeMillis(); // 获取当前时间戳
        return UUID.nameUUIDFromBytes((inputString + timestamp).getBytes()).toString();
    }

    /**
     * 判断是否为空字符串最优代码
     *
     * @param str
     * @return 如果为空，则返回true
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断字符串是否非空
     *
     * @param str 如果不为空，则返回true
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
