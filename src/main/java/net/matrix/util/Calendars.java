/*
 * $Id: Calendars.java 886 2014-01-22 08:31:03Z tweea@263.net $
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.util;

import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类。
 */
public final class Calendars {
	/**
	 * 日志记录器。
	 */
	private static final Logger LOG = LoggerFactory.getLogger(Calendars.class);

	/**
	 * 阻止实例化。
	 */
	private Calendars() {
	}

	/**
	 * 校验日期的年、月、日数值是否符合历法。
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月 1-12
	 * @param day
	 *            日
	 * @return true 符合历法
	 */
	public static boolean isValidDate(final int year, final int month, final int day) {
		try {
			buildDate(year, month, day);
			return true;
		} catch (IllegalFieldValueException e) {
			if (LOG.isTraceEnabled()) {
				LOG.trace("", e);
			}
			return false;
		}
	}

	/**
	 * 构造日期对象。
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return 日期对象
	 */
	private static DateTime buildDate(final int year, final int month, final int day) {
		return new DateTime(year, month, day, 0, 0);
	}

	/**
	 * 判断某年是否闰年。
	 * 
	 * @param year
	 *            年份
	 * @return true 是闰年
	 */
	public static boolean isLeapYear(final int year) {
		return buildDate(year, 1, 1).year().isLeap();
	}
}
