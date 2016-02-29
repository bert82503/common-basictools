package com.common.request;

/**
 * 参数有效性校验工具辅助类。
 *
 * @author xingle
 * @since 1.0
 */
public abstract class ParamValidationUtil {

    public static boolean isNotValidId(long id) {
        return id <= 0L;
    }

}
