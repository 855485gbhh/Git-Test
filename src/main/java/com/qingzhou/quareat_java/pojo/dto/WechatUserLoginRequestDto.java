package com.qingzhou.quareat_java.pojo.dto;

import lombok.Data;

/**
 * wechat登录接受dto
 */
@Data
public class WechatUserLoginRequestDto {
    private String code;
    private String userCode;
}
