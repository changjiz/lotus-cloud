package com.lotus.security.service;

import java.util.Set;

public interface PermissionQuery {

    /**
     * 查询用户权限，缓存中没有时会调用
     * @param userId
     * @param roleId
     * @param tenantId
     * @return
     */
    Set<String> query(Long userId, Long roleId, Long tenantId);
}
