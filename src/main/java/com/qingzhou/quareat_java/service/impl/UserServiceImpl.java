package com.qingzhou.quareat_java.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.qingzhou.quareat_java.dao.UserMapper;
import com.qingzhou.quareat_java.pojo.entity.User;
import com.qingzhou.quareat_java.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User add(User user) {
        return null;
    }

    @Override
    public List<User> getByProperty(HashMap<String, Object> propertyMap) {
        return userMapper.selectListByMap(propertyMap);
    }

    @Override
    public User getUserInfo(long userId) {
    return null;
    }
}
