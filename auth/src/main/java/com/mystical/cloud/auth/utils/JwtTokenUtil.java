package com.mystical.cloud.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

public class JwtTokenUtil {
    /**
     * 寻找证书文件
     */
    private static InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("jwt.jks");
    private static PrivateKey privateKey = null;
    private static PublicKey publicKey = null;

    static {
        // 将证书文件里边的私钥公钥拿出来
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("jwt.jks");
            // java key store 固定常量
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(inputStream, "123456".toCharArray());
            // jwt 为 命令生成整数文件时的别名
            privateKey = (PrivateKey) keyStore.getKey("jwt", "123456".toCharArray());
            publicKey = keyStore.getCertificate("jwt").getPublicKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        String s = jwtTokenUtil.parseToken("eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ5Y2MiLCJleHAiOjE2MDUxNjUwODJ9.sD361WMLZ-BRAaKqnUzARvb4HE-F-_fr-nwDFLew9hlfn55FHYUP07VYOVUJBIQvzDO90zxODlTrwUKJ1dTL8FDSxHoCRb3D44ofMVvKRHShxVLUggHnTQ7Lub0hKb5e9d-l9Am74FvTRQsK8Na24vwGfdN6lgkcgjb-R9a1vdE");
        System.out.println(s);

    }
    /**
     *
     * 使用私钥加密 token

     */
    public static String generateToken(String subject, int expirationSeconds) {
        return Jwts.builder()
                .setClaims(null)
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    /**
     *
     * 使用私钥加密 token
     *
     * @param:
     */
    public static String generateToken(String subject) {
        return Jwts.builder()
                .setClaims(null)
                .setSubject(subject)
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    /**
     *
     * 不使用公钥私钥 加密token
     */
    public static String generateToken(String subject, int expirationSeconds, String salt) {
        return Jwts.builder()
                .setClaims(null)
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000))
                // 不使用公钥私钥
                .signWith(SignatureAlgorithm.HS512, salt)
                .compact();
    }

    /**
     *
     * 通过 公钥解密token
     */
    public static String parseToken(String token) {
        String subject = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(token).getBody();
            subject = claims.getSubject();
        } catch (Exception e) {
        }
        return subject;
    }

    /**
     *
     * 不嘛通过 公钥解密token
     */
    public static String parseToken(String token,String salt) {
        String subject = null;
        try {
            Claims claims = Jwts.parser()
                    // 不使用公钥私钥
                    .setSigningKey(salt)
                    .parseClaimsJws(token).getBody();
            subject = claims.getSubject();
        } catch (Exception e) {
        }
        return subject;
    }

}
