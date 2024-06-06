/*
 * 版权所有 2023 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DateTimeFormatterMxTest {
    static final String ISO_INSTANT_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX";

    static final DateTimeFormatter ISO_INSTANT_FORMATTER = DateTimeFormatter.ISO_INSTANT;

    static final String ISO_DATE_FORMAT = "yyyy-MM-dd";

    static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    static final String ISO_TIME_FORMAT = "HH:mm:ss";

    static final DateTimeFormatter ISO_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;

    static final String ISO_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    static final DateTimeFormatter ISO_DATETIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Test
    void testOf() {
        DateTimeFormatter formatter1 = DateTimeFormatterMx.of(ISO_DATE_FORMAT);
        DateTimeFormatter formatter2 = DateTimeFormatterMx.of(ISO_DATE_FORMAT);
        assertThat(formatter1).isSameAs(formatter2);
    }

    @Test
    void testOf_zoneId() {
        DateTimeFormatter formatter1 = DateTimeFormatterMx.of(ISO_DATE_FORMAT, ZoneId.of("+8"));
        DateTimeFormatter formatter2 = DateTimeFormatterMx.of(ISO_DATE_FORMAT, ZoneId.of("+8"));
        assertThat(formatter1).isSameAs(formatter2);
    }

    @Test
    void testOf_locale() {
        DateTimeFormatter formatter1 = DateTimeFormatterMx.of(ISO_DATE_FORMAT, Locale.CHINA);
        DateTimeFormatter formatter2 = DateTimeFormatterMx.of(ISO_DATE_FORMAT, Locale.CHINA);
        assertThat(formatter1).isSameAs(formatter2);
    }

    @Test
    void testOf_zoneId_locale() {
        DateTimeFormatter formatter1 = DateTimeFormatterMx.of(ISO_DATE_FORMAT, ZoneId.of("+8"), Locale.CHINA);
        DateTimeFormatter formatter2 = DateTimeFormatterMx.of(ISO_DATE_FORMAT, ZoneId.of("+8"), Locale.CHINA);
        assertThat(formatter1).isSameAs(formatter2);
    }

    @Test
    void testFormat() {
        LocalDate date = LocalDate.of(2011, 1, 1);
        LocalDate nullDate = null;

        assertThat(DateTimeFormatterMx.format(date, ISO_DATE_FORMAT)).isEqualTo("2011-01-01");
        assertThat(DateTimeFormatterMx.format(nullDate, ISO_DATE_FORMAT)).isNull();

        assertThat(DateTimeFormatterMx.format(date, ISO_DATE_FORMATTER)).isEqualTo("2011-01-01");
        assertThat(DateTimeFormatterMx.format(nullDate, ISO_DATE_FORMATTER)).isNull();
    }

    @Test
    void testParse() {
        LocalDate date = LocalDate.of(2011, 12, 1);

        assertThat(DateTimeFormatterMx.parse("2011-12-01", ISO_DATE_FORMAT, LocalDate::from)).isEqualTo(date);
        assertThat(DateTimeFormatterMx.parse(null, ISO_DATE_FORMAT, LocalDate::from)).isNull();

        assertThat(DateTimeFormatterMx.parse("2011-12-01", ISO_DATE_FORMATTER, LocalDate::from)).isEqualTo(date);
        assertThat(DateTimeFormatterMx.parse(null, ISO_DATE_FORMATTER, LocalDate::from)).isNull();
    }

    @Test
    void testParseInstant() {
        LocalDateTime datetime = LocalDateTime.of(2011, 12, 1, 12, 13, 14);

        assertThat(LocalDateTime.ofInstant(DateTimeFormatterMx.parseInstant("2011-12-01T12:13:14Z", ISO_INSTANT_FORMAT), ZoneId.of("Z"))).isEqualTo(datetime);
        assertThat(DateTimeFormatterMx.parseInstant(null, ISO_INSTANT_FORMAT)).isNull();

        assertThat(LocalDateTime.ofInstant(DateTimeFormatterMx.parseInstant("2011-12-01T12:13:14Z", ISO_INSTANT_FORMATTER), ZoneId.of("Z")))
            .isEqualTo(datetime);
        assertThat(DateTimeFormatterMx.parseInstant(null, ISO_INSTANT_FORMATTER)).isNull();
    }

    @Test
    void testParseLocalDate() {
        LocalDate date = LocalDate.of(2011, 12, 1);

        assertThat(DateTimeFormatterMx.parseLocalDate("2011-12-01", ISO_DATE_FORMAT)).isEqualTo(date);
        assertThat(DateTimeFormatterMx.parseLocalDate(null, ISO_DATE_FORMAT)).isNull();

        assertThat(DateTimeFormatterMx.parseLocalDate("2011-12-01", ISO_DATE_FORMATTER)).isEqualTo(date);
        assertThat(DateTimeFormatterMx.parseLocalDate(null, ISO_DATE_FORMATTER)).isNull();
    }

    @Test
    void testParseLocalTime() {
        LocalTime time = LocalTime.of(12, 13, 14);

        assertThat(DateTimeFormatterMx.parseLocalTime("12:13:14", ISO_TIME_FORMAT)).isEqualTo(time);
        assertThat(DateTimeFormatterMx.parseLocalTime(null, ISO_TIME_FORMAT)).isNull();

        assertThat(DateTimeFormatterMx.parseLocalTime("12:13:14", ISO_TIME_FORMATTER)).isEqualTo(time);
        assertThat(DateTimeFormatterMx.parseLocalTime(null, ISO_TIME_FORMATTER)).isNull();
    }

    @Test
    void testParseLocalDateTime() {
        LocalDateTime datetime = LocalDateTime.of(2011, 12, 1, 12, 13, 14);

        assertThat(DateTimeFormatterMx.parseLocalDateTime("2011-12-01T12:13:14", ISO_DATETIME_FORMAT)).isEqualTo(datetime);
        assertThat(DateTimeFormatterMx.parseLocalDateTime(null, ISO_DATETIME_FORMAT)).isNull();

        assertThat(DateTimeFormatterMx.parseLocalDateTime("2011-12-01T12:13:14", ISO_DATETIME_FORMATTER)).isEqualTo(datetime);
        assertThat(DateTimeFormatterMx.parseLocalDateTime(null, ISO_DATETIME_FORMATTER)).isNull();
    }
}
