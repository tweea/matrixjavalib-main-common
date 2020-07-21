/*
 * Copyright(C) 2011 matrix
 * All right reserved.
 */
package net.matrix.text;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DateFormatHelperTest {
    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";

    @Test
    public void testFormatDateString() {
        assertThat(DateFormatHelper.format(new GregorianCalendar(2011, 0, 1, 1, 1, 1).getTime(), ISO_DATE_FORMAT)).isEqualTo("2011-01-01");
    }

    @Test
    public void testFormatCalendarString() {
        assertThat(DateFormatHelper.format(new GregorianCalendar(2011, 0, 1, 1, 1, 1), ISO_DATE_FORMAT)).isEqualTo("2011-01-01");
    }

    @Test
    public void testFormatTime() {
        assertThat(DateFormatHelper.format(new GregorianCalendar(2011, 0, 1, 1, 1, 1).getTime().getTime(), ISO_DATE_FORMAT)).isEqualTo("2011-01-01");
    }

    @Test
    public void testParseString() {
        Calendar bd1 = DateFormatHelper.parse("2011-12-01T13:15:35");
        Calendar bd2 = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
        assertThat(bd1).isEqualTo(bd2);
    }

    @Test
    public void testParseStringString() {
        Calendar bd1 = DateFormatHelper.parse("2011-12-01", "yyyy-MM-dd");
        Calendar bd2 = new GregorianCalendar(2011, 11, 1);
        assertThat(bd1).isEqualTo(bd2);
    }

    @Test
    public void testToString() {
        GregorianCalendar bd = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
        assertThat(DateFormatHelper.toString(bd)).isEqualTo("2011-12-01T13:15:35");
    }

    @Test
    public void testToStringString() {
        GregorianCalendar bd = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
        assertThat(DateFormatHelper.format(bd, "HH:mm:ss")).isEqualTo("13:15:35");
    }

    @Test
    public void testToDisplayStringStringStringString() {
        GregorianCalendar bd = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
        assertThat(DateFormatHelper.toDisplayString(bd, "a", "b", "c")).isEqualTo("2011a12b01c");
    }

    @Test
    public void testToDisplayString() {
        GregorianCalendar bd = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
        assertThat(DateFormatHelper.toDisplayString(bd)).isEqualTo("2011-12-01");
    }

    @Test
    public void testToChineseString() {
        GregorianCalendar bd = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
        assertThat(DateFormatHelper.toChineseString(bd)).isEqualTo("2011年12月01日");
    }
}
