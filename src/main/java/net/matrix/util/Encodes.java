/*
 * $Id: Encodes.java 942 2014-04-16 14:51:01Z tweea@263.net $
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import net.matrix.lang.ImpossibleException;

/**
 * 封装各种格式的编码解码工具类。
 * 1.自行编写的，将 long 进行 base62 编码以缩短其长度
 * 2.JDK 提供的 URLEncoder
 */
public final class Encodes {
	/**
	 * Base 编码字母表。
	 */
	private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	/**
	 * 默认 URL 编码。
	 */
	private static final String DEFAULT_URL_ENCODING = "UTF-8";

	/**
	 * 阻止实例化。
	 */
	private Encodes() {
	}

	/**
	 * Base62(0_9A_Za_z) 编码数字，long->String。
	 * 
	 * @param num
	 *            数字
	 * @return 编码结果
	 */
	public static String encodeBase62(final long num) {
		long index = Math.abs(num);
		int base = BASE62.length();
		StringBuilder sb = new StringBuilder();
		for (; index > 0; index /= base) {
			sb.append(BASE62.charAt((int) (index % base)));
		}

		return sb.toString();
	}

	/**
	 * Base62(0_9A_Za_z) 解码数字，String->long。
	 * 
	 * @param str
	 *            编码结果
	 * @return 数字
	 */
	public static long decodeBase62(final String str) {
		long result = 0;
		int base = BASE62.length();
		for (int i = 0; i < str.length(); i++) {
			result += BASE62.indexOf(str.charAt(i)) * Math.pow(base, i);
		}

		return result;
	}

	/**
	 * URL 编码，默认编码为 UTF-8。
	 * 
	 * @param part
	 *            待编码字符串
	 * @return 编码字符串
	 */
	public static String urlEncode(final String part) {
		try {
			return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new ImpossibleException(e);
		}
	}

	/**
	 * URL 解码，默认编码为 UTF-8。
	 * 
	 * @param part
	 *            待解码字符串
	 * @return 解码字符串
	 */
	public static String urlDecode(final String part) {
		try {
			return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new ImpossibleException(e);
		}
	}
}
