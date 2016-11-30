package com.common.util;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Test of {@link ThreadLocalRandomUtil}.
 *
 * @author dannong
 * @since 2016年11月29日 20:36
 */
public class ThreadLocalRandomUtilTest {

    @Test
    public void nextInt() {
        int random;
        for (int i = 0; i < 50; i++) {
            random = ThreadLocalRandomUtil.nextInt(0, 1);
            assertThat(random).isEqualTo(0);
        }

        int size = 10;
        for (int i = 0; i < 10000; i++) {
            random = ThreadLocalRandomUtil.nextInt(0, size);
            assertThat(random).isGreaterThanOrEqualTo(0);
            assertThat(random).isLessThan(size);
        }
    }
}
