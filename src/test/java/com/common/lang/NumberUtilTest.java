package com.common.lang;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test of {@link NumberUtil}.
 *
 * @since 2016-09-14
 */
public class NumberUtilTest {
    @Test(dataProvider = "intValueTestData")
    public void intValue(Integer value, int expected) {
        int result = NumberUtil.intValue(value, expected);
        assertThat(result).isEqualTo(expected);
    }

    @DataProvider(name = "intValueTestData")
    private static Object[][] intValueTestData() {
        return new Object[][]{
                {null, 0},

                {0, 0},
                {1, 1},
                {-1, -1},
                {Integer.MIN_VALUE, Integer.MIN_VALUE},
                {Integer.MAX_VALUE, Integer.MAX_VALUE},
        };
    }

    @Test(dataProvider = "longValueTestData")
    public void longValue(Long value, long expected) {
        long result = NumberUtil.longValue(value, expected);
        assertThat(result).isEqualTo(expected);
    }

    @DataProvider(name = "longValueTestData")
    private static Object[][] longValueTestData() {
        return new Object[][]{
                {null, 0L},

                {0L, 0L},
                {1L, 1L},
                {-1L, -1L},
                {Long.MAX_VALUE, Long.MAX_VALUE},
                {Long.MIN_VALUE, Long.MIN_VALUE},
        };
    }
}
