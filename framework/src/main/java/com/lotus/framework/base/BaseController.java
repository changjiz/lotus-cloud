package com.lotus.framework.base;

import com.lotus.web.utils.IdUtils;

/**
 * @program: framework
 * @description
 * @author: changjiz
 * @create: 2021-10-22 17:05
 **/
public abstract class BaseController {

    protected Long getUserId() {
        return BaseUtils.getUserId();
    }

    protected Long getTenantId() {
        return BaseUtils.getTenantId();
    }

    protected Long getRoleId() {
        return BaseUtils.getRoleId();
    }

    protected int getDeviceType() {
        return BaseUtils.getDeviceType();
    }

    protected Long decryptLong(String securityId) {
        return IdUtils.decryptLong(securityId);
    }
}
