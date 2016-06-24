package com.common.time;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for {@link TimeUtil}.
 *
 * @author xingle
 * @since 1.0
 */
public class TimeUtilTest {

    @Test(dataProvider = "constantValueTestData")
    public void constantValue(int constant, int expected) {
        assertThat(constant).isEqualTo(expected);
    }

    @DataProvider(name = "constantValueTestData")
    public static Object[][] constantValueTestData() {
        return new Object[][] {
                { TimeUtil.ONE_DAY_SECONDS, 86400 },
        };
    }


    /**
     * 请参考 {@link TimeUtil#currentTimeSeconds()}
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
        int zeroTimeSecondsOfToday = TimeUtil.toZeroTimeSecondsOfToday(
                timeSeconds);
        // 时间肯定大于或等于当日零点时刻
        assertThat(timeSeconds).isGreaterThanOrEqualTo(zeroTimeSecondsOfToday);

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

                { 1457097294, "2016-03-04 00:00:00" }, // 2016-03-04 21:14:54
                // 边界
                { 1457020800, "2016-03-04 00:00:00" }, // 2016-03-04 00:00:00
                { 1457020799, "2016-03-03 00:00:00" }, // 2016-03-03 59:59:59
        };
    }


    @Test(dataProvider = "toZeroTimeSecondsOfTomorrowTestData")
    public void toZeroTimeSecondsOfTomorrow(int timeSeconds, String expected) {
        int zeroTimeSecondsOfTomorrow = TimeUtil.toZeroTimeSecondsOfTomorrow(
                timeSeconds);
        // 时间肯定小于或等于明日零点时刻
        assertThat(timeSeconds).isLessThanOrEqualTo(zeroTimeSecondsOfTomorrow);

        long zeroTimeMillisOfTomorrow = TimeUnit.SECONDS.toMillis(
                zeroTimeSecondsOfTomorrow);
        String actual = DATE_TIME_FORMATTER.print(zeroTimeMillisOfTomorrow);
        assertThat(actual).isEqualTo(expected);
    }

    @DataProvider(name = "toZeroTimeSecondsOfTomorrowTestData")
    public static Object[][] toZeroTimeSecondsOfTomorrowTestData() {
        return new Object[][] {
                { 1457097294, "2016-03-05 00:00:00" }, // 2016-03-04 21:14:54
                // 边界
                { 1457107199, "2016-03-05 00:00:00" }, // 2016-03-04 59:59:59
                { 1457107200, "2016-03-06 00:00:00" }, // 2016-03-05 00:00:00
        };
    }


    @Test(dataProvider = "toTimeTextTestData")
    public void toTimeText(int displayTimeSeconds, int currentTimeSeconds,
                           String expectedTimeText) {
        String displayTimeText = TimeUtil.toTimeText(
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
