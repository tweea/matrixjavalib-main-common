/*
 * $Id: Objects2.java 683 2013-09-04 08:19:39Z tweea@263.net $
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.lang;

/**
 * 对象操作实用工具。
 */
public final class Objects2 {
	/**
	 * 阻止实例化。
	 */
	private Objects2() {
	}

	/**
	 * 如果对象值是 null 返回替代对象，否则返回对象本身。
	 * 
	 * @param value
	 *            对象
	 * @param replacement
	 *            替代对象
	 * @param <T>
	 *            对象类型
	 * @return 结果
	 */
	public static <T> T isNull(final T value, final T replacement) {
		if (value == null) {
			return replacement;
		}
		return value;
	}

	/**
	 * 如果两个对象中有 null 值或两个对象相同，返回 null，否则返回对象 1。
	 * 
	 * @param value
	 *            对象 1
	 * @param value2
	 *            对象 2
	 * @param <T>
	 *            对象类型
	 * @return 结果
	 */
	public static <T> T nullIf(final T value, final T value2) {
		if (value == null || value2 == null) {
			return null;
		}
		if (value.equals(value2)) {
			return null;
		}
		return value;
	}
}
