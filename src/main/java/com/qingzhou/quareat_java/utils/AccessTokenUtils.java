package com.qingzhou.quareat_java.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

/**
 * AccessToken工具类
 */
@Slf4j
@Component
public class AccessTokenUtils {

    @Autowired
    private Environment env;

    /**
     * 检验令牌
     *
     * @param accessToken
     * @return
     */
    public HashMap<String, String> verifyAccessToken(String accessToken){
        /**
         * 获取jwtSecret
         */
        String jwtSecret = env.getProperty("web.qingzhou.jwt-secret", "x1IWFzqv@zEwCVFJJz3HfG26cM#kNZg&HNTHMn1ICloPDQF*");
        log.info("获取jwtSecret--jwtSecret：{}", jwtSecret);

        HashMap<String, String> jwtData = new HashMap<>();

        try {
            /**
             * 验证 JWT
             * jwtSecret 是用于生成和验证签名的密钥。HMAC256 是指定使用 HMAC-SHA256 算法进行签名
             */
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtSecret)).build();
            log.info("验证JWT：{}", jwtVerifier);

            /**
             * 验证accessToken
             * verify 方法将返回一个 DecodedJWT 对象，其中包含了 JWT 的各个部分（例如头部、负载和签名），以及验证结果。
             */
            DecodedJWT verify = jwtVerifier.verify(accessToken);
            log.info("验证accessToken：{}", verify);

            /**
             * 检查 JWT 的受众（audience）是否与预期值 "qingzhou.service" 相符
             */
            if (!Objects.equals(verify.getAudience().get(0), "qingzhou.service")) {
                log.info("JWT携带信息错误--负载信息--{}", verify.getAudience().get(0));
            }

            /**
             * 填充信息
             */
            jwtData.put("userId", String.valueOf(verify.getClaim("userId")));
            jwtData.put("permission", String.valueOf(verify.getClaim("permission")));
            log.info("填充返回jwt信息：{}", jwtData);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jwtData;
    }

    /**
     * 设置AccessToken 使用refreshToken
     *
     * @param refreshToken
     * @return
     * @throws Exception
     */
    public String setAccessToken(String refreshToken) throws Exception {

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

        return this.generateAccessToken(userId, Integer.parseInt(permission));
    }


    /**
     * 设置AccessToken 使用userId和permission
     *
     * @param userId
     * @param permission
     * @return
     */
    public String setAccessToken(String userId, int permission) {
        return generateAccessToken(userId, permission);
    }


    /**
     * 生成AccessToken
     *
     * @param userId
     * @param permission
     * @return
     */
    private String generateAccessToken(String userId, int permission) {

        /**
         * 获取jwtSecret参数
         */
        String jwtSecret = env.getProperty("app.quareat.jwt-secret", "x1IWFzqv@zEwCVFJJz3HfG26cM#kNZg&HNTHMn1ICloPDQF*");

        /**
         * 设置过期时间：2小时
         */
        Calendar expires = Calendar.getInstance();
        expires.add(Calendar.HOUR, 2);
        log.info("设置过期时间：2小时");

        /**
         * 生成jwtToken
         */
        HashMap<String, Object> headers = new HashMap<>();
        String jwtToken = JWT.create()
                // 第一部分Header
                .withHeader(headers)
                // 第二部分Payload
                .withAudience("quareat.service")
                .withClaim("userId", userId)
                .withClaim("permission", permission)
                .withExpiresAt(expires.getTime())
                // 第三部分Signature
                .sign(Algorithm.HMAC256(jwtSecret));
        log.info("生成jwtToken--jwtToken：{}", jwtToken);

        return jwtToken;
    }


}
