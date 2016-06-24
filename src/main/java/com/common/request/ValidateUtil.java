package com.common.request;

/**
 * 参数有效性校验工具辅助类。
 *
 * @author xingle
 * @since 1.0
 */
public abstract class ValidateUtil {

    /**
     * Return {@code true} if the supplied Long is {@code null} or less than 0.
     * Otherwise, return {@code false}.
     * <p>
     * Null-safe check if the specified Long is empty.
     * <p>
     * Null returns true.
     *
     * @param value the Long to check, may be null
     * @return {@code true} if the given Long is null or less than 0
     */
    public static boolean isNotPositive(Long value) {
        return value == null || value <= 0L;
    }

}
