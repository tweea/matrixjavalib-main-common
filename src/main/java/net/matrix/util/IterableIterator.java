/*
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.util.Iterator;

/**
 * 把 Iterator 接口转化为一个 Iterable 接口。
 * 
 * @param <E>
 *     内容类型
 */
public class IterableIterator<E>
    implements Iterable<E> {
    /**
     * 目标 Iterator。
     */
    private final Iterator<E> it;

    /**
     * 用已有 Iterator 构造。
     * 
     * @param it
     *     Iterator
     */
    public IterableIterator(final Iterator<E> it) {
        this.it = it;
    }

    @Override
    public Iterator<E> iterator() {
        return it;
    }
}
