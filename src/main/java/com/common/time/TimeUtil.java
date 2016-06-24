package com.common.time;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * 时间工具辅助类。
 *
 * @author xingle
 * @since 1.0
 */
public abstract class TimeUtil {

    /**
     * 一天有多少秒
     */
    public static final int ONE_DAY_SECONDS = (int) TimeUnit.DAYS.toSeconds(1L);

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

    // 小时、分钟 格式化器
    private static final DateTimeFormatter hourMinuteFormatter =
            DateTimeFormat.forPattern("HH:mm");

    /**
     * 转换“时间”到“那天当日的零点时刻”。
     *
     * @param timeSeconds 时间
     * @return 那天当日的零点时刻
     */
    public static int toZeroTimeSecondsOfToday(int timeSeconds) {
        final Calendar calendar = Calendar.getInstance();
        long timeMillis = TimeUnit.SECONDS.toMillis(timeSeconds);
        calendar.setTimeInMillis(timeMillis);

        // 设置 小时、分钟、秒、毫秒 都为 0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return (int) (TimeUnit.MILLISECONDS.toSeconds(calendar.getTimeInMillis()));
    }

    /**
     * 转换“时间”到“那天明日的零点时刻”。
     *
     * @param timeSeconds 时间
     * @return 那天明日的零点时刻
     */
    public static int toZeroTimeSecondsOfTomorrow(int timeSeconds) {
        final Calendar calendar = Calendar.getInstance();
        long timeMillis = TimeUnit.SECONDS.toMillis(timeSeconds);
        calendar.setTimeInMillis(timeMillis);

        // 设置 小时、分钟、秒、毫秒 都为 0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        // 增加 1 天
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);

        return (int) (TimeUnit.MILLISECONDS.toSeconds(calendar.getTimeInMillis()));
    }

    /**
     * 根据当前时间格式化展示时间。
     * <p>
     * 格式：{昨日|今日|明日} HH:mm
     * </p>
     *
     * @param displayTimeSeconds 展示时间
     * @param currentTimeSeconds 当前时间
     * @return 格式化后的时间文本
     */
    public static String toTimeText(int displayTimeSeconds, int currentTimeSeconds) {
        int zeroTimeSecondsOfToday = toZeroTimeSecondsOfToday(currentTimeSeconds); // 今天零点时刻
        int zeroTimeSecondsOfTomorrow = zeroTimeSecondsOfToday + ONE_DAY_SECONDS; // 明天零点时刻

        // 判断{昨日|今日|明日}
        String textPrefix = "今日";
        if (displayTimeSeconds < zeroTimeSecondsOfToday) {
            textPrefix = "昨日";
        } else if (displayTimeSeconds >= zeroTimeSecondsOfTomorrow) {
            textPrefix = "明日";
        }
        return textPrefix + ' ' + hourMinuteFormatter.print(
                TimeUnit.SECONDS.toMillis(displayTimeSeconds));
    }

}
