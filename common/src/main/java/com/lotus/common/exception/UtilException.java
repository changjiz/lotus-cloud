package com.lotus.common.exception;

/**
 * @program: common
 * @description
 * @author: changjiz
 * @create: 2021-12-17 10:55
 **/
public class UtilException extends RuntimeException {
    private static final long serialVersionUID = 8247610319171014183L;


    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
