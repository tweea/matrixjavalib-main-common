/*
 * Copyright(C) 2011 matrix
 * All right reserved.
 */
package net.matrix.text;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class DateFormatHelperTest {
    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";

    @Test
    public void testFormatDateString() {
        Assertions.assertThat(DateFormatHelper.format(new GregorianCalendar(2011, 0, 1, 1, 1, 1).getTime(), ISO_DATE_FORMAT)).isEqualTo("2011-01-01");
    }

    @Test
    public void testFormatCalendarString() {
        Assertions.assertThat(DateFormatHelper.format(new GregorianCalendar(2011, 0, 1, 1, 1, 1), ISO_DATE_FORMAT)).isEqualTo("2011-01-01");
    }

    @Test
    public void testFormatTime() {
        Assertions.assertThat(DateFormatHelper.format(new GregorianCalendar(2011, 0, 1, 1, 1, 1).getTime().getTime(), ISO_DATE_FORMAT)).isEqualTo("2011-01-01");
    }

    @Test
    public void testParseString() {
        Calendar bd1 = DateFormatHelper.parse("2011-12-01T13:15:35");
        Calendar bd2 = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
        Assertions.assertThat(bd1).isEqualTo(bd2);
    }

    @Test
    public void testParseStringString() {
        Calendar bd1 = DateFormatHelper.parse("2011-12-01", "yyyy-MM-dd");
        Calendar bd2 = new GregorianCalendar(2011, 11, 1);
        Assertions.assertThat(bd1).isEqualTo(bd2);
    }

    @Test
    public void testToString() {
        GregorianCalendar bd = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
        Assertions.assertThat(DateFormatHelper.toString(bd)).isEqualTo("2011-12-01T13:15:35");
    }

    @Test
    public void testToStringString() {
        GregorianCalendar bd = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
        Assertions.assertThat(DateFormatHelper.format(bd, "HH:mm:ss")).isEqualTo("13:15:35");
    }

    @Test
    public void testToDisplayStringStringStringString() {
        GregorianCalendar bd = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
        Assertions.assertThat(DateFormatHelper.toDisplayString(bd, "a", "b", "c")).isEqualTo("2011a12b01c");
    }

    @Test
    public void testToDisplayString() {
        GregorianCalendar bd = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
        Assertions.assertThat(DateFormatHelper.toDisplayString(bd)).isEqualTo("2011-12-01");
    }

    @Test
    public void testToChineseString() {
        GregorianCalendar bd = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
        Assertions.assertThat(DateFormatHelper.toChineseString(bd)).isEqualTo("2011年12月01日");
    }
}
