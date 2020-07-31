/*
 * Copyright(C) 2011 matrix
 * All right reserved.
 */
package net.matrix.text;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DateFormatHelperTest {
    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";

    public static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormat.forPattern(ISO_DATE_FORMAT);

    @Test
    public void testFormat_calendar() {
        Calendar date = new GregorianCalendar(2011, 0, 1, 1, 1, 1);
        Calendar nullDate = null;

        assertThat(DateFormatHelper.format(date, ISO_DATE_FORMAT)).isEqualTo("2011-01-01");
        assertThat(DateFormatHelper.format(nullDate, ISO_DATE_FORMAT)).isNull();

        assertThat(DateFormatHelper.format(date, ISO_DATE_FORMATTER)).isEqualTo("2011-01-01");
        assertThat(DateFormatHelper.format(nullDate, ISO_DATE_FORMATTER)).isNull();
    }

    @Test
    public void testFormat_date() {
        Date date = new GregorianCalendar(2011, 0, 1, 1, 1, 1).getTime();
        Date nullDate = null;

        assertThat(DateFormatHelper.format(date, ISO_DATE_FORMAT)).isEqualTo("2011-01-01");
        assertThat(DateFormatHelper.format(nullDate, ISO_DATE_FORMAT)).isNull();

        assertThat(DateFormatHelper.format(date, ISO_DATE_FORMATTER)).isEqualTo("2011-01-01");
        assertThat(DateFormatHelper.format(nullDate, ISO_DATE_FORMATTER)).isNull();
    }

    @Test
    public void testFormat_time() {
        long date = new GregorianCalendar(2011, 0, 1, 1, 1, 1).getTime().getTime();

        assertThat(DateFormatHelper.format(date, ISO_DATE_FORMAT)).isEqualTo("2011-01-01");

        assertThat(DateFormatHelper.format(date, ISO_DATE_FORMATTER)).isEqualTo("2011-01-01");
    }

    @Test
    public void testParseCalendar() {
        Calendar date = new GregorianCalendar(2011, 11, 1);

        assertThat(DateFormatHelper.parseCalendar("2011-12-01", ISO_DATE_FORMAT)).isEqualTo(date);
        assertThat(DateFormatHelper.parseCalendar(null, ISO_DATE_FORMAT)).isNull();

        assertThat(DateFormatHelper.parseCalendar("2011-12-01", ISO_DATE_FORMATTER)).isEqualTo(date);
        assertThat(DateFormatHelper.parseCalendar(null, ISO_DATE_FORMATTER)).isNull();
    }

    @Test
    public void testParseDate() {
        Date date = new GregorianCalendar(2011, 11, 1).getTime();

        assertThat(DateFormatHelper.parseDate("2011-12-01", ISO_DATE_FORMAT)).isEqualTo(date);
        assertThat(DateFormatHelper.parseDate(null, ISO_DATE_FORMAT)).isNull();

        assertThat(DateFormatHelper.parseDate("2011-12-01", ISO_DATE_FORMATTER)).isEqualTo(date);
        assertThat(DateFormatHelper.parseDate(null, ISO_DATE_FORMATTER)).isNull();
    }

    @Test
    public void testFormat_calendar_datetime() {
        Calendar date = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
        Calendar nullDate = null;

        assertThat(DateFormatHelper.format(date)).isEqualTo("2011-12-01T13:15:35");
        assertThat(DateFormatHelper.format(nullDate)).isNull();
    }

    @Test
    public void testFormat_date_datetime() {
        Date date = new GregorianCalendar(2011, 11, 1, 13, 15, 35).getTime();
        Date nullDate = null;

        assertThat(DateFormatHelper.format(date)).isEqualTo("2011-12-01T13:15:35");
        assertThat(DateFormatHelper.format(nullDate)).isNull();
    }

    @Test
    public void testFormat_time_datetime() {
        long date = new GregorianCalendar(2011, 11, 1, 13, 15, 35).getTime().getTime();

        assertThat(DateFormatHelper.format(date)).isEqualTo("2011-12-01T13:15:35");
    }

    @Test
    public void testFormatDate_calendar() {
        Calendar date = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
        Calendar nullDate = null;

        assertThat(DateFormatHelper.formatDate(date)).isEqualTo("2011-12-01");
        assertThat(DateFormatHelper.formatDate(nullDate)).isNull();
    }

    @Test
    public void testFormatDate_date() {
        Date date = new GregorianCalendar(2011, 11, 1, 13, 15, 35).getTime();
        Date nullDate = null;

        assertThat(DateFormatHelper.formatDate(date)).isEqualTo("2011-12-01");
        assertThat(DateFormatHelper.formatDate(nullDate)).isNull();
    }

    @Test
    public void testFormatDate_time() {
        long date = new GregorianCalendar(2011, 11, 1, 13, 15, 35).getTime().getTime();

        assertThat(DateFormatHelper.formatDate(date)).isEqualTo("2011-12-01");
    }

    @Test
    public void testFormatDate2_calendar() {
        Calendar date = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
        Calendar nullDate = null;

        assertThat(DateFormatHelper.formatDate(date, "a", "b", "c")).isEqualTo("2011a12b01c");
        assertThat(DateFormatHelper.formatDate(nullDate, "a", "b", "c")).isNull();
    }

    @Test
    public void testFormatDate2_date() {
        Date date = new GregorianCalendar(2011, 11, 1, 13, 15, 35).getTime();
        Date nullDate = null;

        assertThat(DateFormatHelper.formatDate(date, "a", "b", "c")).isEqualTo("2011a12b01c");
        assertThat(DateFormatHelper.formatDate(nullDate, "a", "b", "c")).isNull();
    }

    @Test
    public void testFormatDate2_time() {
        long date = new GregorianCalendar(2011, 11, 1, 13, 15, 35).getTime().getTime();

        assertThat(DateFormatHelper.formatDate(date, "a", "b", "c")).isEqualTo("2011a12b01c");
    }

    @Test
    public void testFormatDateChinese_calendar() {
        Calendar date = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
        Calendar nullDate = null;

        assertThat(DateFormatHelper.formatDateChinese(date)).isEqualTo("2011年12月01日");
        assertThat(DateFormatHelper.formatDateChinese(nullDate)).isNull();
    }

    @Test
    public void testFormatDateChinese_date() {
        Date date = new GregorianCalendar(2011, 11, 1, 13, 15, 35).getTime();
        Date nullDate = null;

        assertThat(DateFormatHelper.formatDateChinese(date)).isEqualTo("2011年12月01日");
        assertThat(DateFormatHelper.formatDateChinese(nullDate)).isNull();
    }

    @Test
    public void testFormatDateChinese_time() {
        long date = new GregorianCalendar(2011, 11, 1, 13, 15, 35).getTime().getTime();

        assertThat(DateFormatHelper.formatDateChinese(date)).isEqualTo("2011年12月01日");
    }

    @Test
    public void testParseCalendar_datetime() {
        Calendar date = new GregorianCalendar(2011, 11, 1, 13, 15, 35);

        assertThat(DateFormatHelper.parseCalendar("2011-12-01T13:15:35")).isEqualTo(date);
        assertThat(DateFormatHelper.parseCalendar(null)).isNull();
    }

    @Test
    public void testParseDate_datetime() {
        Date date = new GregorianCalendar(2011, 11, 1, 13, 15, 35).getTime();

        assertThat(DateFormatHelper.parseDate("2011-12-01T13:15:35")).isEqualTo(date);
        assertThat(DateFormatHelper.parseDate(null)).isNull();
    }
}
