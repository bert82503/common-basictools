package com.common.util;

import java.util.Collection;

/**
 * 集合工具辅助类。
 * <p/>
 * Miscellaneous collection utility methods.
 * Mainly for internal use within the framework.
 *
 * @author xingle
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Arjen Poutsma
 * @see org.apache.commons.collections4.CollectionUtils
 * @since 1.0
 */
public final class CollectionUtil {
    private CollectionUtil() {
    }

    //-----------------------------------------------------------------------

    /**
     * Return {@code true} if the supplied Collection is {@code null} or empty.
     * Otherwise, return {@code false}.
     * <p/>
     * Null-safe check if the specified Collection is empty.
     * <p/>
     * Null returns true.
     *
     * @param collection the Collection to check, may be null
     * @return {@code true} if the given Collection is null or empty
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

}
