package com.common.request;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for {@link ValidateUtil}.
 *
 * @since 1.0
 */
public class ValidateUtilTest {
    @Test(dataProvider = "isNotPositiveTestData")
    public void isNotPositive(Long value, boolean expected) {
        boolean actual = ValidateUtil.isNotPositive(value);
        assertThat(actual).isEqualTo(expected);
    }

    @DataProvider(name = "isNotPositiveTestData")
    public static Object[][] isNotPositiveTestData() {
        return new Object[][] {
                { Long.MIN_VALUE, true }, // 临界值
                { -23L, true },
                { -1L, true },
                { 0L, true }, // 边界
                { 1L, false }, // 边界
                { 10L, false },
                { Long.MAX_VALUE, false }, // 临界值
        };
    }
}
