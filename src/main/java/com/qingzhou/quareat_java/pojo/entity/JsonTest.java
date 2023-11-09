package com.qingzhou.quareat_java.pojo.entity;

import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

@Data
@Table(value = "test")
public class JsonTest {
    @Id
    private Long id;
    private String info;
}
