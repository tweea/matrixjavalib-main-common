/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.util;

import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具。
 */
public final class Calendars {
    /**
     * 日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(Calendars.class);

    /**
     * 阻止实例化。
     */
    private Calendars() {
    }

    /**
     * 校验日期的年、月、日数值是否符合历法。
     * 
     * @param year
     *     年
     * @param month
     *     月 1-12
     * @param day
     *     日
     * @return 是否符合历法
     */
    public static boolean isValidDate(final int year, final int month, final int day) {
        try {
            buildDate(year, month, day);
            return true;
        } catch (IllegalFieldValueException e) {
            LOG.trace("", e);
            return false;
        }
    }

    /**
     * 构造日期对象。
     * 
     * @param year
     *     年
     * @param month
     *     月
     * @param day
     *     日
     * @return 日期对象
     */
    private static LocalDate buildDate(final int year, final int month, final int day) {
        return new LocalDate(year, month, day);
    }

    /**
     * 判断指定年是否闰年。
     * 
     * @param year
     *     年份
     * @return 是否闰年
     */
    public static boolean isLeapYear(final int year) {
        return buildDate(year, 1, 1).year().isLeap();
    }

    /**
     * 创建 {@link DateTime} 对象，参数为 null 时返回 null。
     * 
     * @param instant
     *     表示日期的对象
     * @return {@link DateTime} 对象
     */
    public static DateTime newDateTime(final Object instant) {
        if (instant == null) {
            return null;
        }

        return new DateTime(instant);
    }

    /**
     * 创建 {@link LocalDate} 对象，参数为 null 时返回 null。
     * 
     * @param instant
     *     表示日期的对象
     * @return {@link LocalDate} 对象
     */
    public static LocalDate newLocalDate(final Object instant) {
        if (instant == null) {
            return null;
        }

        return new LocalDate(instant);
    }

    /**
     * 创建 {@link LocalTime} 对象，参数为 null 时返回 null。
     * 
     * @param instant
     *     表示日期的对象
     * @return {@link LocalTime} 对象
     */
    public static LocalTime newLocalTime(final Object instant) {
        if (instant == null) {
            return null;
        }

        return new LocalTime(instant);
    }

    /**
     * 创建 {@link LocalDateTime} 对象，参数为 null 时返回 null。
     * 
     * @param instant
     *     表示日期的对象
     * @return {@link LocalDateTime} 对象
     */
    public static LocalDateTime newLocalDateTime(final Object instant) {
        if (instant == null) {
            return null;
        }

        return new LocalDateTime(instant);
    }
}
