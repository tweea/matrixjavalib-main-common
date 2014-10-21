/*
 * $Id: DateFormatHelperTest.java 349 2012-03-11 16:22:44Z tweea $
 * Copyright(C) 2011 matrix
 * All right reserved.
 */
package net.matrix.text;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

public class DateFormatHelperTest {
	public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";

	@Test
	public void testFormatDateString() {
		Assert.assertEquals("2011-01-01", DateFormatHelper.format(new GregorianCalendar(2011, 0, 1, 1, 1, 1).getTime(), ISO_DATE_FORMAT));
	}

	@Test
	public void testFormatCalendarString() {
		Assert.assertEquals("2011-01-01", DateFormatHelper.format(new GregorianCalendar(2011, 0, 1, 1, 1, 1), ISO_DATE_FORMAT));
	}

	@Test
	public void testFormatTime() {
		Assert.assertEquals("2011-01-01", DateFormatHelper.format(new GregorianCalendar(2011, 0, 1, 1, 1, 1).getTime().getTime(), ISO_DATE_FORMAT));
	}

	@Test
	public void testParseString() {
		Calendar bd1 = DateFormatHelper.parse("2011-12-01T13:15:35");
		Calendar bd2 = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
		Assert.assertEquals(bd2, bd1);
	}

	@Test
	public void testParseStringString() {
		Calendar bd1 = DateFormatHelper.parse("2011-12-01", "yyyy-MM-dd");
		Calendar bd2 = new GregorianCalendar(2011, 11, 1);
		Assert.assertEquals(bd2, bd1);
	}

	@Test
	public void testToString() {
		GregorianCalendar bd = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
		Assert.assertEquals("2011-12-01T13:15:35", DateFormatHelper.toString(bd));
	}

	@Test
	public void testToStringString() {
		GregorianCalendar bd = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
		Assert.assertEquals("13:15:35", DateFormatHelper.format(bd, "HH:mm:ss"));
	}

	@Test
	public void testToDisplayStringStringStringString() {
		GregorianCalendar bd = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
		Assert.assertEquals("2011a12b01c", DateFormatHelper.toDisplayString(bd, "a", "b", "c"));
	}

	@Test
	public void testToDisplayString() {
		GregorianCalendar bd = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
		Assert.assertEquals("2011-12-01", DateFormatHelper.toDisplayString(bd));
	}

	@Test
	public void testToChineseString() {
		GregorianCalendar bd = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
		Assert.assertEquals("2011年12月01日", DateFormatHelper.toChineseString(bd));
	}
}
