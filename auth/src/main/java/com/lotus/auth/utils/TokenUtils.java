package com.lotus.auth.utils;

import com.lotus.common.utils.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: auth
 * @description
 * @author: changjiz
 * @create: 2022-03-10 13:53
 **/
public class TokenUtils {

    /**
     * 获取请求token
     */
    public static String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(AuthConstants.AUTHORIZATION);
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(AuthConstants.PREFIX)) {
            token = token.replaceFirst(AuthConstants.PREFIX, StringUtils.EMPTY);
        }
        return token;
    }

    /**
     * 获取请求token
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader(AuthConstants.AUTHORIZATION);
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(AuthConstants.PREFIX)) {
            token = token.replaceFirst(AuthConstants.PREFIX, StringUtils.EMPTY);
        }
        return token;
    }
}
