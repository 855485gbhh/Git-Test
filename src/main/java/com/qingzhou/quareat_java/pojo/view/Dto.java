package com.qingzhou.quareat_java.pojo.view;

import lombok.Data;

@Data
public class Dto {

    /**
     * 用户可拿
     */
    public static class UserBaseDto {
    }

    /**
     * 商户可拿
     */
    public static class MerchantBaseDto extends UserBaseDto {
    }

    /**
     * 管理员可拿
     */
    public static class AdminBaseDto extends MerchantBaseDto {
    }
}
