package com.qingzhou.quareat_java.service;

import com.qingzhou.quareat_java.pojo.entity.JsonTest;
import com.qingzhou.quareat_java.pojo.vo.JsonTestVo;

import java.util.List;

public interface TestService {

    List<JsonTestVo> add(JsonTest jsonTest);
}
