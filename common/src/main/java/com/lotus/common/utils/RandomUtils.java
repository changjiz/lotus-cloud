package com.lotus.common.utils;

import com.lotus.common.exception.UtilException;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @program: common
 * @description
 * @author: changjiz
 * @create: 2021-12-17 10:24
 **/
public class RandomUtils {

    public static final String BASE_NUMBER = "0123456789";
    public static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";
    public static final String BASE_CHAR_NUMBER = "abcdefghijklmnopqrstuvwxyz0123456789";

    public RandomUtils() {
    }

    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }

    public static SecureRandom getSecureRandom() {
        try {
            return SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException var1) {
            throw new UtilException("RandomUtils getSecureRandom error");
        }
    }

    public static Random getRandom(boolean isSecure) {
        return (Random) (isSecure ? getSecureRandom() : getRandom());
    }

    public static boolean randomBoolean() {
        return 0 == randomInt(2);
    }

    public static int randomInt(int min, int max) {
        return getRandom().nextInt(min, max);
    }

    public static int randomInt() {
        return getRandom().nextInt();
    }

    public static int randomInt(int limit) {
        return getRandom().nextInt(limit);
    }

    public static long randomLong(long min, long max) {
        return getRandom().nextLong(min, max);
    }

    public static long randomLong() {
        return getRandom().nextLong();
    }

    public static long randomLong(long limit) {
        return getRandom().nextLong(limit);
    }

    public static double randomDouble(double min, double max) {
        return getRandom().nextDouble(min, max);
    }

    public static double randomDouble(double min, double max, int scale, RoundingMode roundingMode) {
        return NumberUtils.round(randomDouble(min, max), scale, roundingMode).doubleValue();
    }

    public static double randomDouble() {
        return getRandom().nextDouble();
    }

    public static double randomDouble(int scale, RoundingMode roundingMode) {
        return NumberUtils.round(randomDouble(), scale, roundingMode).doubleValue();
    }

    public static double randomDouble(double limit) {
        return getRandom().nextDouble(limit);
    }

    public static double randomDouble(double limit, int scale, RoundingMode roundingMode) {
        return NumberUtils.round(randomDouble(limit), scale, roundingMode).doubleValue();
    }

    public static BigDecimal randomBigDecimal() {
        return NumberUtils.toBigDecimal(getRandom().nextDouble());
    }

    public static BigDecimal randomBigDecimal(BigDecimal limit) {
        return NumberUtils.toBigDecimal(getRandom().nextDouble(limit.doubleValue()));
    }

    public static BigDecimal randomBigDecimal(BigDecimal min, BigDecimal max) {
        return NumberUtils.toBigDecimal(getRandom().nextDouble(min.doubleValue(), max.doubleValue()));
    }

    public static byte[] randomBytes(int length) {
        byte[] bytes = new byte[length];
        getRandom().nextBytes(bytes);
        return bytes;
    }

    public static <T> T randomEle(List<T> list) {
        return randomEle(list, list.size());
    }

    public static <T> T randomEle(List<T> list, int limit) {
        return list.get(randomInt(limit));
    }

    public static <T> T randomEle(T[] array) {
        return randomEle(array, array.length);
    }

    public static <T> T randomEle(T[] array, int limit) {
        return array[randomInt(limit)];
    }

    public static <T> List<T> randomEles(List<T> list, int count) {
        List<T> result = new ArrayList(count);
        int limit = list.size();

        while (result.size() < count) {
            result.add(randomEle(list, limit));
        }

        return result;
    }

    public static String randomString(int length) {
        return randomString("abcdefghijklmnopqrstuvwxyz0123456789", length);
    }

    public static String randomStringUpper(int length) {
        return randomString("abcdefghijklmnopqrstuvwxyz0123456789", length).toUpperCase();
    }

    public static String randomNumbers(int length) {
        return randomString("0123456789", length);
    }

    public static String randomString(String baseString, int length) {
        StringBuilder sb = new StringBuilder();
        if (length < 1) {
            length = 1;
        }

        int baseLength = baseString.length();

        for (int i = 0; i < length; ++i) {
            int number = getRandom().nextInt(baseLength);
            sb.append(baseString.charAt(number));
        }

        return sb.toString();
    }

    public static int randomNumber() {
        return randomChar("0123456789");
    }

    public static char randomChar() {
        return randomChar("abcdefghijklmnopqrstuvwxyz0123456789");
    }

    public static char randomChar(String baseString) {
        return baseString.charAt(getRandom().nextInt(baseString.length()));
    }

    public static Color randomColor() {
        Random random = getRandom();
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }


    /**
     * @deprecated
     */
    @Deprecated
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

}
