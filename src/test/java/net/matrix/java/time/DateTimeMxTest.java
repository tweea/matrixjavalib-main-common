/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DateTimeMxTest {
    @Test
    void testIsValidDate() {
        assertThat(DateTimeMx.isValidDate(1948, 5, 1)).isTrue();
        assertThat(DateTimeMx.isValidDate(2011, 10, 1)).isTrue();
        assertThat(DateTimeMx.isValidDate(2011, 13, 1)).isFalse();
        assertThat(DateTimeMx.isValidDate(2011, 2, 31)).isFalse();
    }

    @Test
    void testIsValidTime() {
        assertThat(DateTimeMx.isValidTime(2, 2, 2)).isTrue();
        assertThat(DateTimeMx.isValidTime(18, 18, 18)).isTrue();
        assertThat(DateTimeMx.isValidTime(25, 18, 18)).isFalse();
        assertThat(DateTimeMx.isValidTime(18, 68, 18)).isFalse();
        assertThat(DateTimeMx.isValidTime(18, 18, 68)).isFalse();
    }

    @Test
    void testToInstant() {
        assertThat(DateTimeMx.toInstant(null)).isNull();
        assertThat(DateTimeMx.toInstant(1L)).isNotNull();
        assertThat(DateTimeMx.toInstant(new Date())).isNotNull();
        assertThat(DateTimeMx.toInstant(Calendar.getInstance())).isNotNull();
    }

    @Test
    void testToLocalDate() {
        assertThat(DateTimeMx.toLocalDate(null, null)).isNull();
        assertThat(DateTimeMx.toLocalDate(1L, null)).isNotNull();
        assertThat(DateTimeMx.toLocalDate(new Date(), null)).isNotNull();
        assertThat(DateTimeMx.toLocalDate(Calendar.getInstance(), null)).isNotNull();
    }

    @Test
    void testToLocalTime() {
        assertThat(DateTimeMx.toLocalTime(null, null)).isNull();
        assertThat(DateTimeMx.toLocalTime(1L, null)).isNotNull();
        assertThat(DateTimeMx.toLocalTime(new Date(), null)).isNotNull();
        assertThat(DateTimeMx.toLocalTime(Calendar.getInstance(), null)).isNotNull();
    }

    @Test
    void testToLocalDateTime() {
        assertThat(DateTimeMx.toLocalDateTime(null, null)).isNull();
        assertThat(DateTimeMx.toLocalDateTime(1L, null)).isNotNull();
        assertThat(DateTimeMx.toLocalDateTime(new Date(), null)).isNotNull();
        assertThat(DateTimeMx.toLocalDateTime(Calendar.getInstance(), null)).isNotNull();
    }

    @Test
    void testToEpochMilli() {
        assertThat(DateTimeMx.toEpochMilli(null, null)).isNull();
        assertThat(DateTimeMx.toEpochMilli(Instant.now(), null)).isNotNull();
        assertThat(DateTimeMx.toEpochMilli(LocalDate.now(), null)).isNotNull();
        assertThat(DateTimeMx.toEpochMilli(LocalTime.now(), null)).isNotNull();
        assertThat(DateTimeMx.toEpochMilli(LocalDateTime.now(), null)).isNotNull();
    }

    @Test
    void testToDate() {
        assertThat(DateTimeMx.toDate(null, null)).isNull();
        assertThat(DateTimeMx.toDate(Instant.now(), null)).isNotNull();
        assertThat(DateTimeMx.toDate(LocalDate.now(), null)).isNotNull();
        assertThat(DateTimeMx.toDate(LocalTime.now(), null)).isNotNull();
        assertThat(DateTimeMx.toDate(LocalDateTime.now(), null)).isNotNull();
    }

    @Test
    void testToCalendar() {
        assertThat(DateTimeMx.toCalendar(null, null)).isNull();
        assertThat(DateTimeMx.toCalendar(Instant.now(), null)).isNotNull();
        assertThat(DateTimeMx.toCalendar(LocalDate.now(), null)).isNotNull();
        assertThat(DateTimeMx.toCalendar(LocalTime.now(), null)).isNotNull();
        assertThat(DateTimeMx.toCalendar(LocalDateTime.now(), null)).isNotNull();
    }
}
