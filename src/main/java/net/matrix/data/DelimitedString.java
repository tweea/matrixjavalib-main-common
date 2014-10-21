/*
 * $Id: DelimitedString.java 682 2013-09-04 08:07:49Z tweea@263.net $
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang3.StringUtils;

/**
 * 表示一种特定格式的字符串，其内容由特定分割符分割成多个部分。默认的分隔符是逗号：“,”。
 */
public class DelimitedString
	implements List<String> {
	/**
	 * 默认的分隔符。
	 */
	public static final String DEFAULT_DELIMITER = ",";

	/**
	 * 分隔符。
	 */
	private String delimiter;

	/**
	 * 实际内容。
	 */
	private List<String> content;

	/**
	 * 构造一个使用默认分隔符的空字符串。
	 */
	public DelimitedString() {
		this.delimiter = DEFAULT_DELIMITER;
		this.content = new ArrayList<String>();
	}

	/**
	 * 构造一个使用默认分隔符的字符串。
	 * 
	 * @param value
	 *            默认分隔符分割的字符串
	 */
	public DelimitedString(final String value) {
		this(value, DEFAULT_DELIMITER);
	}

	/**
	 * 构造一个使用指定分隔符的字符串。
	 * 
	 * @param value
	 *            字符串
	 * @param delimiter
	 *            分隔符
	 */
	public DelimitedString(final String value, final String delimiter) {
		this.delimiter = delimiter;
		this.content = new ArrayList<String>();
		String[] array = value.split(delimiter, -1);
		for (String item : array) {
			content.add(StringUtils.trim(item));
		}
	}

	/**
	 * 构造一个使用默认分隔符的字符串，使用已有的字符串数组作为内容。
	 * 
	 * @param values
	 *            已有的字符串数组
	 */
	public DelimitedString(final String[] values) {
		this(values, DEFAULT_DELIMITER);
	}

	/**
	 * 构造一个使用指定分隔符的字符串，使用已有的字符串数组作为内容。
	 * 
	 * @param values
	 *            已有的字符串数组
	 * @param delimiter
	 *            分隔符
	 */
	public DelimitedString(final String[] values, final String delimiter) {
		this.delimiter = delimiter;
		this.content = new ArrayList<String>();
		for (String item : values) {
			content.add(StringUtils.trim(item));
		}
	}

	/**
	 * 构造一个使用默认分隔符的字符串，使用已有的字符串列表作为内容。
	 * 
	 * @param list
	 *            已有的字符串列表
	 */
	public DelimitedString(final List<String> list) {
		this(list, DEFAULT_DELIMITER);
	}

	/**
	 * 构造一个使用指定分隔符的字符串，使用已有的字符串列表作为内容。
	 * 
	 * @param list
	 *            已有的字符串列表
	 * @param delimiter
	 *            分隔符
	 */
	public DelimitedString(final List<String> list, final String delimiter) {
		this.delimiter = delimiter;
		this.content = new ArrayList<String>();
		for (String item : list) {
			content.add(StringUtils.trim(item));
		}
	}

	/**
	 * @return the delimiter
	 */
	public String getDelimiter() {
		return delimiter;
	}

	/**
	 * @param delimiter
	 *            the delimiter to set
	 */
	public void setDelimiter(final String delimiter) {
		this.delimiter = delimiter;
	}

	@Override
	public void add(final int index, final String element) {
		content.add(index, element);
	}

	@Override
	public boolean add(final String o) {
		return content.add(o);
	}

	@Override
	public boolean addAll(final Collection<? extends String> c) {
		return content.addAll(c);
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends String> c) {
		return content.addAll(index, c);
	}

	@Override
	public void clear() {
		content.clear();
	}

	@Override
	public boolean contains(final Object obj) {
		return content.contains(obj);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return content.containsAll(c);
	}

	@Override
	public boolean equals(final Object obj) {
		return content.equals(obj);
	}

	@Override
	public String get(final int i) {
		return content.get(i);
	}

	@Override
	public int hashCode() {
		return content.hashCode();
	}

	@Override
	public int indexOf(final Object obj) {
		return content.indexOf(obj);
	}

	@Override
	public boolean isEmpty() {
		return content.isEmpty();
	}

	@Override
	public Iterator<String> iterator() {
		return content.iterator();
	}

	@Override
	public int lastIndexOf(final Object obj) {
		return content.lastIndexOf(obj);
	}

	@Override
	public ListIterator<String> listIterator() {
		return content.listIterator();
	}

	@Override
	public ListIterator<String> listIterator(final int i) {
		return content.listIterator(i);
	}

	@Override
	public String remove(final int i) {
		return content.remove(i);
	}

	@Override
	public boolean remove(final Object obj) {
		return content.remove(obj);
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		return content.removeAll(c);
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		return content.retainAll(c);
	}

	@Override
	public String set(final int index, final String element) {
		return content.set(index, element);
	}

	@Override
	public int size() {
		return content.size();
	}

	@Override
	public List<String> subList(final int i, final int j) {
		return new DelimitedString(content.subList(i, j), delimiter);
	}

	@Override
	public Object[] toArray() {
		return content.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return content.toArray(a);
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < content.size(); i++) {
			buffer.append(content.get(i));
			if (i < content.size() - 1) {
				buffer.append(delimiter);
			}
		}
		return buffer.toString();
	}
}
