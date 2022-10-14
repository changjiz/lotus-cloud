package com.lotus.security.service;

import com.lotus.auth.utils.AuthConstants;
import com.lotus.common.utils.ServletUtils;
import com.lotus.common.utils.StringUtils;
import com.lotus.redis.service.RedisService;
import com.lotus.security.exception.NotLoginException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @program: security
 * @description:
 * @author: changjiz
 * @create: 2022-10-09 14:24
 **/
@Service
@Slf4j
public class PermissionService {

    @Autowired
    private RedisService redisService;
    @Autowired(required = false)
    private PermissionQuery permissionQuery;
    /**
     * 一分钟的秒
     */
    protected static final long SECOND_MINUTE = 60;

    /**
     * 缓存有效期，默认120（分钟）
     */
    @Value("${user.token.expire:120}")
    private long EXPIRE_TIME;

    /**
     * 缓存刷新时间，默认30（分钟） token失效时间低于该时间则需要刷新
     */
    @Value("${user.token.refresh:30}")
    private long REFRESH_TIME;

    /**
     * 用户Session权限集合前缀
     */
    private final static String USER_SESSION_PERMISSIONS_KEY = "user:session:permissions:";


    /**
     * 获取用户权限集合
     *
     * @return 权限集合
     */
    public Set<String> getPermissions() {
        Set<String> permissions = null;
        try {
            permissions = redisService.sGet(getPermissionsFix());
            return permissions;
        } catch (Exception e) {
            log.error("getPermissions  ---> {}" + e.getMessage());
        }
        return new HashSet<>();
    }

    /**
     * 添加用户权限集合
     */
    public void addPermissions() {
        try {
            Set<String> permissions = permissionQuery();
            if (CollectionUtils.isNotEmpty(permissions)) {
                redisService.sSetAndTime(getPermissionsFix(), EXPIRE_TIME * SECOND_MINUTE, permissions.toArray());
            } else {
                refreshExpire();
            }
        } catch (Exception e) {
            log.error("addPermissions  ---> {}" + e.getMessage());
        }
    }

    private Set<String> permissionQuery() {
        String userId = null, roleId = null, tenantId = null;
        Long userIdL, roleIdL, tenantIdL;
        try {
            userId = ServletUtils.getRequest().getHeader(AuthConstants.USER_ID);
            roleId = ServletUtils.getRequest().getHeader(AuthConstants.ROLE_ID);
            tenantId = ServletUtils.getRequest().getHeader(AuthConstants.TENANT_ID);
            userIdL = Long.valueOf(userId);
            roleIdL = Long.valueOf(roleId);
            tenantIdL = StringUtils.isEmpty(tenantId) ? null : Long.valueOf(tenantId);
        } catch (Exception e) {
            log.error("permissionQuery  ---> {}  {}  {}", userId, roleId, tenantId);
            throw new NotLoginException("用户未登录，无法获取权限信息");
        }
        if (null == permissionQuery) {
            log.error("permissionQuery  ---> 未实现权限查询接口");
            return new HashSet<>();
        }
        return permissionQuery.query(userIdL, roleIdL, tenantIdL);
    }

    /**
     * 删除用户权限集合
     */
    public void clearPermissions() {
        try {
            redisService.deleteObject(getPermissionsFix());
        } catch (Exception e) {
            log.error("clearPermissions  ---> {}" + e.getMessage());
        }
    }

    /**
     * 验证有效期，相差不足120分钟，自动刷新缓存
     *
     * @param
     */
    public void touch() {
        Long expireTime = redisService.getExpire(getPermissionsFix());
        if (expireTime <= REFRESH_TIME * SECOND_MINUTE) {
            refreshExpire();
        }
    }

    /**
     * 刷新有效期
     */
    public void refreshExpire() {
        if (!redisService.expire(getPermissionsFix(), EXPIRE_TIME * SECOND_MINUTE)) {
            // 刷新失败手动重新插入
            Set<String> permissions = permissionQuery();
            if (CollectionUtils.isNotEmpty(permissions)) {
                redisService.sSetAndTime(getPermissionsFix(), EXPIRE_TIME * SECOND_MINUTE, permissions.toArray());
            } else {
                // 可能已经达到用户会话超时临界点/ 长时间调用非权限验证接口
                log.error("refreshExpire  ---> 用户刷新权限异常（长时间调用非权限验证接口）");
            }
        }
    }

    /**
     * @return redis key
     */
    private String getPermissionsFix() {
        String sessionId = ServletUtils.getRequest().getHeader(AuthConstants.SESSION_ID);
        if (StringUtils.isEmpty(sessionId)) {
            throw new NotLoginException("用户未登录，无法获取权限信息");
        }
        return USER_SESSION_PERMISSIONS_KEY + sessionId;
    }


}
