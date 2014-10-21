/*
 * $Id: IterableEnumeration.java 800 2013-12-26 06:22:14Z tweea@263.net $
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.util.Enumeration;
import java.util.Iterator;

import org.apache.commons.collections4.iterators.EnumerationIterator;

/**
 * 把 Enumeration 接口转化为一个 Iterable 接口。
 * 
 * @param <E>
 *            内容类型
 */
public class IterableEnumeration<E>
	implements Iterable<E> {
	/**
	 * 目标 Iterator。
	 */
	private final Iterator<E> it;

	/**
	 * 用已有 Enumeration 构造。
	 * 
	 * @param enumeration
	 *            Enumeration
	 */
	public IterableEnumeration(final Enumeration<E> enumeration) {
		this.it = new EnumerationIterator(enumeration);
	}

	@Override
	public Iterator<E> iterator() {
		return it;
	}
}
