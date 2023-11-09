package com.qingzhou.quareat_java.controller;

import com.alibaba.fastjson2.JSONObject;
import com.qingzhou.quareat_java.pojo.dto.JsonTestDto;
import com.qingzhou.quareat_java.pojo.entity.JsonTest;
import com.qingzhou.quareat_java.pojo.response.JsonResponse;
import com.qingzhou.quareat_java.pojo.vo.JsonTestVo;
import com.qingzhou.quareat_java.service.TestService;
import com.qingzhou.quareat_java.utils.JSONUtils;
import com.qingzhou.quareat_java.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/sys/test")
@Tag(name = "test")
public class TestController {
    @Autowired
    private TestService testService;

    @PostMapping("/add")
    public JsonResponse<Object> add(@RequestBody JsonTestDto jsonTestDto) {
        System.out.println("jsonTestDto = " + jsonTestDto);

        JsonTest test = new JsonTest();


        BeanUtils.copyProperties(jsonTestDto, test);

        JSONObject info = jsonTestDto.getInfo();
        String jsonString = JSONObject.toJSONString(info);
        test.setInfo(jsonString);
        System.out.println("test = " + test);

        List<JsonTestVo> voList = testService.add(test);
        return ResponseUtils.success(voList);
    }




}
