package com.qingzhou.quareat_java.utils;

import com.alibaba.fastjson2.JSONObject;

import com.qingzhou.quareat_java.exception.WechatException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * 微信工具类
 */
@Component
@Slf4j
public class WechatUtils {
    @Value("${app.wechat.user-client.appid}")
    private String appid;


    @Value("${app.wechat.user-client.secret}")
    private String secret;


    @Autowired
    private Environment env;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 作者：lzm
     * 从微信API中通过appid、secret获取accessToken
     *
     * @param appid
     * @param secret
     * @return
     */
    public String getAccessToken(String appid, String secret) {
        /**
         * 从redis中获取wechat_access_token
         */
        String wechatAccessToken = redisUtils.get("wechat_access_token_" + appid);
        log.info("redis中获取wechat_access_token：{}", wechatAccessToken);

        /**
         * 如果wechat_access_token为空的话
         */
        if (StringUtils.isEmpty(wechatAccessToken)) {
            log.info("wechat_access_token为空");
            /**
             * 请求微信获取forEntity
             */
            String tokenFetchUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
            ResponseEntity<String> forEntity = this.restTemplate.getForEntity(tokenFetchUrl, String.class);
            log.info("请求微信获取forEntity：{}", forEntity);

            if (StringUtils.isEmpty(forEntity.getBody())) {
                log.info("forEntity不为空");
                /**
                 * 将forEntity转换为json格式
                 */
                JSONObject parse = JSONObject.parse(forEntity.getBody());
                log.info("将forEntity转换为json格式：{}", parse);

                /**
                 * 获取accessToken
                 */
                wechatAccessToken = parse.getString("access_token");
                log.info("获取accessToken：{}", wechatAccessToken);

                /**
                 * 向redis中设置token
                 */
                this.redisTemplate.opsForValue().set("wechat_access_token_" + appid, wechatAccessToken);

                /**
                 * 向redis中设置token的过期时间
                 */
                this.redisTemplate.expire("wechat_access_token_" + appid, 7200, TimeUnit.SECONDS);
                log.info("Redis操作完成");

                return wechatAccessToken;
            } else {
                log.info("forEntity为空");
                return wechatAccessToken;
            }
        }

        log.info("wechat_access_token不为空");
        return wechatAccessToken;
    }

    /**
     * 作者（lzm）
     * 获取用户手机号码
     *
     * @param wechatToken
     * @param code
     * @return
     */
    public String getUserPhoneNumber(String wechatToken, String code) {
        /**
         * 请求头
         * 1、创建一个headers对象，用于设置HTTP请求的头部信息
         */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        /**
         * 请求体
         * 2、利用FastJson库将一个包含code字段的HashMap对象转化为JSON字符串，并保存在requestJson中
         */
        String requestJson = JSONObject.toJSONString(new HashMap<String, Object>() {{
            put("code", code);
        }});

        /**
         * 请求url
         * 3、构建请求URL（包含微信接口url和访问令牌）
         */
        String requestUrl = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + wechatToken;

        /**
         * 4、发送请求
         */
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, entity, String.class);

        /**
         * 5、获取参数
         */
        JSONObject responseObject = null;
        String userPhoneNumber;
        try {
            responseObject = JSONObject.parse(response.getBody());
        } catch (Exception e) {
            throw new WechatException(50001, "无法解析用户手机号响应体");
        }

        if (responseObject == null) {
            throw new WechatException(50001, "无法获取用户手机号响应体");
        } else {
            log.info("用户登陆信息：{}", responseObject.getString("errmsg"));
            String errcode = responseObject.getString("errcode");
            JSONObject userInfoObject = responseObject.getJSONObject("phone_info");

            userPhoneNumber = userInfoObject.getString("phoneNumber");
            if (errcode.equals("0")) {
                return userPhoneNumber;
            } else {
                throw new WechatException(50001, "无法获取用户手机号信息");
            }
        }
    }


    /**
     * 获取用户openId
     *
     * @param js_code
     * @return
     */
    public String getUserOpenId(String js_code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        String appid = this.appid;
        String secret = this.secret;
        String openid = "";
        // 构建请求URL
        String requestUrl = url + "?appid=" + appid + "&secret=" + secret + "&js_code=" + js_code + "&grant_type=authorization_code";

        // 创建 HttpClient 对象
        HttpClient client = HttpClient.newHttpClient();

        // 创建 HttpRequest 对象
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(requestUrl))
            .GET()
            .build();

        try {
            // 发送请求并获取响应
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 获取响应状态码
            int statusCode = response.statusCode();
            log.info("获取openId状态码：{}", statusCode);

            // 获取响应头信息
            HttpHeaders headers = response.headers();

            // 获取响应内容
            String responseBody = response.body();
            log.info("获取openId响应信息：{}", responseBody);

            // 解析 JSON 响应体
            JSONObject jsonObject = JSONObject.parseObject(responseBody);

            // 获取 openid
            openid = jsonObject.getString("openid");
            log.info("获取openId--openId：{}", openid);
        } catch (IOException | InterruptedException e) {
            log.info("获取用户openId出错");
            Thread.currentThread().interrupt();
        }
        return openid;
    }

}
