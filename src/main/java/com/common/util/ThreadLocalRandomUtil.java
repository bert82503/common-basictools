package com.common.util;

import org.apache.commons.lang3.Validate;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 多线程安全的随机数辅助类。
 *
 * @author dannong
 * @since 2016年11月29日 20:27
 * @see org.apache.commons.lang3.RandomUtils
 */
public class ThreadLocalRandomUtil {

    private ThreadLocalRandomUtil() {
        // prevent instantiate
    }

    /**
     * Returns a random integer within the specified range.
     *
     * @param minInclusive
     *            the smallest value that can be returned, must be non-negative
     * @param maxExclusive
     *            the upper bound (not included)
     * @throws IllegalArgumentException
     *             if {@code minInclusive >= maxExclusive} or if
     *             {@code minInclusive} is negative
     * @return the random integer
     * @see org.apache.commons.lang3.RandomUtils#nextInt(int, int)
     */
    public static int nextInt(int minInclusive, int maxExclusive) {
        Validate.isTrue(maxExclusive > minInclusive, "Min value must be smaller than max value");
        Validate.isTrue(minInclusive >= 0, "Both range values must be non-negative");

        // 多线程设计：更高效的随机数生成器的并发实现
        return ThreadLocalRandom.current().nextInt(minInclusive, maxExclusive);
    }
}
