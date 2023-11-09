package com.qingzhou.quareat_java.pojo.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum AuthorityTypeEnums {

    DEFAULT("空权限", 0),
    CUSTOM_BASE("访客权限", 1),
    USER_BASE("用户基础权限", 100),
    USER_VIP("会员权限", 101),
    USER_SUPER("用户超级权限", 199),
    MERCHANT_BASE("商家基础权限", 200),
    VENDOR_BASE("厂商基础权限", 300),
    ADMIN_BASE("管理员基础权限", 901),
    ADMIN_SERVICE("管理员客服权限", 902),
    ADMIN_SUPER("管理员超级权限", 999);

//    DEFAULT("未处理", 0),
//
//    HANDLED("已处理", 1);

    private final String text;

    private final int value;

    AuthorityTypeEnums(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
