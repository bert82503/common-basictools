package com.common.time;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link DateTimeUtil}.
 *
 * @author xingle
 * @since 1.0
 */
public class DateTimeUtilTest {

    @Test(dataProvider = "constantValueTestData")
    public void constantValue(int constant, int expected) {
        assertThat(constant).isEqualTo(expected);
    }

    @DataProvider(name = "constantValueTestData")
    public static Object[][] constantValueTestData() {
        return new Object[][] {
                { DateTimeUtil.ONE_DAY_SECONDS, 86400 },
        };
    }

    /**
     * 请参考 {@link DateTimeUtil#currentTimeSeconds()}
     */
    @Test(dataProvider = "currentTimeSecondsTestData")
    public void currentTimeSeconds(long currentTimeMillis, int expectedCurrentTimeSeconds) {
        int currentTimeSeconds = (int) TimeUnit.MILLISECONDS.toSeconds(
                currentTimeMillis);
        assertThat(currentTimeSeconds).isEqualTo(expectedCurrentTimeSeconds);
    }

    @DataProvider(name = "currentTimeSecondsTestData")
    public static Object[][] currentTimeSecondsTestData() {
        return new Object[][] {
                { 1454564227493L, 1454564227 },
                { 1456668046300L, 1456668046 },
        };
    }

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    @Test(dataProvider = "toZeroTimeSecondsOfTodayTestData")
    public void toZeroTimeSecondsOfToday(int timeSeconds, String expected) {
        int zeroTimeSecondsOfToday = DateTimeUtil.toZeroTimeSecondsOfToday(
                timeSeconds);
        long zeroTimeMillisOfToday = TimeUnit.SECONDS.toMillis(
                zeroTimeSecondsOfToday);
        String actual = DATE_TIME_FORMATTER.print(zeroTimeMillisOfToday);
        assertThat(actual).isEqualTo(expected);
    }

    @DataProvider(name = "toZeroTimeSecondsOfTodayTestData")
    public static Object[][] toZeroTimeSecondsOfTodayTestData() {
        return new Object[][] {
                { 1456366664, "2016-02-25 00:00:00" }, // 2016-02-25 10:17:44
                { 1456668046, "2016-02-28 00:00:00" }, // 2016-02-28 22:00:46
        };
    }

    @Test(dataProvider = "toTimeTextTestData")
    public void toTimeText(int displayTimeSeconds, int currentTimeSeconds,
                           String expectedTimeText) {
        String displayTimeText = DateTimeUtil.toTimeText(
                displayTimeSeconds, currentTimeSeconds);
        assertThat(displayTimeText).isEqualTo(expectedTimeText);
    }

    @DataProvider(name = "toTimeTextTestData")
    public static Object[][] toTimeTextTestData() {
        return new Object[][] {
                { 1456243200, 1456368124, "昨日 00:00" }, // 边界
                { 1456311600, 1456368124, "昨日 19:00" },
                { 1456329599, 1456357324, "昨日 23:59" }, // 边界
                { 1456329600, 1456357324, "今日 00:00" }, // 边界
                { 1456354800, 1456357324, "今日 07:00" },
                { 1456365600, 1456368124, "今日 10:00" },
                { 1456415999, 1456368124, "今日 23:59" }, // 边界
                { 1456416000, 1456368124, "明日 00:00" }, // 边界
                { 1456462800, 1456368124, "明日 13:00" },
                { 1456502399, 1456368124, "明日 23:59" }, // 边界
        };
    }

}
