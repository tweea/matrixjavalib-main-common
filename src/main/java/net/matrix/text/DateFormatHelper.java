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
 * 日期格式化工具方法。
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
        return format(date.getTime(), format);
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
        return format(date.getTime(), format);
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
        return format(time, DateTimeFormat.forPattern(format));
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
    public static Calendar parse(final String date, final String format) {
        return parse(date, DateTimeFormat.forPattern(format));
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
    public static Calendar parse(final String date, final DateTimeFormatter format) {
        return format.parseDateTime(date).toGregorianCalendar();
    }

    /**
     * 根据字符串构造实例。
     * 格式为 yyyy-MM-dd'T'HH:mm:ss。
     * 
     * @param date
     *     日期字符串。
     * @return 日期
     */
    public static Calendar parse(final String date) {
        return parse(date, ISODateTimeFormat.dateHourMinuteSecond());
    }

    /**
     * 转换日期到字符串。
     * 格式为 yyyy-MM-dd'T'HH:mm:ss。
     * 
     * @param date
     *     日期。
     * @return 字符串
     */
    public static String toString(final Calendar date) {
        return format(date.getTimeInMillis(), ISODateTimeFormat.dateHourMinuteSecond());
    }

    /**
     * 转换日期到字符串。
     * 
     * @param date
     *     日期。
     * @return 目标字符串，形式为 yyyy-MM-dd。
     */
    public static String toDisplayString(final Calendar date) {
        return format(date.getTimeInMillis(), ISODateTimeFormat.yearMonthDay());
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
    public static String toDisplayString(final Calendar date, final String year, final String month, final String day) {
        return format(date, "yyyy") + year + format(date, "MM") + month + format(date, "dd") + day;
    }

    /**
     * 转换日期到字符串。
     * 
     * @param date
     *     日期。
     * @return 目标字符串，形式为 yyyy年MM月dd日。
     */
    public static String toChineseString(final Calendar date) {
        return toDisplayString(date, "年", "月", "日");
    }
}
