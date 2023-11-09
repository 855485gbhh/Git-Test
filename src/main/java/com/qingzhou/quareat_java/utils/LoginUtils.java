package com.qingzhou.quareat_java.utils;


import com.qingzhou.quareat_java.pojo.dto.WechatUserLoginRequestDto;
import com.qingzhou.quareat_java.pojo.entity.User;
import com.qingzhou.quareat_java.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 登录工具类
 */
@Slf4j
@Component
public class LoginUtils {

    @Autowired
    private Environment env;

    @Autowired
    private WechatUtils wechatUtils;

    @Autowired
    private AccessTokenUtils accessTokenUtils;

    @Autowired
    private RefreshTokenUtils refreshTokenUtils;

    @Autowired
    private UserService userService;

    /**
     * 修改：完成
     * <p>
     * 微信Token模式登录
     *
     * @param userLoginRequest
     * @return
     * @throws Exception
     */
    public HashMap<String, String> wechatUserLogin(WechatUserLoginRequestDto userLoginRequest) throws Exception {
        /**
         * 获取用户微信参数
         */
        HashMap<String, String> tokenInfo = new HashMap<>();
        String appId = this.env.getProperty("app.wechat.user-client.appid");
        String secret = this.env.getProperty("app.wechat.user-client.secret");

        /**
         * 获取微信token
         */
        String wechatAppToken = wechatUtils.getAccessToken(appId, secret);

        /**
         * 获取用户手机号码
         */
        String userPhoneNumber = wechatUtils.getUserPhoneNumber(wechatAppToken, userLoginRequest.getCode());

        /**
         * 通过手机号读取数据库判断是否注册
         */
        User user_select = new User();
//        user_select.setPhone(Long.valueOf(userPhoneNumber));
        user_select.setBindPhone(userPhoneNumber);
//        List<User> userList = userService.getByProperty(user_select);
        List<User> userList = new ArrayList<>();
        if (userList.size() > 1) {
            throw new Exception("用户数据异常：多个手机号注册");
        }

        /**
         * 如果没有注册进行注册
         */
        User user = new User();
        if (userList.size() == 0) {
//            user.setPhone(Long.valueOf(userPhoneNumber));
            user_select.setBindPhone(userPhoneNumber);
            user = userService.add(user);
            log.info("用户是否成功注册信息：{}", user);

            if (user.getName() == null) {
                throw new Exception("用户数据异常：添加用户失败");
            }
        }

        /**
         * 获取用户id以及permission信息
         */

        if (userList.size() == 1) {
            user = userList.get(0);
        }
        String userId = String.valueOf(user.getId());
        long permission = user.getPermission();


        /**
         * 获取accessToken以及refreshToken
         */
        String accessToken = accessTokenUtils.setAccessToken(userId, (int) permission);
        String refreshToken = refreshTokenUtils.setRefreshToken(userId, (int) permission);

        /**
         * 填充数据
         */
        tokenInfo.put("accessToken", accessToken);
        tokenInfo.put("refreshToken", refreshToken);

        return tokenInfo;
    }
}
