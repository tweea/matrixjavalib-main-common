/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class CalendarsTest {
	@Test
	public void isValidDate() {
		Assertions.assertThat(Calendars.isValidDate(1948, 5, 1)).isTrue();
		Assertions.assertThat(Calendars.isValidDate(2011, 10, 1)).isTrue();
		Assertions.assertThat(Calendars.isValidDate(2011, 13, 1)).isFalse();
		Assertions.assertThat(Calendars.isValidDate(2011, 2, 31)).isFalse();
	}

	@Test
	public void isLeapYear() {
		Assertions.assertThat(Calendars.isLeapYear(2012)).isTrue();
		Assertions.assertThat(Calendars.isLeapYear(2000)).isTrue();
		Assertions.assertThat(Calendars.isLeapYear(2011)).isFalse();
		Assertions.assertThat(Calendars.isLeapYear(2100)).isFalse();
	}

	@Test
	public void newDateTime() {
		Assertions.assertThat(Calendars.newDateTime((Date) null)).isNull();
	}

	@Test
	public void newLocalDate() {
		Assertions.assertThat(Calendars.newLocalDate((Date) null)).isNull();
	}

	@Test
	public void newLocalTime() {
		Assertions.assertThat(Calendars.newLocalTime((Date) null)).isNull();
	}

	@Test
	public void newLocalDateTime() {
		Assertions.assertThat(Calendars.newLocalDateTime((Date) null)).isNull();
	}
}
