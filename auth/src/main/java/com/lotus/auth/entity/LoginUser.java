package com.lotus.auth.entity;

import com.lotus.common.utils.LongUtils;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = -1L;

    private String sessionId;

    private Long id;

    private Long roleId;

    private Long tenantId;

    private int deviceType;

    private int languageNum;

    private String ipAddr;

    private long loginTime;

    private long expireTime;

    public String getFixKey() {
        String fixKey = this.deviceType + ":" + this.id.toString();
        return LongUtils.isEmpty(this.tenantId) ? fixKey : fixKey + ":" + this.getTenantId().toString();
    }
}
