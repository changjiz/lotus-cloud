package com.lotus.auth.utils;

import com.lotus.auth.entity.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Jwt工具类
 *
 * @author changjiz
 */
public class JwtUtils {
    public static String SECRET = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 从数据声明生成令牌
     *
     * @param loginUser 数据声明
     * @return 令牌
     */
    public static String createToken(LoginUser loginUser) {
        Map<String, Object> claimsMap = new HashMap<String, Object>();
        claimsMap.put(AuthConstants.SESSION_ID, loginUser.getSessionId());
        claimsMap.put(AuthConstants.DEVICE_TYPE, loginUser.getDeviceType());
        claimsMap.put(AuthConstants.USER_ID, loginUser.getId());
        claimsMap.put(AuthConstants.TENANT_ID, loginUser.getTenantId());
        claimsMap.put(AuthConstants.ROLE_ID, loginUser.getRoleId());
        return Jwts.builder().setClaims(claimsMap).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    /**
     * 令牌转换成数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static LoginUser token2LoginUser(String token) {
        Claims claims = parseToken(token);
        if (null == claims) {
            return null;
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setSessionId(getSessionId(claims));
        loginUser.setDeviceType(getDeviceType(claims));
        loginUser.setId(getUserId(claims));
        loginUser.setTenantId(getTenantId(claims));
        loginUser.setRoleId(getRoleId(claims));
        return loginUser;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    /**
     * 根据身份信息获取键值
     *
     * @param claims 身份信息
     * @param key    键
     * @return 值
     */
    public static String getValue(Claims claims, String key) {
        Object value = claims.get(key);
        if (null == value) {
            return "";
        }
        if (value instanceof String) {
            return (String) value;
        }
        return value.toString();
    }

    /**
     * 根据令牌获取用户会话id
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static String getSessionId(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, AuthConstants.SESSION_ID);
    }

    /**
     * 根据令牌获取用户会话id
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    public static String getSessionId(Claims claims) {
        return getValue(claims, AuthConstants.SESSION_ID);
    }

    /**
     * 根据令牌获取设备类型
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static int getDeviceType(String token) {
        Claims claims = parseToken(token);
        return Integer.valueOf(getValue(claims, AuthConstants.DEVICE_TYPE));
    }

    /**
     * 根据身份信息获取设备类型
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    public static int getDeviceType(Claims claims) {
        return Integer.valueOf(getValue(claims, AuthConstants.DEVICE_TYPE));
    }

    /**
     * 根据令牌获取用户ID
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static Long getUserId(String token) {
        Claims claims = parseToken(token);
        return Long.valueOf(getValue(claims, AuthConstants.USER_ID));
    }

    /**
     * 根据身份信息获取用户ID
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    public static Long getUserId(Claims claims) {
        return Long.valueOf(getValue(claims, AuthConstants.USER_ID));
    }

    /**
     * 根据令牌获取角色ID
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static Long getRoleId(String token) {
        Claims claims = parseToken(token);
        return Long.valueOf(getValue(claims, AuthConstants.ROLE_ID));
    }

    /**
     * 根据身份信息获取角色ID
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    public static Long getRoleId(Claims claims) {
        return Long.valueOf(getValue(claims, AuthConstants.ROLE_ID));
    }

    /**
     * 根据令牌获取租户ID
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static Long getTenantId(String token) {
        Claims claims = parseToken(token);
        return Long.valueOf(getValue(claims, AuthConstants.TENANT_ID));
    }

    /**
     * 根据身份信息获取租户ID
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    public static Long getTenantId(Claims claims) {
        return Long.valueOf(getValue(claims, AuthConstants.TENANT_ID));
    }
}
