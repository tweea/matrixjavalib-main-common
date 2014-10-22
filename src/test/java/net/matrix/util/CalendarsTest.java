/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.util;

import org.junit.Assert;
import org.junit.Test;

public class CalendarsTest {
	@Test
	public void isValidDate() {
		Assert.assertTrue(Calendars.isValidDate(2011, 10, 1));
		Assert.assertFalse(Calendars.isValidDate(2011, 13, 1));
		Assert.assertFalse(Calendars.isValidDate(2011, 2, 31));
	}

	@Test
	public void isLeapYear() {
		Assert.assertTrue(Calendars.isLeapYear(2012));
		Assert.assertTrue(Calendars.isLeapYear(2000));
		Assert.assertFalse(Calendars.isLeapYear(2011));
		Assert.assertFalse(Calendars.isLeapYear(2100));
	}
}
