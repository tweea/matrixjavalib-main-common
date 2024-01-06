/*
 * 版权所有 2023 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 日期时间格式化工具。
 */
public final class DateTimeFormatterMx {
    /**
     * 缓存。
     */
    private static final ConcurrentMap<MultiKey, DateTimeFormatter> CACHE = new ConcurrentHashMap<>();

    /**
     * 阻止实例化。
     */
    private DateTimeFormatterMx() {
    }

    /**
     * 获取格式化对象，使用系统默认时区和系统默认区域。
     * 
     * @param pattern
     *     格式，形式见 {@link java.time.format.DateTimeFormatter}。
     */
    public static DateTimeFormatter of(String pattern) {
        return CACHE.computeIfAbsent(new MultiKey(ArrayUtils.toArray(pattern), false), key -> DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取格式化对象，使用特定时区和系统默认区域。
     * 
     * @param pattern
     *     格式，形式见 {@link java.time.format.DateTimeFormatter}。
     * @param zoneId
     *     时区。
     */
    public static DateTimeFormatter of(String pattern, ZoneId zoneId) {
        return CACHE.computeIfAbsent(new MultiKey(pattern, zoneId), key -> DateTimeFormatter.ofPattern(pattern).withZone(zoneId));
    }

    /**
     * 获取格式化对象，使用系统默认时区和特定区域。
     * 
     * @param pattern
     *     格式，形式见 {@link java.time.format.DateTimeFormatter}。
     * @param locale
     *     区域。
     */
    public static DateTimeFormatter of(String pattern, Locale locale) {
        return CACHE.computeIfAbsent(new MultiKey(pattern, locale), key -> DateTimeFormatter.ofPattern(pattern, locale));
    }

    /**
     * 获取格式化对象，使用特定时区和特定区域。
     * 
     * @param pattern
     *     格式，形式见 {@link java.time.format.DateTimeFormatter}。
     * @param zoneId
     *     时区。
     * @param locale
     *     区域。
     */
    public static DateTimeFormatter of(String pattern, ZoneId zoneId, Locale locale) {
        return CACHE.computeIfAbsent(new MultiKey(pattern, zoneId, locale), key -> DateTimeFormatter.ofPattern(pattern, locale).withZone(zoneId));
    }

    /**
     * 转换日期时间对象到字符串。
     * 
     * @param temporal
     *     日期时间对象。
     * @param pattern
     *     格式，形式见 {@link java.time.format.DateTimeFormatter}。
     * @return 字符串。
     */
    public static String format(TemporalAccessor temporal, String pattern) {
        if (temporal == null) {
            return null;
        }

        return of(pattern).format(temporal);
    }

    /**
     * 转换日期时间对象到字符串。
     * 
     * @param temporal
     *     日期时间对象。
     * @param formatter
     *     格式化对象。
     * @return 字符串。
     */
    public static String format(TemporalAccessor temporal, DateTimeFormatter formatter) {
        if (temporal == null) {
            return null;
        }

        return formatter.format(temporal);
    }

    /**
     * 转换字符串到日期时间对象。
     * 
     * @param text
     *     字符串。
     * @param pattern
     *     格式，形式见 {@link java.time.format.DateTimeFormatter}。
     * @param query
     *     日期时间对象的类型。
     * @return 日期时间对象。
     */
    public static <T> T parse(CharSequence text, String pattern, TemporalQuery<T> query) {
        if (text == null) {
            return null;
        }

        return of(pattern).parse(text, query);
    }

    /**
     * 转换字符串到日期时间对象。
     * 
     * @param text
     *     字符串。
     * @param formatter
     *     格式化对象。
     * @param query
     *     日期时间对象的类型。
     * @return 日期时间对象。
     */
    public static <T> T parse(CharSequence text, DateTimeFormatter formatter, TemporalQuery<T> query) {
        if (text == null) {
            return null;
        }

        return formatter.parse(text, query);
    }

    /**
     * 转换字符串到时刻对象。
     * 
     * @param text
     *     字符串。
     * @param pattern
     *     格式，形式见 {@link java.time.format.DateTimeFormatter}。
     * @return 时刻对象。
     */
    public static Instant parseInstant(CharSequence text, String pattern) {
        return parse(text, pattern, Instant::from);
    }

    /**
     * 转换字符串到时刻对象。
     * 
     * @param text
     *     字符串。
     * @param formatter
     *     格式化对象。
     * @return 时刻对象。
     */
    public static Instant parseInstant(CharSequence text, DateTimeFormatter formatter) {
        return parse(text, formatter, Instant::from);
    }

    /**
     * 转换字符串到本地日期对象。
     * 
     * @param text
     *     字符串。
     * @param pattern
     *     格式，形式见 {@link java.time.format.DateTimeFormatter}。
     * @return 本地日期对象。
     */
    public static LocalDate parseLocalDate(CharSequence text, String pattern) {
        return parse(text, pattern, LocalDate::from);
    }

    /**
     * 转换字符串到本地日期对象。
     * 
     * @param text
     *     字符串。
     * @param formatter
     *     格式化对象。
     * @return 本地日期对象。
     */
    public static LocalDate parseLocalDate(CharSequence text, DateTimeFormatter formatter) {
        return parse(text, formatter, LocalDate::from);
    }

    /**
     * 转换字符串到本地时间对象。
     * 
     * @param text
     *     字符串。
     * @param pattern
     *     格式，形式见 {@link java.time.format.DateTimeFormatter}。
     * @return 本地时间对象。
     */
    public static LocalTime parseLocalTime(CharSequence text, String pattern) {
        return parse(text, pattern, LocalTime::from);
    }

    /**
     * 转换字符串到本地时间对象。
     * 
     * @param text
     *     字符串。
     * @param formatter
     *     格式化对象。
     * @return 本地时间对象。
     */
    public static LocalTime parseLocalTime(CharSequence text, DateTimeFormatter formatter) {
        return parse(text, formatter, LocalTime::from);
    }

    /**
     * 转换字符串到本地日期时间对象。
     * 
     * @param text
     *     字符串。
     * @param pattern
     *     格式，形式见 {@link java.time.format.DateTimeFormatter}。
     * @return 本地日期时间对象。
     */
    public static LocalDateTime parseLocalDateTime(CharSequence text, String pattern) {
        return parse(text, pattern, LocalDateTime::from);
    }

    /**
     * 转换字符串到本地日期时间对象。
     * 
     * @param text
     *     字符串。
     * @param formatter
     *     格式化对象。
     * @return 本地日期时间对象。
     */
    public static LocalDateTime parseLocalDateTime(CharSequence text, DateTimeFormatter formatter) {
        return parse(text, formatter, LocalDateTime::from);
    }
}
