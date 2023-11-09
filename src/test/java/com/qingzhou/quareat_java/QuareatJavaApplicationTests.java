package com.qingzhou.quareat_java;

import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.update.UpdateChain;
import com.qingzhou.quareat_java.dao.TestMapper;
import com.qingzhou.quareat_java.pojo.entity.JsonTest;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.HashMap;
import java.util.List;



@SpringBootTest
class QuareatJavaApplicationTests {

    @Resource
    private TestMapper testMapper;


    @Test
    void test1() {
        JSONObject object = JSONObject.parse("{\"name\":\"Kiana\"}");
        HashMap<String, Object> map = new HashMap<>();
        map.put("info", object);
        List<JsonTest> jsonTests = testMapper.selectListByMap(map);

    }

}
