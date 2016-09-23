package com.common.lang;

/**
 * Operations on {@link Number} and subclasses that are {@code null} safe.
 *
 * <p>数字操作辅助类
 *
 * @author xingle
 * @see org.apache.commons.lang3.math.NumberUtils
 * @since 2016年09月14日 11:05
 */
public final class NumberUtil {
    private NumberUtil() {
        throw new AssertionError("No com.common.lang.NumberUtil instances for you!");
    }


    // Number

    public static int intValue(Integer value, int defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static long longValue(Long value, long defaultValue) {
        return value == null ? defaultValue : value;
    }
}
