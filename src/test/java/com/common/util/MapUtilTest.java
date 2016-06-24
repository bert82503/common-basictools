package com.common.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Unit test of {@link MapUtil}.
 *
 * @author xingle
 * @since 2016年06月24日 14:20
 */
public class MapUtilTest {

    @Test(dataProvider = "isEmptyTestData")
    public void isEmpty(Map<?, ?> map, boolean expected) {
        boolean result = MapUtil.isEmpty(map);
        assertThat(result).isEqualTo(expected);
    }

    @DataProvider(name = "isEmptyTestData")
    private static Object[][] isEmptyTestData() {
        Map<String, String> map = Maps.newHashMap();
        map.put("hash", "map");

        return new Object[][]{
                {null, true},
                {Collections.emptyMap(), true},
                {Maps.newHashMap(), true},
                {map, false},
        };
    }
}
