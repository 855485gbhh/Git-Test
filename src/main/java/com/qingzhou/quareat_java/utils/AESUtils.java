package com.qingzhou.quareat_java.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * AES加密解密工具类
 */
@Slf4j
public class AESUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    /**
     * 加密
     *
     * @param key
     * @param plainText
     * @return
     * @throws Exception
     */
    public static String encrypt(String key, String plainText) throws Exception {
        log.info("AES工具--加密--密钥：{}， 内容：{}", key, plainText);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherText = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    /**
     * 解密
     *
     * @param key
     * @param encryptedText
     * @return
     * @throws Exception
     */
    public static String decrypt(String key, String encryptedText) throws Exception {
        log.info("AES工具--解密--密钥：{}， 内容：{}", key, encryptedText);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] cipherText = Base64.getDecoder().decode(encryptedText);
        byte[] plainText = cipher.doFinal(cipherText);
        return new String(plainText);
    }
}
