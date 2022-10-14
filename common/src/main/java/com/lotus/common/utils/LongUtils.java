package com.lotus.common.utils;

/**
 * @program: common
 * @description:
 * @author: changjiz
 * @create: 2019-09-21 18:08
 **/
public class LongUtils {
    public static boolean isEmpty(Long lo) {
        return null == lo || 0L == lo;
    }

    public static boolean isNotEmpty(Long lo) {
        return !isEmpty(lo);
    }
}
