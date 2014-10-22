/*
 * 版权所有 2013 Matrix。
 * 保留所有权利。
 */
package net.matrix.text;

import java.util.Locale;

/**
 * 区域工具。
 */
public final class Locales {
	/**
	 * 保存当前设定区域。
	 */
	private static ThreadLocal<Locale> currentHolder = new ThreadLocal<Locale>();

	/**
	 * 阻止实例化。
	 */
	private Locales() {
	}

	/**
	 * 获取当前设定区域。
	 * 
	 * @return 当前设定区域
	 */
	public static Locale current() {
		Locale current = currentHolder.get();
		if (current == null) {
			current = Locale.getDefault();
			currentHolder.set(current);
		}
		return current;
	}

	/**
	 * 设定当前区域。
	 * 
	 * @param current
	 *            当前设定区域
	 */
	public static void current(final Locale current) {
		currentHolder.set(current);
	}
}
