/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util;

import java.util.Enumeration;
import java.util.Iterator;

import org.apache.commons.collections4.iterators.EnumerationIterator;

/**
 * 把 {@link Enumeration} 接口转换为 {@link Iterable} 接口。
 */
public class EnumerationIterable<E>
    implements Iterable<E> {
    /**
     * 被转换的 {@link Enumeration}。
     */
    private Enumeration<? extends E> enumeration;

    /**
     * 构造器。
     * 
     * @param enumeration
     *     被转换的 {@link Enumeration}。
     */
    public EnumerationIterable(Enumeration<? extends E> enumeration) {
        this.enumeration = enumeration;
    }

    @Override
    public Iterator<E> iterator() {
        return new EnumerationIterator(enumeration);
    }
}
