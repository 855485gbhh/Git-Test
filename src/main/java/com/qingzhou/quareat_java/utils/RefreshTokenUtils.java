package com.qingzhou.quareat_java.utils;


import com.qingzhou.quareat_java.pojo.entity.RefreshToken;
import com.qingzhou.quareat_java.service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Slf4j
@Component
public class RefreshTokenUtils {
    @Autowired
    private Environment env;

    @Autowired
    private RefreshTokenService refreshTokenService;

    /**
     * 通过旧refreshToken生成新refreshToken
     *
     * @param refreshToken
     * @return
     * @throws Exception
     */
    public String setRefreshToken(String refreshToken) throws Exception {

        String jwtSecret = env.getProperty("web.qingzhou.jwt-secret", "x1IWFzqv@zEwCVFJJz3HfG26cM#kNZg&HNTHMn1ICloPDQF*");

        /**
         * 获取aesSecret
         */
        String aesSecret = env.getProperty("web.qingzhou.aes-secret", "PRQmOofmqHQioplmHmm1XwKk5V3oGrCH");


        /**
         * 获取decryptToken
         */
        String decryptToken = AESUtils.decrypt(aesSecret, refreshToken);


        /**
         * 获取token
         */
        byte[] decodedBytes = Base64.getDecoder().decode(decryptToken);
        String token = new String(decodedBytes);
        String[] tokenSplit = token.split("&");


        /**
         * 获取token中信息
         */
        String userId = tokenSplit[0];
        String generateTime = tokenSplit[1];
        String permission = tokenSplit[2];
        String issuingServer = tokenSplit[3];

        return setRefreshToken(userId, Integer.parseInt(permission));
    }

    /**
     * 设置RefreshToken，使用userId，permission
     *
     * @param userId
     * @param permission
     * @return
     * @throws Exception
     */
    public String setRefreshToken(String userId, int permission) throws Exception {

        /**
         * 获取aesSecret参数
         */
        String aesSecret = env.getProperty("app.quareat.aes-secret", "PRQmOofmqHQioplmHmm1XwKk5V3oGrCH");
        log.info("获取aesSecret参数：{}", aesSecret);

        /**
         * 初始化refreshToken对象
         */
        String nowTime = String.valueOf(System.currentTimeMillis() / 1000);
        String randCode = StringUtils.randomStr(24);
        String[] dataList = {userId, nowTime, String.valueOf(permission), randCode, "java-1"};
        String dataStr = String.join("&", dataList);
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUserId(userId);
        refreshToken.setSignCode(randCode);
        refreshToken.setIssueServer("java-1");
        refreshToken.setExpireTime(System.currentTimeMillis() / 1000 + 259200);


        /**
         * 插入数据
         */
        refreshToken = refreshTokenService.add(refreshToken);
        log.info("添加数据：{}", refreshToken);

        if (refreshToken == null) {
            throw new Exception("添加refreshToken失败");
        }

        /**
         * 数据加密，
         * 对encodedString加密，
         * aesSecret是密钥，
         * encrypt是加密后的结果
         */
        String encodedString = Base64.getEncoder().encodeToString(dataStr.getBytes());

        /**
         * 返回数据
         */
        return AESUtils.encrypt(aesSecret, encodedString);
    }

}
