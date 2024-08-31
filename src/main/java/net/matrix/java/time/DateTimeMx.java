/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.time;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.matrix.text.ResourceBundleMessageFormatter;

/**
 * 日期时间工具。
 */
@ThreadSafe
public final class DateTimeMx {
    /**
     * 日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(DateTimeMx.class);

    /**
     * 区域相关资源。
     */
    private static final ResourceBundleMessageFormatter RBMF = new ResourceBundleMessageFormatter(DateTimeMx.class).useCurrentLocale();

    /**
     * 阻止实例化。
     */
    private DateTimeMx() {
    }

    /**
     * 校验日期的年、月、日数值是否符合历法。
     *
     * @param year
     *     年。
     * @param month
     *     月。
     * @param day
     *     日。
     * @return 是否符合历法。
     */
    public static boolean isValidDate(int year, int month, int day) {
        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (DateTimeException e) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(RBMF.get("日期不符合历法"), e);
            }
            return false;
        }
    }

    /**
     * 校验时间的时、分、秒数值是否符合历法。
     *
     * @param hour
     *     时。
     * @param minute
     *     分。
     * @param second
     *     秒。
     * @return 是否符合历法。
     */
    public static boolean isValidTime(int hour, int minute, int second) {
        try {
            LocalTime.of(hour, minute, second);
            return true;
        } catch (DateTimeException e) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(RBMF.get("时间不符合历法"), e);
            }
            return false;
        }
    }

    /**
     * 将参数转换为 {@link Instant} 对象。
     *
     * @param object
     *     表示日期时间的对象。
     * @return {@link Instant} 对象。
     */
    @Nullable
    public static Instant toInstant(@Nullable Object object) {
        if (object == null) {
            return null;
        }

        if (object instanceof Long) {
            return Instant.ofEpochMilli((long) object);
        }
        if (object instanceof Date date) {
            return date.toInstant();
        }
        if (object instanceof Calendar calendar) {
            return calendar.toInstant();
        }

        throw new UnsupportedOperationException();
    }

    /**
     * 将参数转换为 {@link LocalDate} 对象。
     *
     * @param object
     *     表示日期时间的对象。
     * @param zoneId
     *     时区 ID，为 <code>null</code> 时使用系统默认时区 ID。
     * @return {@link LocalDate} 对象。
     */
    @Nullable
    public static LocalDate toLocalDate(@Nullable Object object, @Nullable ZoneId zoneId) {
        Instant instant = toInstant(object);
        if (instant == null) {
            return null;
        }

        if (zoneId == null) {
            zoneId = ZoneId.systemDefault();
        }

        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * 将参数转换为 {@link LocalTime} 对象。
     *
     * @param object
     *     表示日期时间的对象。
     * @param zoneId
     *     时区 ID，为 <code>null</code> 时使用系统默认时区 ID。
     * @return {@link LocalTime} 对象。
     */
    @Nullable
    public static LocalTime toLocalTime(@Nullable Object object, @Nullable ZoneId zoneId) {
        Instant instant = toInstant(object);
        if (instant == null) {
            return null;
        }

        if (zoneId == null) {
            zoneId = ZoneId.systemDefault();
        }

        return instant.atZone(zoneId).toLocalTime();
    }

    /**
     * 将参数转换为 {@link LocalDateTime} 对象。
     *
     * @param object
     *     表示日期时间的对象。
     * @param zoneId
     *     时区 ID，为 <code>null</code> 时使用系统默认时区 ID。
     * @return {@link LocalDateTime} 对象。
     */
    @Nullable
    public static LocalDateTime toLocalDateTime(@Nullable Object object, @Nullable ZoneId zoneId) {
        Instant instant = toInstant(object);
        if (instant == null) {
            return null;
        }

        if (zoneId == null) {
            zoneId = ZoneId.systemDefault();
        }

        return instant.atZone(zoneId).toLocalDateTime();
    }

    private static Instant toInstant(Object object, ZoneId zoneId) {
        if (object instanceof Instant instant) {
            return instant;
        }
        if (object instanceof LocalDate localDate) {
            return localDate.atStartOfDay(zoneId).toInstant();
        }
        if (object instanceof LocalTime localTime) {
            return localTime.atDate(LocalDate.now()).atZone(zoneId).toInstant();
        }
        if (object instanceof LocalDateTime localDateTime) {
            return localDateTime.atZone(zoneId).toInstant();
        }

        throw new UnsupportedOperationException();
    }

    /**
     * 将参数转换为自 1970-01-01T00:00:00Z 以来的毫秒数。
     *
     * @param object
     *     表示日期时间的对象。
     * @param zoneId
     *     时区 ID，为 <code>null</code> 时使用系统默认时区 ID。
     * @return 毫秒数。
     */
    @Nullable
    public static Long toEpochMilli(@Nullable Object object, @Nullable ZoneId zoneId) {
        if (object == null) {
            return null;
        }

        if (zoneId == null) {
            zoneId = ZoneId.systemDefault();
        }

        return toInstant(object, zoneId).toEpochMilli();
    }

    /**
     * 将参数转换为 {@link Date} 对象。
     *
     * @param object
     *     表示日期时间的对象。
     * @param zoneId
     *     时区 ID，为 <code>null</code> 时使用系统默认时区 ID。
     * @return {@link Date} 对象。
     */
    @Nullable
    public static Date toDate(@Nullable Object object, @Nullable ZoneId zoneId) {
        if (object == null) {
            return null;
        }

        if (zoneId == null) {
            zoneId = ZoneId.systemDefault();
        }

        return Date.from(toInstant(object, zoneId));
    }

    /**
     * 将参数转换为 {@link Calendar} 对象。
     *
     * @param object
     *     表示日期时间的对象。
     * @param zoneId
     *     时区 ID，为 <code>null</code> 时使用系统默认时区 ID。
     * @return {@link Calendar} 对象。
     */
    @Nullable
    public static Calendar toCalendar(@Nullable Object object, @Nullable ZoneId zoneId) {
        if (object == null) {
            return null;
        }

        if (zoneId == null) {
            zoneId = ZoneId.systemDefault();
        }

        Date date = Date.from(toInstant(object, zoneId));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
