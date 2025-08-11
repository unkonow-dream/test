package com.sakura.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT 工具类
 * 提供创建和解析 JWT 的方法
 * 使用 HS256 算法进行签名
 */
public class JwtUtil {

    // Token 默认有效期：1 小时
    public static final long JWT_TTL = 60 * 60 * 1000L;

    // 密钥（Base64 编码字符串）
    public static final String JWT_KEY = "mengshujun";

    /**
     * 生成 UUID，用作 JWT ID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 创建 JWT（使用默认 ID）
     *
     * @param subject    主题（一般为 JSON 字符串，存储用户信息）
     * @param ttlMillis  过期时间（毫秒）
     * @return JWT 字符串
     */
    public static String createJWT(String subject, Long ttlMillis) {
        return getJwtBuilder(subject, ttlMillis, getUUID()).compact();
    }

    /**
     * 创建 JWT（指定 ID）
     *
     * @param id         JWT ID
     * @param subject    主题（用户信息等）
     * @param ttlMillis  过期时间（毫秒）
     * @return JWT 字符串
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        return getJwtBuilder(subject, ttlMillis, id).compact();
    }

    /**
     * 构建 JWTBuilder
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        if (ttlMillis == null) {
            ttlMillis = JWT_TTL;
        }

        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        return Jwts.builder()
                .setId(uuid)                  // 唯一 ID
                .setSubject(subject)         // 主题（用户信息）
                .setIssuer("sakura")         // 签发者
                .setIssuedAt(now)            // 签发时间
                .setExpiration(expDate)      // 过期时间
                .signWith(signatureAlgorithm, secretKey); // 签名算法 & 密钥
    }

    /**
     * 生成密钥，用于 JWT 签名和解析
     *
     * @return SecretKey
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JWT_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 解析 JWT，返回 Claims
     *
     * @param jwt JWT 字符串
     * @return Claims（载荷）
     * @throws Exception 签名错误或格式异常
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
