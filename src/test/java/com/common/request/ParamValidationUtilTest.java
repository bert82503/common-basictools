package com.common.request;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link ParamValidationUtil}.
 *
 * @author xingle
 * @since 1.0
 */
public class ParamValidationUtilTest {

    @Test(dataProvider = "isNotValidIdTestData")
    public void isNotValidId(long id, boolean expected) {
        boolean actual = ParamValidationUtil.isNotValidId(id);
        assertThat(actual).isEqualTo(expected);
    }

    @DataProvider(name = "isNotValidIdTestData")
    public static Object[][] isNotValidIdTestData() {
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
