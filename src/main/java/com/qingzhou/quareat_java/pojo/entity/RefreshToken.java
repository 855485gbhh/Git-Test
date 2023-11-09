package com.qingzhou.quareat_java.pojo.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

@Table("refresh_token")
@Data
public class RefreshToken {

    private Long id;
    private String userId;
    private String signCode;
    private String issueServer;
    private Long expireTime;
    private Long updateTime;
    private Long createTime;
    private Long isDeleted;
}
