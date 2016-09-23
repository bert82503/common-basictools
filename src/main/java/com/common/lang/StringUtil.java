package com.common.lang;

/**
 * Operations on {@link String} that are {@code null} safe.
 *
 * <p>字符串操作辅助类
 *
 * @author xingle
 * @see org.apache.commons.lang3.StringUtils
 * @since 2016年06月25日 14:17
 */
public final class StringUtil {
    private StringUtil() {
        throw new AssertionError("No com.common.lang.StringUtil instances for you!");
    }

    // Empty checks
    //-----------------------------------------------------------------------

    /**
     * Checks if a CharSequence is empty ("") or {@code null}.
     * 
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     * 
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the CharSequence.
     * That functionality is available in {@link #isBlank(CharSequence)}.
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     * @see org.apache.commons.lang3.StringUtils#isEmpty(CharSequence)
     * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * Checks if a CharSequence is whitespace, empty ("") or {@code null}.
     * 
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     * @see org.apache.commons.lang3.StringUtils#isBlank(CharSequence)
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
    public static boolean isBlank(CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return true;
        }
        for (int i = 0, csLen = cs.length(); i < csLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
