package com.qingzhou.quareat_java.pojo.dto;

import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.core.handler.JacksonTypeHandler;
import lombok.Data;

@Data

public class JsonTestDto {
    private Long id;

    private JSONObject info;
}
