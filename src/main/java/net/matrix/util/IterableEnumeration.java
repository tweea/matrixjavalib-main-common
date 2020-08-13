/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.util;

import java.util.Enumeration;
import java.util.Iterator;

import org.apache.commons.collections4.iterators.EnumerationIterator;

/**
 * 把 {@link Enumeration} 接口转化为 {@link Iterable} 接口。
 */
public class IterableEnumeration<E>
    implements Iterable<E> {
    /**
     * 目标 {@link Iterator}。
     */
    private final Iterator<E> it;

    /**
     * 用已有 {@link Enumeration} 构造实例。
     */
    public IterableEnumeration(final Enumeration<E> enumeration) {
        this.it = new EnumerationIterator(enumeration);
    }

    @Override
    public Iterator<E> iterator() {
        return it;
    }
}
