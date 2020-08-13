/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.util;

import java.util.Date;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CalendarsTest {
    @Test
    public void testIsValidDate() {
        assertThat(Calendars.isValidDate(1948, 5, 1)).isTrue();
        assertThat(Calendars.isValidDate(2011, 10, 1)).isTrue();
        assertThat(Calendars.isValidDate(2011, 13, 1)).isFalse();
        assertThat(Calendars.isValidDate(2011, 2, 31)).isFalse();
    }

    @Test
    public void testIsLeapYear() {
        assertThat(Calendars.isLeapYear(2012)).isTrue();
        assertThat(Calendars.isLeapYear(2000)).isTrue();
        assertThat(Calendars.isLeapYear(2011)).isFalse();
        assertThat(Calendars.isLeapYear(2100)).isFalse();
    }

    @Test
    public void testNewDateTime() {
        assertThat(Calendars.newDateTime((Date) null)).isNull();
    }

    @Test
    public void testNewLocalDate() {
        assertThat(Calendars.newLocalDate((Date) null)).isNull();
    }

    @Test
    public void testNewLocalTime() {
        assertThat(Calendars.newLocalTime((Date) null)).isNull();
    }

    @Test
    public void testNewLocalDateTime() {
        assertThat(Calendars.newLocalDateTime((Date) null)).isNull();
    }
}
