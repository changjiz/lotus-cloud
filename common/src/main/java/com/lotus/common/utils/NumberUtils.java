package com.lotus.common.utils;

import com.lotus.common.exception.UtilException;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

/**
 * @program: common
 * @description
 * @author: changjiz
 * @create: 2021-12-17 10:31
 **/
public class NumberUtils {

    private static final int DEFAUT_DIV_SCALE = 10;

    public NumberUtils() {
    }

    public static double add(float v1, float v2) {
        return add(Float.toString(v1), Float.toString(v2)).doubleValue();
    }

    public static double add(float v1, double v2) {
        return add(Float.toString(v1), Double.toString(v2)).doubleValue();
    }

    public static double add(double v1, float v2) {
        return add(Double.toString(v1), Float.toString(v2)).doubleValue();
    }

    public static double add(double v1, double v2) {
        return add(Double.toString(v1), Double.toString(v2)).doubleValue();
    }

    public static double add(Double v1, Double v2) {
        return add((Number) v1, (Number) v2).doubleValue();
    }

    public static BigDecimal add(Number v1, Number v2) {
        return add(v1, v2);
    }

    public static BigDecimal add(Number... values) {
        if (null == values || 0 == values.length) {
            return BigDecimal.ZERO;
        } else {
            Number value = values[0];
            BigDecimal result = new BigDecimal(null == value ? "0" : value.toString());

            for (int i = 1; i < values.length; ++i) {
                value = values[i];
                if (null != value) {
                    result = result.add(new BigDecimal(value.toString()));
                }
            }

            return result;
        }
    }

    public static BigDecimal add(String... values) {
        if (null == values || 0 == values.length) {
            return BigDecimal.ZERO;
        } else {
            String value = values[0];
            BigDecimal result = new BigDecimal(null == value ? "0" : value);

            for (int i = 1; i < values.length; ++i) {
                value = values[i];
                if (null != value) {
                    result = result.add(new BigDecimal(value));
                }
            }

            return result;
        }
    }

    public static BigDecimal add(BigDecimal... values) {
        if (null == values || 0 == values.length) {
            return BigDecimal.ZERO;
        } else {
            BigDecimal value = values[0];
            BigDecimal result = null == value ? BigDecimal.ZERO : value;

            for (int i = 1; i < values.length; ++i) {
                value = values[i];
                if (null != value) {
                    result = result.add(value);
                }
            }

            return result;
        }
    }

    public static double sub(float v1, float v2) {
        return sub(Float.toString(v1), Float.toString(v2)).doubleValue();
    }

    public static double sub(float v1, double v2) {
        return sub(Float.toString(v1), Double.toString(v2)).doubleValue();
    }

    public static double sub(double v1, float v2) {
        return sub(Double.toString(v1), Float.toString(v2)).doubleValue();
    }

    public static double sub(double v1, double v2) {
        return sub(Double.toString(v1), Double.toString(v2)).doubleValue();
    }

    public static double sub(Double v1, Double v2) {
        return sub((Number) v1, (Number) v2).doubleValue();
    }

    public static BigDecimal sub(Number v1, Number v2) {
        return sub(v1, v2);
    }

    public static BigDecimal sub(Number... values) {
        if (null == values || 0 == values.length) {
            return BigDecimal.ZERO;
        } else {
            Number value = values[0];
            BigDecimal result = new BigDecimal(null == value ? "0" : value.toString());

            for (int i = 1; i < values.length; ++i) {
                value = values[i];
                if (null != value) {
                    result = result.subtract(new BigDecimal(value.toString()));
                }
            }

            return result;
        }
    }

    public static BigDecimal sub(String... values) {
        if (null == values || 0 == values.length) {
            return BigDecimal.ZERO;
        } else {
            String value = values[0];
            BigDecimal result = new BigDecimal(null == value ? "0" : value);

            for (int i = 1; i < values.length; ++i) {
                value = values[i];
                if (null != value) {
                    result = result.subtract(new BigDecimal(value));
                }
            }

            return result;
        }
    }

    public static BigDecimal sub(BigDecimal... values) {
        if (null == values || 0 == values.length) {
            return BigDecimal.ZERO;
        } else {
            BigDecimal value = values[0];
            BigDecimal result = null == value ? BigDecimal.ZERO : value;

            for (int i = 1; i < values.length; ++i) {
                value = values[i];
                if (null != value) {
                    result = result.subtract(value);
                }
            }

            return result;
        }
    }

    public static double mul(float v1, float v2) {
        return mul(Float.toString(v1), Float.toString(v2)).doubleValue();
    }

    public static double mul(float v1, double v2) {
        return mul(Float.toString(v1), Double.toString(v2)).doubleValue();
    }

    public static double mul(double v1, float v2) {
        return mul(Double.toString(v1), Float.toString(v2)).doubleValue();
    }

    public static double mul(double v1, double v2) {
        return mul(Double.toString(v1), Double.toString(v2)).doubleValue();
    }

    public static double mul(Double v1, Double v2) {
        return mul((Number) v1, (Number) v2).doubleValue();
    }

    public static BigDecimal mul(Number v1, Number v2) {
        return mul(v1, v2);
    }

    public static BigDecimal mul(Number... values) {
        if (null == values || 0 == values.length) {
            return BigDecimal.ZERO;
        } else {
            Number value = values[0];
            BigDecimal result = new BigDecimal(null == value ? "0" : value.toString());

            for (int i = 1; i < values.length; ++i) {
                value = values[i];
                if (null != value) {
                    result = result.multiply(new BigDecimal(value.toString()));
                }
            }

            return result;
        }
    }

    public static BigDecimal mul(String v1, String v2) {
        return mul((Number) (new BigDecimal(v1)), (Number) (new BigDecimal(v2)));
    }

    public static BigDecimal mul(String... values) {
        if (null == values || 0 == values.length) {
            return BigDecimal.ZERO;
        } else {
            String value = values[0];
            BigDecimal result = new BigDecimal(null == value ? "0" : value);

            for (int i = 1; i < values.length; ++i) {
                value = values[i];
                if (null != value) {
                    result = result.multiply(new BigDecimal(value));
                }
            }

            return result;
        }
    }

    public static BigDecimal mul(BigDecimal... values) {
        if (null == values || 0 == values.length) {
            return BigDecimal.ZERO;
        } else {
            BigDecimal value = values[0];
            BigDecimal result = null == value ? BigDecimal.ZERO : value;

            for (int i = 1; i < values.length; ++i) {
                value = values[i];
                if (null != value) {
                    result = result.multiply(value);
                }
            }

            return result;
        }
    }

    public static double div(float v1, float v2) {
        return div(v1, v2, 10);
    }

    public static double div(float v1, double v2) {
        return div(v1, v2, 10);
    }

    public static double div(double v1, float v2) {
        return div(v1, v2, 10);
    }

    public static double div(double v1, double v2) {
        return div(v1, v2, 10);
    }

    public static double div(Double v1, Double v2) {
        return div((Double) v1, (Double) v2, 10);
    }

    public static BigDecimal div(Number v1, Number v2) {
        return div((Number) v1, (Number) v2, 10);
    }

    public static BigDecimal div(String v1, String v2) {
        return div((String) v1, (String) v2, 10);
    }

    public static double div(float v1, float v2, int scale) {
        return div(v1, v2, scale, RoundingMode.HALF_UP);
    }

    public static double div(float v1, double v2, int scale) {
        return div(v1, v2, scale, RoundingMode.HALF_UP);
    }

    public static double div(double v1, float v2, int scale) {
        return div(v1, v2, scale, RoundingMode.HALF_UP);
    }

    public static double div(double v1, double v2, int scale) {
        return div(v1, v2, scale, RoundingMode.HALF_UP);
    }

    public static double div(Double v1, Double v2, int scale) {
        return div(v1, v2, scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal div(Number v1, Number v2, int scale) {
        return div(v1, v2, scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal div(String v1, String v2, int scale) {
        return div(v1, v2, scale, RoundingMode.HALF_UP);
    }

    public static double div(float v1, float v2, int scale, RoundingMode roundingMode) {
        return div(Float.toString(v1), Float.toString(v2), scale, roundingMode).doubleValue();
    }

    public static double div(float v1, double v2, int scale, RoundingMode roundingMode) {
        return div(Float.toString(v1), Double.toString(v2), scale, roundingMode).doubleValue();
    }

    public static double div(double v1, float v2, int scale, RoundingMode roundingMode) {
        return div(Double.toString(v1), Float.toString(v2), scale, roundingMode).doubleValue();
    }

    public static double div(double v1, double v2, int scale, RoundingMode roundingMode) {
        return div(Double.toString(v1), Double.toString(v2), scale, roundingMode).doubleValue();
    }

    public static double div(Double v1, Double v2, int scale, RoundingMode roundingMode) {
        return div((Number) v1, (Number) v2, scale, roundingMode).doubleValue();
    }

    public static BigDecimal div(Number v1, Number v2, int scale, RoundingMode roundingMode) {
        return div(v1.toString(), v2.toString(), scale, roundingMode);
    }

    public static BigDecimal div(String v1, String v2, int scale, RoundingMode roundingMode) {
        return div(new BigDecimal(v1), new BigDecimal(v2), scale, roundingMode);
    }

    public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale, RoundingMode roundingMode) {
        Assert.notNull(v2, "Divisor must be not null !");
        if (null == v1) {
            return BigDecimal.ZERO;
        } else {
            if (scale < 0) {
                scale = -scale;
            }

            return v1.divide(v2, scale, roundingMode);
        }
    }

    public static BigDecimal round(double v, int scale) {
        return round(v, scale, RoundingMode.HALF_UP);
    }

    public static String roundStr(double v, int scale) {
        return round(v, scale).toString();
    }

    public static BigDecimal round(String numberStr, int scale) {
        return round(numberStr, scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal round(BigDecimal number, int scale) {
        return round(number, scale, RoundingMode.HALF_UP);
    }

    public static String roundStr(String numberStr, int scale) {
        return round(numberStr, scale).toString();
    }

    public static BigDecimal round(double v, int scale, RoundingMode roundingMode) {
        return round(Double.toString(v), scale, roundingMode);
    }

    public static String roundStr(double v, int scale, RoundingMode roundingMode) {
        return round(v, scale, roundingMode).toString();
    }

    public static BigDecimal round(String numberStr, int scale, RoundingMode roundingMode) {
        if (StringUtils.isEmpty(numberStr)) {
            throw new IllegalArgumentException("[Assertion failed] - this String argument must have length; it must not be null or empty");
        }
        if (scale < 0) {
            scale = 0;
        }

        return round(toBigDecimal(numberStr), scale, roundingMode);
    }

    public static BigDecimal round(BigDecimal number, int scale, RoundingMode roundingMode) {
        if (null == number) {
            number = BigDecimal.ZERO;
        }

        if (scale < 0) {
            scale = 0;
        }

        if (null == roundingMode) {
            roundingMode = RoundingMode.HALF_UP;
        }

        return number.setScale(scale, roundingMode);
    }

    public static String roundStr(String numberStr, int scale, RoundingMode roundingMode) {
        return round(numberStr, scale, roundingMode).toString();
    }

    public static BigDecimal roundHalfEven(Number number, int scale) {
        return roundHalfEven(toBigDecimal(number), scale);
    }

    public static BigDecimal roundHalfEven(BigDecimal value, int scale) {
        return round(value, scale, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal roundDown(Number number, int scale) {
        return roundDown(toBigDecimal(number), scale);
    }

    public static BigDecimal roundDown(BigDecimal value, int scale) {
        return round(value, scale, RoundingMode.DOWN);
    }

    public static String decimalFormat(String pattern, double value) {
        return (new DecimalFormat(pattern)).format(value);
    }

    public static String decimalFormat(String pattern, long value) {
        return (new DecimalFormat(pattern)).format(value);
    }

    public static String decimalFormatMoney(double value) {
        return decimalFormat(",##0.00", value);
    }

    public static String formatPercent(double number, int scale) {
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMaximumFractionDigits(scale);
        return format.format(number);
    }

    public static boolean isNumber(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else {
            char[] chars = str.toCharArray();
            int sz = chars.length;
            boolean hasExp = false;
            boolean hasDecPoint = false;
            boolean allowSigns = false;
            boolean foundDigit = false;
            int start = chars[0] == '-' ? 1 : 0;
            int i;
            if (sz > start + 1 && chars[start] == '0' && chars[start + 1] == 'x') {
                i = start + 2;
                if (i == sz) {
                    return false;
                } else {
                    while (i < chars.length) {
                        if ((chars[i] < '0' || chars[i] > '9') && (chars[i] < 'a' || chars[i] > 'f') && (chars[i] < 'A' || chars[i] > 'F')) {
                            return false;
                        }

                        ++i;
                    }

                    return true;
                }
            } else {
                --sz;

                for (i = start; i < sz || i < sz + 1 && allowSigns && !foundDigit; ++i) {
                    if (chars[i] >= '0' && chars[i] <= '9') {
                        foundDigit = true;
                        allowSigns = false;
                    } else if (chars[i] == '.') {
                        if (hasDecPoint || hasExp) {
                            return false;
                        }

                        hasDecPoint = true;
                    } else if (chars[i] != 'e' && chars[i] != 'E') {
                        if (chars[i] != '+' && chars[i] != '-') {
                            return false;
                        }

                        if (!allowSigns) {
                            return false;
                        }

                        allowSigns = false;
                        foundDigit = false;
                    } else {
                        if (hasExp) {
                            return false;
                        }

                        if (!foundDigit) {
                            return false;
                        }

                        hasExp = true;
                        allowSigns = true;
                    }
                }

                if (i < chars.length) {
                    if (chars[i] >= '0' && chars[i] <= '9') {
                        return true;
                    } else if (chars[i] != 'e' && chars[i] != 'E') {
                        if (chars[i] == '.') {
                            return !hasDecPoint && !hasExp ? foundDigit : false;
                        } else if (allowSigns || chars[i] != 'd' && chars[i] != 'D' && chars[i] != 'f' && chars[i] != 'F') {
                            if (chars[i] != 'l' && chars[i] != 'L') {
                                return false;
                            } else {
                                return foundDigit && !hasExp;
                            }
                        } else {
                            return foundDigit;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return !allowSigns && foundDigit;
                }
            }
        }
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }

    public static boolean isLong(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return s.contains(".");
        } catch (NumberFormatException var2) {
            return false;
        }
    }

    public static boolean isPrimes(int n) {
        Assert.isTrue(n > 1, "The number must be > 1");

        for (int i = 2; (double) i <= Math.sqrt((double) n); ++i) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static int[] generateRandomNumber(int begin, int end, int size) {
        if (begin > end) {
            int temp = begin;
            begin = end;
            end = temp;
        }

        if (end - begin < size) {
            throw new UtilException("Size is larger than range between begin and end!");
        } else {
            int[] seed = new int[end - begin];

            for (int i = begin; i < end; seed[i - begin] = i++) {
            }

            int[] ranArr = new int[size];
            Random ran = new Random();

            for (int i = 0; i < size; ++i) {
                int j = ran.nextInt(seed.length - i);
                ranArr[i] = seed[j];
                seed[j] = seed[seed.length - 1 - i];
            }

            return ranArr;
        }
    }

    public static Integer[] generateBySet(int begin, int end, int size) {
        if (begin > end) {
            int temp = begin;
            begin = end;
            end = temp;
        }

        if (end - begin < size) {
            throw new UtilException("Size is larger than range between begin and end!");
        } else {
            Random ran = new Random();
            HashSet set = new HashSet();

            while (set.size() < size) {
                set.add(begin + ran.nextInt(end - begin));
            }

            Integer[] ranArr = (Integer[]) set.toArray(new Integer[size]);
            return ranArr;
        }
    }

    public static int[] range(int stop) {
        return range(0, stop);
    }

    public static int[] range(int start, int stop) {
        return range(start, stop, 1);
    }

    public static int[] range(int start, int stop, int step) {
        if (start < stop) {
            step = Math.abs(step);
        } else {
            if (start <= stop) {
                return new int[]{start};
            }

            step = -Math.abs(step);
        }

        int size = Math.abs((stop - start) / step) + 1;
        int[] values = new int[size];
        int index = 0;
        int i = start;

        while (true) {
            if (step > 0) {
                if (i > stop) {
                    break;
                }
            } else if (i < stop) {
                break;
            }

            values[index] = i;
            ++index;
            i += step;
        }

        return values;
    }

    public static Collection<Integer> appendRange(int start, int stop, Collection<Integer> values) {
        return appendRange(start, stop, 1, values);
    }

    public static Collection<Integer> appendRange(int start, int stop, int step, Collection<Integer> values) {
        if (start < stop) {
            step = Math.abs(step);
        } else {
            if (start <= stop) {
                values.add(start);
                return values;
            }

            step = -Math.abs(step);
        }

        int i = start;

        while (true) {
            if (step > 0) {
                if (i > stop) {
                    break;
                }
            } else if (i < stop) {
                break;
            }

            values.add(i);
            i += step;
        }

        return values;
    }

    public static long factorial(long start, long end) {
        if (start < end) {
            return 0L;
        } else {
            return start == end ? 1L : start * factorial(start - 1L, end);
        }
    }

    public static long factorial(long n) {
        return factorial(n, 1L);
    }

    public static long sqrt(long x) {
        long y = 0L;

        for (long b = 4611686018427387904L; b > 0L; b >>= 2) {
            if (x >= y + b) {
                x -= y + b;
                y >>= 1;
                y += b;
            } else {
                y >>= 1;
            }
        }

        return y;
    }

    public static int processMultiple(int selectNum, int minNum) {
        int result = mathSubnode(selectNum, minNum) / mathNode(selectNum - minNum);
        return result;
    }

    public static int divisor(int m, int n) {
        while (m % n != 0) {
            int temp = m % n;
            m = n;
            n = temp;
        }

        return n;
    }

    public static int multiple(int m, int n) {
        return m * n / divisor(m, n);
    }

    public static String getBinaryStr(Number number) {
        if (number instanceof Long) {
            return Long.toBinaryString((Long) number);
        } else {
            return number instanceof Integer ? Integer.toBinaryString((Integer) number) : Long.toBinaryString(number.longValue());
        }
    }

    public static int binaryToInt(String binaryStr) {
        return Integer.parseInt(binaryStr, 2);
    }

    public static long binaryToLong(String binaryStr) {
        return Long.parseLong(binaryStr, 2);
    }

    public static int compare(char x, char y) {
        return x - y;
    }

    public static int compare(double x, double y) {
        return Double.compare(x, y);
    }

    public static int compare(int x, int y) {
        if (x == y) {
            return 0;
        } else {
            return x < y ? -1 : 1;
        }
    }

    public static int compare(long x, long y) {
        if (x == y) {
            return 0;
        } else {
            return x < y ? -1 : 1;
        }
    }

    public static int compare(short x, short y) {
        if (x == y) {
            return 0;
        } else {
            return x < y ? -1 : 1;
        }
    }

    public static int compare(byte x, byte y) {
        return x - y;
    }

    public static boolean isGreater(BigDecimal bigNum1, BigDecimal bigNum2) {
        Assert.notNull(bigNum1);
        Assert.notNull(bigNum2);
        return bigNum1.compareTo(bigNum2) > 0;
    }

    public static boolean isGreaterOrEqual(BigDecimal bigNum1, BigDecimal bigNum2) {
        Assert.notNull(bigNum1);
        Assert.notNull(bigNum2);
        return bigNum1.compareTo(bigNum2) >= 0;
    }

    public static boolean isLess(BigDecimal bigNum1, BigDecimal bigNum2) {
        Assert.notNull(bigNum1);
        Assert.notNull(bigNum2);
        return bigNum1.compareTo(bigNum2) < 0;
    }

    public static boolean isLessOrEqual(BigDecimal bigNum1, BigDecimal bigNum2) {
        Assert.notNull(bigNum1);
        Assert.notNull(bigNum2);
        return bigNum1.compareTo(bigNum2) <= 0;
    }

    public static boolean equals(BigDecimal bigNum1, BigDecimal bigNum2) {
        Assert.notNull(bigNum1);
        Assert.notNull(bigNum2);
        return 0 == bigNum1.compareTo(bigNum2);
    }

    public static BigDecimal toBigDecimal(Number number) {
        return null == number ? BigDecimal.ZERO : toBigDecimal(number.toString());
    }

    public static BigDecimal toBigDecimal(String number) {
        return null == number ? BigDecimal.ZERO : new BigDecimal(number);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static boolean isBlankChar(char c) {
        return isBlankChar((int) c);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static boolean isBlankChar(int c) {
        return Character.isWhitespace(c) || Character.isSpaceChar(c) || c == 65279 || c == 8234;
    }

    public static int count(int total, int part) {
        return total % part == 0 ? total / part : total / part + 1;
    }

    public static BigDecimal null2Zero(BigDecimal decimal) {
        return decimal == null ? BigDecimal.ZERO : decimal;
    }

    public static int zero2One(int value) {
        return 0 == value ? 1 : value;
    }

    public static boolean isBeside(long number1, long number2) {
        return Math.abs(number1 - number2) == 1L;
    }

    public static boolean isBeside(int number1, int number2) {
        return Math.abs(number1 - number2) == 1;
    }


    public static BigDecimal pow(Number number, int n) {
        return pow(toBigDecimal(number), n);
    }

    public static BigDecimal pow(BigDecimal number, int n) {
        return number.pow(n);
    }


    public static byte[] toBytes(int value) {
        byte[] result = new byte[]{(byte) (value >> 24), (byte) (value >> 16), (byte) (value >> 8), (byte) value};
        return result;
    }

    public static int toInt(byte[] bytes) {
        return (bytes[0] & 255) << 24 | (bytes[1] & 255) << 16 | (bytes[2] & 255) << 8 | bytes[3] & 255;
    }

    public static byte[] toUnsignedByteArray(BigInteger value) {
        byte[] bytes = value.toByteArray();
        if (bytes[0] == 0) {
            byte[] tmp = new byte[bytes.length - 1];
            System.arraycopy(bytes, 1, tmp, 0, tmp.length);
            return tmp;
        } else {
            return bytes;
        }
    }

    public static byte[] toUnsignedByteArray(int length, BigInteger value) {
        byte[] bytes = value.toByteArray();
        if (bytes.length == length) {
            return bytes;
        } else {
            int start = bytes[0] == 0 ? 1 : 0;
            int count = bytes.length - start;
            if (count > length) {
                throw new IllegalArgumentException("standard length exceeded for value");
            } else {
                byte[] tmp = new byte[length];
                System.arraycopy(bytes, start, tmp, tmp.length - count, count);
                return tmp;
            }
        }
    }

    public static BigInteger fromUnsignedByteArray(byte[] buf) {
        return new BigInteger(1, buf);
    }

    public static BigInteger fromUnsignedByteArray(byte[] buf, int off, int length) {
        byte[] mag = buf;
        if (off != 0 || length != buf.length) {
            mag = new byte[length];
            System.arraycopy(buf, off, mag, 0, length);
        }

        return new BigInteger(1, mag);
    }

    private static int mathSubnode(int selectNum, int minNum) {
        return selectNum == minNum ? 1 : selectNum * mathSubnode(selectNum - 1, minNum);
    }

    private static int mathNode(int selectNum) {
        return selectNum == 0 ? 1 : selectNum * mathNode(selectNum - 1);
    }

}
