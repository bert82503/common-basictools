package com.common.cache;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 缓存辅助类。
 *
 * @author dannong
 * @since 2017年03月09日 10:25
 */
public final class CacheUtils {
    private CacheUtils() {
    }

    public static String toCacheKey(Object... keyElements) {
        if (ArrayUtils.isEmpty(keyElements)) {
            throw new IllegalArgumentException("keyElements must be not empty");
        }

        StringBuilder sb = new StringBuilder(50);
        for (int i = 0, iMax = keyElements.length - 1; ; i++) {
            sb.append(String.valueOf(keyElements[i]));
            if (i == iMax) {
                return sb.toString();
            }
            sb.append(':');
        }
    }

}
