package com.qingzhou.quareat_java.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.qingzhou.quareat_java.dao.TestMapper;
import com.qingzhou.quareat_java.pojo.entity.JsonTest;
import com.qingzhou.quareat_java.pojo.vo.JsonTestVo;
import com.qingzhou.quareat_java.service.TestService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.qingzhou.quareat_java.pojo.entity.table.JsonTestTableDef.JSON_TEST;

@Service
public class TestServiceImpl implements TestService {

    @Resource
    private TestMapper testMapper;


    @Override
    public List<JsonTestVo> add(JsonTest jsonTest) {
        testMapper.insertSelectiveWithPk(jsonTest);
        QueryWrapper queryWrapper = QueryWrapper.create().select()
                .from(JSON_TEST).where(JSON_TEST.ID.eq(jsonTest.getId()));
        return testMapper.selectListByQueryAs(queryWrapper, JsonTestVo.class);

    }
}
