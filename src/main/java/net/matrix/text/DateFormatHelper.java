/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.text;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * 日期格式化工具。
 */
public final class DateFormatHelper {
    /**
     * 阻止实例化。
     */
    private DateFormatHelper() {
    }

    /**
     * 转换日期到字符串。
     * 
     * @param date
     *     日期。
     * @param format
     *     格式，形式见 {@code org.joda.time.format.DateTimeFormat}。
     * @return 字符串
     */
    public static String format(final Calendar date, final String format) {
        if (date == null) {
            return null;
        }

        return DateTimeFormat.forPattern(format).print(date.getTimeInMillis());
    }

    /**
     * 转换日期到字符串。
     * 
     * @param date
     *     日期。
     * @param format
     *     格式，形式见 {@code org.joda.time.format.DateTimeFormat}。
     * @return 字符串
     */
    public static String format(final Calendar date, final DateTimeFormatter format) {
        if (date == null) {
            return null;
        }

        return format.print(date.getTimeInMillis());
    }

    /**
     * 转换日期到字符串。
     * 
     * @param date
     *     日期。
     * @param format
     *     格式，形式见 {@code org.joda.time.format.DateTimeFormat}。
     * @return 字符串
     */
    public static String format(final Date date, final String format) {
        if (date == null) {
            return null;
        }

        return DateTimeFormat.forPattern(format).print(date.getTime());
    }

    /**
     * 转换日期到字符串。
     * 
     * @param date
     *     日期。
     * @param format
     *     格式，形式见 {@code org.joda.time.format.DateTimeFormat}。
     * @return 字符串
     */
    public static String format(final Date date, final DateTimeFormatter format) {
        if (date == null) {
            return null;
        }

        return format.print(date.getTime());
    }

    /**
     * 转换日期到字符串。
     * 
     * @param time
     *     日期。
     * @param format
     *     格式，形式见 {@code org.joda.time.format.DateTimeFormat}。
     * @return 字符串
     */
    public static String format(final long time, final String format) {
        return DateTimeFormat.forPattern(format).print(time);
    }

    /**
     * 转换日期到字符串。
     * 
     * @param time
     *     日期。
     * @param format
     *     格式，形式见 {@code org.joda.time.format.DateTimeFormat}。
     * @return 字符串
     */
    public static String format(final long time, final DateTimeFormatter format) {
        return format.print(time);
    }

    /**
     * 根据字符串构造实例。
     * 
     * @param date
     *     日期字符串。
     * @param format
     *     格式，形式见 {@code org.joda.time.format.DateTimeFormat}。
     * @return 日期
     */
    public static Calendar parseCalendar(final String date, final String format) {
        if (date == null) {
            return null;
        }

        return DateTimeFormat.forPattern(format).parseDateTime(date).toGregorianCalendar();
    }

    /**
     * 根据字符串构造实例。
     * 
     * @param date
     *     日期字符串。
     * @param format
     *     格式，形式见 {@code org.joda.time.format.DateTimeFormat}。
     * @return 日期
     */
    public static Calendar parseCalendar(final String date, final DateTimeFormatter format) {
        if (date == null) {
            return null;
        }

        return format.parseDateTime(date).toGregorianCalendar();
    }

    /**
     * 根据字符串构造实例。
     * 
     * @param date
     *     日期字符串。
     * @param format
     *     格式，形式见 {@code org.joda.time.format.DateTimeFormat}。
     * @return 日期
     */
    public static Date parseDate(final String date, final String format) {
        if (date == null) {
            return null;
        }

        return DateTimeFormat.forPattern(format).parseDateTime(date).toDate();
    }

    /**
     * 根据字符串构造实例。
     * 
     * @param date
     *     日期字符串。
     * @param format
     *     格式，形式见 {@code org.joda.time.format.DateTimeFormat}。
     * @return 日期
     */
    public static Date parseDate(final String date, final DateTimeFormatter format) {
        if (date == null) {
            return null;
        }

        return format.parseDateTime(date).toDate();
    }

    /**
     * 转换日期到字符串。
     * 格式为 yyyy-MM-dd'T'HH:mm:ss。
     * 
     * @param date
     *     日期。
     * @return 字符串
     */
    public static String format(final Calendar date) {
        return format(date, ISODateTimeFormat.dateHourMinuteSecond());
    }

    /**
     * 转换日期到字符串。
     * 格式为 yyyy-MM-dd'T'HH:mm:ss。
     * 
     * @param date
     *     日期。
     * @return 字符串
     */
    public static String format(final Date date) {
        return format(date, ISODateTimeFormat.dateHourMinuteSecond());
    }

    /**
     * 转换日期到字符串。
     * 格式为 yyyy-MM-dd'T'HH:mm:ss。
     * 
     * @param time
     *     日期。
     * @return 字符串
     */
    public static String format(final long time) {
        return format(time, ISODateTimeFormat.dateHourMinuteSecond());
    }

    /**
     * 转换日期到字符串。
     * 
     * @param date
     *     日期。
     * @return 目标字符串，形式为 yyyy-MM-dd。
     */
    public static String formatDate(final Calendar date) {
        return format(date, ISODateTimeFormat.yearMonthDay());
    }

    /**
     * 转换日期到字符串。
     * 
     * @param date
     *     日期。
     * @return 目标字符串，形式为 yyyy-MM-dd。
     */
    public static String formatDate(final Date date) {
        return format(date, ISODateTimeFormat.yearMonthDay());
    }

    /**
     * 转换日期到字符串。
     * 
     * @param time
     *     日期。
     * @return 目标字符串，形式为 yyyy-MM-dd。
     */
    public static String formatDate(final long time) {
        return format(time, ISODateTimeFormat.yearMonthDay());
    }

    /**
     * 转换日期到字符串。
     * 
     * @param date
     *     日期。
     * @param year
     *     年。
     * @param month
     *     月。
     * @param day
     *     日。
     * @return 目标字符串，形式为 yyyy(year)MM(month)dd(date)。
     */
    public static String formatDate(final Calendar date, final String year, final String month, final String day) {
        if (date == null) {
            return null;
        }

        return format(date, "yyyy") + year + format(date, "MM") + month + format(date, "dd") + day;
    }

    /**
     * 转换日期到字符串。
     * 
     * @param date
     *     日期。
     * @param year
     *     年。
     * @param month
     *     月。
     * @param day
     *     日。
     * @return 目标字符串，形式为 yyyy(year)MM(month)dd(date)。
     */
    public static String formatDate(final Date date, final String year, final String month, final String day) {
        if (date == null) {
            return null;
        }

        return format(date, "yyyy") + year + format(date, "MM") + month + format(date, "dd") + day;
    }

    /**
     * 转换日期到字符串。
     * 
     * @param time
     *     日期。
     * @param year
     *     年。
     * @param month
     *     月。
     * @param day
     *     日。
     * @return 目标字符串，形式为 yyyy(year)MM(month)dd(date)。
     */
    public static String formatDate(final long time, final String year, final String month, final String day) {
        return format(time, "yyyy") + year + format(time, "MM") + month + format(time, "dd") + day;
    }

    /**
     * 转换日期到字符串。
     * 
     * @param date
     *     日期。
     * @return 目标字符串，形式为 yyyy年MM月dd日。
     */
    public static String formatDateChinese(final Calendar date) {
        return formatDate(date, "年", "月", "日");
    }

    /**
     * 转换日期到字符串。
     * 
     * @param date
     *     日期。
     * @return 目标字符串，形式为 yyyy年MM月dd日。
     */
    public static String formatDateChinese(final Date date) {
        return formatDate(date, "年", "月", "日");
    }

    /**
     * 转换日期到字符串。
     * 
     * @param time
     *     日期。
     * @return 目标字符串，形式为 yyyy年MM月dd日。
     */
    public static String formatDateChinese(final long time) {
        return formatDate(time, "年", "月", "日");
    }

    /**
     * 根据字符串构造实例。
     * 格式为 yyyy-MM-dd'T'HH:mm:ss。
     * 
     * @param date
     *     日期字符串。
     * @return 日期
     */
    public static Calendar parseCalendar(final String date) {
        return parseCalendar(date, ISODateTimeFormat.dateHourMinuteSecond());
    }

    /**
     * 根据字符串构造实例。
     * 格式为 yyyy-MM-dd'T'HH:mm:ss。
     * 
     * @param date
     *     日期字符串。
     * @return 日期
     */
    public static Date parseDate(final String date) {
        return parseDate(date, ISODateTimeFormat.dateHourMinuteSecond());
    }
}
