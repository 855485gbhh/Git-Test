package com.qingzhou.quareat_java.log;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义mybatis-plus日志输出
 * mybatis-plus:
 *   configuration:
 *     log-impl: cn.hll.test.common.logging.MybatisLogImpl
 * @author helele
 * @date 2021/1/4 14:26
 */
@Slf4j
public class MybatisLogImpl implements Log {

    public MybatisLogImpl(String clazz) {
        //需要定义一个有参的构造函数
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void error(String s, Throwable e) {
        System.out.println("----error----    " + s);
        e.printStackTrace(System.err);
    }

    @Override
    public void error(String s) {
        System.out.println("----error----    " + s);
    }

    @Override
    public void debug(String s) {
        Pattern pattern = Pattern.compile("from|where|and|into|select|insert|left join|values|,", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        int start = 0;
        while (matcher.find()) {
            String segment = s.substring(start, matcher.start()).trim();
            System.out.println("----debug----    " + segment);
            start = matcher.start();
        }
        String lastSegment = s.substring(start).trim();
        System.out.println("----debug----    " + lastSegment);
        System.out.println(" ");
    }

    @Override
    public void trace(String s) {
        System.out.println("----trace----    " + s);
    }

    @Override
    public void warn(String s) {
        System.out.println("----warn ----    " + s);
    }
}
