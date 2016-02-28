package com.common.request;

/**
 * 参数有效性校验工具辅助类。
 *
 * @author xingle
 * @since 1.0
 */
public final class ParamUtil {

    private ParamUtil() {}

    public static boolean isNotValidId(long id) {
        return id <= 0L;
    }

}
