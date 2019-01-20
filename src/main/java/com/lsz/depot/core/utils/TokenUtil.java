package com.lsz.depot.core.utils;

import io.jsonwebtoken.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    private static final String HEADER_NAME = "Authorization";
    private static String key = "lsz";

    /**
     * 获取Request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
    }

    /**
     * 获取Response
     *
     * @return
     */
    public static HttpServletResponse getResponse() {
        return getServletRequestAttributes().getResponse();
    }

    public static Key getSecretKey() {

        byte[] encodedKey = Base64Util.decryptBASE64(key).getBytes();
        // 使用AES对称加密
        Key key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static ServletRequestAttributes getServletRequestAttributes() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs;
    }

    public static String getCurrentUserId() {
        Claims tokenClaims = getTokenClaims();
        if (tokenClaims == null) return null;
        return tokenClaims.getId();
    }

    public static Claims getTokenClaims() {
        HttpServletRequest request = getRequest();
        String auth = request.getHeader(HEADER_NAME);
        if (StringUtils.isBlank(auth)) {
            return null;
        }
        Key key = getSecretKey();
        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(auth);
            Claims claims = jws.getBody();
            return claims;
        } catch (Exception e) {
            return null;
        }
    }

    public static String createToken(String id) {
        Map<String, Object> claims = new HashMap<>();
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setClaims(claims).setId(id)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, getSecretKey());

        // 生成JWT
        String token = builder.compact();
        return token;
    }

    /**
     * 获取IP地址
     *
     * @return
     */
    public static String getIpAddress() {
        HttpServletRequest request = getRequest();
        String remoteAddr = request.getHeader("X-REAL-IP");
        if (StringUtils.isNotBlank(remoteAddr)) {
            return remoteAddr;
        }
        remoteAddr = request.getHeader("X-FORWARDED-FOR");
        if (StringUtils.isNotBlank(remoteAddr)) {
            return remoteAddr;
        }
        return request.getRemoteAddr();
    }
}
