package com.lotus.auth.service;

import com.lotus.auth.utils.AuthConstants;
import com.lotus.auth.entity.LoginUser;
import com.lotus.auth.utils.JwtUtils;
import com.lotus.auth.utils.TokenUtils;
import com.lotus.common.utils.IPUtils;
import com.lotus.common.utils.ServletUtils;
import com.lotus.common.utils.StringUtils;
import com.lotus.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @author changjiz
 */
@Service
public class TokenService {
    @Autowired
    private RedisService redisService;

    /**
     * 一分钟的毫秒
     */
    protected static final long MILLIS_MINUTE = 60 * 1000;

    /**
     * 缓存有效期，默认120（分钟）
     */
    @Value("${user.token.expire:120}")
    private long EXPIRE_TIME;

    /**
     * 缓存刷新时间，默认30（分钟） token失效时间低于该时间则需要刷新
     */
    @Value("${user.token.refresh:30}")
    private long MILLIS_MINUTE_TEN;

    /**
     * 用户登录Session前缀
     */
    private final static String USER_LOGIN_SESSION_KEY = "user:login:session:";

    /**
     * 创建令牌
     */
    public Map<String, Object> createToken(LoginUser loginUser) {
        String sessionId = UUID.randomUUID().toString();
        loginUser.setSessionId(sessionId);
        loginUser.setIpAddr(IPUtils.getIpAddr(ServletUtils.getRequest()));
        refreshSession(loginUser);

        // Jwt存储信息

        // 接口返回信息
        Map<String, Object> rspMap = new HashMap<String, Object>();
        rspMap.put(AuthConstants.AUTHORIZATION, JwtUtils.createToken(loginUser));
        rspMap.put("expires_in", EXPIRE_TIME);
        return rspMap;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser() {
        return getLoginUser(ServletUtils.getRequest());
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = TokenUtils.getToken(request);
        return getLoginUser(token);
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(String token) {
        LoginUser cacheUser = null;
        try {
            if (StringUtils.isNotEmpty(token)) {
                LoginUser jwtUser = JwtUtils.token2LoginUser(token);
                cacheUser = (LoginUser) redisService.getCacheObject(getSessionFix(jwtUser));
                return cacheUser;
            }
        } catch (Exception e) {
        }
        return cacheUser;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser) {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getSessionId())) {
            refreshSession(loginUser);
        }
    }

    /**
     * 删除用户缓存信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            LoginUser loginUser = JwtUtils.token2LoginUser(token);
            redisService.deleteObject(getSessionFix(loginUser));
        }
    }

    /**
     * 验证令牌有效期，相差不足120分钟，自动刷新缓存
     *
     * @param loginUser
     */
    public void touch(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN * MILLIS_MINUTE) {
            refreshSession(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshSession(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + EXPIRE_TIME * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getSessionFix(loginUser);
        redisService.setCacheObject(userKey, loginUser, EXPIRE_TIME, TimeUnit.MINUTES);
    }

    private String getSessionFix(LoginUser loginUser) {
        return USER_LOGIN_SESSION_KEY + loginUser.getFixKey();
    }
}