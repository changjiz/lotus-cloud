package com.lotus.common.utils;

public class MobileUtils {

    public static boolean checkMobileValidation(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        String mobileRegex = "^1(3|4|5|6|7|8|9)\\d{9}$";
        if (mobile.matches(mobileRegex)) {
            return true;
        } else {
            return false;
        }
    }
}
