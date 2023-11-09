package com.qingzhou.quareat_java.controller;

import com.qingzhou.quareat_java.annotation.Authority;
import com.qingzhou.quareat_java.pojo.enums.AuthorityTypeEnums;
import com.qingzhou.quareat_java.pojo.response.JsonResponse;
import com.qingzhou.quareat_java.service.UserService;
import com.qingzhou.quareat_java.utils.RequestUtils;
import com.qingzhou.quareat_java.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzm19
 * @title UserController
 * @date 2023/11/6 14:21
 * @description TODO
 */
@RequestMapping("/user")
@RestController
@Slf4j
@Tag(name = "用户-控制器")
public class UserController {

    // TODO: 2023/11/6 登录功能，用户信息管理，消息管理，收藏管理，系统管理，
    // TODO: 2023/11/6  申请成为商户 ，查询商铺，查询评论

    @Autowired
    private UserService userService;


    @Autowired
    private RequestUtils requestUtils;

    @GetMapping("/info")
    @Authority(AuthorityTypeEnums.USER_BASE)
    public JsonResponse<Object> getUserInfo(HttpServletRequest request) {
        long userId = requestUtils.getUserId(request);

        return ResponseUtils.success(userService.getUserInfo(userId));


    }
}
