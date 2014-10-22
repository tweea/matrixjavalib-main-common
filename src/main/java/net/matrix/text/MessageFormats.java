/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.text;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 消息格式化工具。
 */
public final class MessageFormats {
	/**
	 * 阻止实例化。
	 */
	private MessageFormats() {
	}

	/**
	 * 当出现错误后，将消息格式化为一般形式。
	 * 
	 * @param pattern
	 *            键值
	 * @param arguments
	 *            参数
	 * @return 消息字符串
	 */
	public static String formatFallback(final String pattern, final Object... arguments) {
		StringBuilder sb = new StringBuilder(pattern);
		for (Object argument : arguments) {
			sb.append(", ");
			sb.append(argument);
		}
		return sb.toString();
	}

	/**
	 * 格式化消息。
	 * 
	 * @param pattern
	 *            格式
	 * @param locale
	 *            区域
	 * @param arguments
	 *            参数
	 * @return 消息字符串
	 */
	public static String format(final String pattern, final Locale locale, final Object... arguments) {
		MessageFormat format = new MessageFormat(pattern, locale);
		return format.format(arguments);
	}

	/**
	 * 格式化消息。
	 * 
	 * @param bundle
	 *            资源
	 * @param key
	 *            键值
	 * @param arguments
	 *            参数
	 * @return 消息字符串
	 */
	public static String format(final ResourceBundle bundle, final String key, final Object... arguments) {
		String pattern = ResourceBundles.getProperty(bundle, key);
		if (arguments.length == 0) {
			return pattern;
		}
		if (pattern.equals(key)) {
			return formatFallback(pattern, arguments);
		}
		return format(pattern, bundle.getLocale(), arguments);
	}
}
