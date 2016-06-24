package com.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for {@link CollUtil}.
 *
 * @author xingle
 * @since 1.0
 */
public class CollUtilTest {

    @Test(dataProvider = "isEmptyTestData")
    public <E> void isEmpty(Collection<E> collection, boolean expected) {
        boolean actual = CollUtil.isEmpty(collection);
        assertThat(actual).isEqualTo(expected);
    }

    @DataProvider(name = "isEmptyTestData")
    public static Object[][] isEmptyTestData() {
        return new Object[][] {
                { null, true },
                // List
                { Collections.emptyList(), true },
                { new ArrayList<String>(), true },
                { Lists.<Integer>newArrayList(), true },
                { new LinkedList<Long>(), true },
                { Lists.<Long>newLinkedList(), true },
                { Lists.<String>newCopyOnWriteArrayList(), true }, // 并发
                // Set
                { Collections.emptySet(), true },
                { new HashSet<Long>(), true },
                { Sets.<Integer>newHashSet(), true },
                { Sets.<String>newLinkedHashSet(), true },
                { Sets.<String>newConcurrentHashSet(), true },
                { new TreeSet<Integer>(), true },
                { Sets.<Long>newTreeSet(), true },
                { Sets.<Integer>newCopyOnWriteArraySet(), true },

                { Arrays.asList(1, 2, 3), false },
                { Lists.newArrayList("a", "b", "c"), false },
                { Sets.newHashSet(23L, 10L), false },
        };
    }

}
