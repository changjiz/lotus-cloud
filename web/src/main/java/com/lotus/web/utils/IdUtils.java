package com.lotus.web.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.lotus.common.utils.Constants;

/**
 * @program: framework
 * @description:
 * @author: changjiz 单例
 * @create: 2019-08-09 17:10
 **/
public class IdUtils {

    //静态内部类
    private static class LazyHolder {
        // snowflake ID生成器
        private static final Snowflake SNOWFLAKE = new Snowflake(Constants.APPLY_ID, 1);
        //盐，用于混交加密,需16位
        private static final String SLAT = "Lotus_zcj-*#@123";
        private static final SymmetricCrypto AES = new SymmetricCrypto(SymmetricAlgorithm.AES, SLAT.getBytes());
    }

    //私有构造函数
    private IdUtils() {
    }

    //提供实例的调用方法
    public static final Snowflake getSnowflake() {
        return LazyHolder.SNOWFLAKE;
    }

    public static final SymmetricCrypto getAes() {
        return LazyHolder.AES;
    }

    public static Long get() {
        return getSnowflake().nextId();
    }

    /**
     * 加密
     *
     * @return
     */
    public static String encryptionStr(Long id) {
        return getAes().encryptHex(id.toString());
    }

    /**
     * 解密
     */
    public static Long decryptLong(String securityId) {
        return Long.valueOf(getAes().decryptStr(securityId, CharsetUtil.CHARSET_UTF_8));
    }
}
