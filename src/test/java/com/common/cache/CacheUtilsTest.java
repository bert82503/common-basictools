package com.common.cache;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.ArrayUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Unit test of {@link CacheUtils}.
 *
 * @since 2017-03-09
 */
public class CacheUtilsTest {
    @Test(dataProvider = "toCacheKeyTestData")
    public void toCacheKey(Object[] keyElements, String expected) {
        String cacheKey = CacheUtils.toCacheKey(keyElements);
        assertThat(cacheKey).isEqualTo(expected);
    }

    @DataProvider(name = "toCacheKeyTestData")
    private static Object[][] toCacheKeyTestData() {
        return new Object[][]{
                {new Object[]{"user", 24}, "user:24"},
                {new Object[]{"card", "2383746338328"}, "card:2383746338328"},
                {new Object[]{"tweet", 83723L, "comment-to"}, "tweet:83723:comment-to"},
        };
    }

    @Test(dataProvider = "toCacheKeyExceptionTestData",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "keyElements must be not empty")
    public void toCacheKeyException(Object[] keyElements) {
        CacheUtils.toCacheKey(keyElements);
    }

    @DataProvider(name = "toCacheKeyExceptionTestData")
    private static Object[][] toCacheKeyExceptionTestData() {
        return new Object[][]{
                {null},
                {ArrayUtils.EMPTY_OBJECT_ARRAY},
        };
    }
}
