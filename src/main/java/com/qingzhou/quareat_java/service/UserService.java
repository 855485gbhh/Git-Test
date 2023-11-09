package com.qingzhou.quareat_java.service;



import com.qingzhou.quareat_java.pojo.entity.User;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    /**
     * 添加
     *
     * @param user
     * @return
     */
    User add(User user);

    /**
     * 通过参数查询数据
     *
     * @param propertyMap
     * @return
     */
    List<User> getByProperty(HashMap<String, Object> propertyMap);

    User getUserInfo(long userId);
}
