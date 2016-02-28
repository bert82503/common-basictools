package com.common.util;

import com.google.common.collect.Lists;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link Comparators}.
 *
 * @author xingle
 * @since 1.0
 */
public class ComparatorsTest {

    @Test(dataProvider = "integerAscendComparatorTestData")
    public void integerAscendComparator(List<Integer> list, List<Integer> expectedList) {
        Collections.sort(list, Comparators.integerAscendComparator());
        assertThat(list).isEqualTo(expectedList);
    }

    @DataProvider(name = "integerAscendComparatorTestData")
    public static Object[][] integerAscendComparatorTestData() {
        return new Object[][] {
                // Lists.newArrayList
                { Lists.newArrayList(999, 666, 288, 266), Lists.newArrayList(266, 288, 666, 999) },
                { Lists.newArrayList(999, 288, 666, 266), Lists.newArrayList(266, 288, 666, 999) },
                { Lists.newArrayList(266, 288, 666, 999), Lists.newArrayList(266, 288, 666, 999) },

                // Arrays.asList
                { Arrays.asList(3, 7, 10, 23), Arrays.asList(3, 7, 10, 23) }, // 极端情况：原始数据就是升序
                { Arrays.asList(3, 23, 7, 10), Arrays.asList(3, 7, 10, 23) },
                { Arrays.asList(23, 10, 7, 3), Arrays.asList(3, 7, 10, 23) }, // 极端情况：原始数据就是降序
        };
    }

}
