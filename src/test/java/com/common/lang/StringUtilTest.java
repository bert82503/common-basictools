package com.common.lang;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Unit test of {@link StringUtil}.
 *
 * @author xingle
 * @since 2016年06月25日 14:47
 */
public class StringUtilTest {

    @Test(dataProvider = "isEmptyTestData")
    public void isEmpty(CharSequence cs, boolean expected) {
        boolean result = StringUtil.isEmpty(cs);
        assertThat(result).isEqualTo(expected);
    }

    @DataProvider(name = "isEmptyTestData")
    private static Object[][] isEmptyTestData() {
        return new Object[][]{
                {null, true},
                {"", true},
                {" ", false},
                {"abc", false},
                {"  alpha  ", false},
        };
    }

    @Test(dataProvider = "isBlankTestData")
    public void isBlank(CharSequence cs, boolean expected) {
        boolean result = StringUtil.isBlank(cs);
        assertThat(result).isEqualTo(expected);
    }

    @DataProvider(name = "isBlankTestData")
    private static Object[][] isBlankTestData() {
        return new Object[][]{
                {null, true},
                {"", true},
                {" ", true},
                {"abc", false},
                {"  alpha  ", false},
        };

    }
}
