package com.common.time;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * 时间工具辅助类。
 *
 * @author xingle
 * @see java.util.concurrent.TimeUnit
 * @see org.apache.commons.lang3.time.DateUtils
 * @see org.joda.time.format.DateTimeFormatter
 * @since 1.0
 */
public final class TimeUtil {
    private TimeUtil() {
        throw new AssertionError("No com.common.time.TimeUtil instances for you!");
    }

    /**
     * Number of seconds in a standard day.
     *
     * @see org.apache.commons.lang3.time.DateUtils#MILLIS_PER_DAY
     */
    public static final int SECONDS_PER_DAY = (int) TimeUnit.DAYS.toSeconds(1L);


    // Time

    /**
     * 以秒为单位返回系统的当前时间。
     * 
     * <p>内部使用 {@link System#currentTimeMillis()} 实现。
     *
     * @return 系统的当前时间
     */
    public static int currentTimeSeconds() {
        return (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    /**
     * 转换“时间”到“那天当日的零点时刻”。
     *
     * @param timeSeconds 时间
     * @return 那天当日的零点时刻
     */
    public static int toZeroTimeSecondsOfToday(int timeSeconds) {
        return toZeroTimeSeconds(timeSeconds, 0);
    }

    /**
     * 转换“时间”到“那天明日的零点时刻”。
     *
     * @param timeSeconds 时间
     * @return 那天明日的零点时刻
     */
    public static int toZeroTimeSecondsOfTomorrow(int timeSeconds) {
        return toZeroTimeSeconds(timeSeconds, 1);
    }

    private static int toZeroTimeSeconds(int timeSeconds, int days) {
        final Calendar calendar = Calendar.getInstance();
        long timeMillis = TimeUnit.SECONDS.toMillis(timeSeconds);
        calendar.setTimeInMillis(timeMillis);

        // 设置 小时、分钟、秒、毫秒 都为 0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        // 增加天数
        if (days != 0) {
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + days);
        }

        return (int) (TimeUnit.MILLISECONDS.toSeconds(calendar.getTimeInMillis()));
    }


    // Format

    /**
     * "小时:分钟"格式化器
     *
     * @see org.apache.commons.lang3.time.DateFormatUtils#ISO_DATETIME_FORMAT
     */
    private static final DateTimeFormatter HOUR_MINUTE_FORMATTER =
            DateTimeFormat.forPattern("HH:mm");

    /**
     * 根据当前时间格式化展示时间。
     * <p>
     * 格式：{昨日|今日|明日} HH:mm
     *
     * @param displayTimeSeconds 展示时间
     * @param currentTimeSeconds 当前时间
     * @return 格式化后的时间文本
     */
    public static String toTimeText(int displayTimeSeconds, int currentTimeSeconds) {
        int zeroTimeSecondsOfToday = toZeroTimeSecondsOfToday(currentTimeSeconds); // 今天零点时刻
        int zeroTimeSecondsOfTomorrow = zeroTimeSecondsOfToday + SECONDS_PER_DAY; // 明天零点时刻

        // 判断{昨日|今日|明日}
        String textPrefix = "今日";
        if (displayTimeSeconds < zeroTimeSecondsOfToday) {
            textPrefix = "昨日";
        } else if (displayTimeSeconds >= zeroTimeSecondsOfTomorrow) {
            textPrefix = "明日";
        }
        return textPrefix + ' ' + HOUR_MINUTE_FORMATTER.print(
                TimeUnit.SECONDS.toMillis(displayTimeSeconds));
    }

}
