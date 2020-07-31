/*
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.util.Iterator;

/**
 * 把 {@link Iterator} 接口转化为 {@link Iterable} 接口。
 */
public class IterableIterator<E>
    implements Iterable<E> {
    /**
     * 目标 {@link Iterator}。
     */
    private final Iterator<E> it;

    /**
     * 用已有 {@link Iterator} 构造实例。
     */
    public IterableIterator(final Iterator<E> it) {
        this.it = it;
    }

    @Override
    public Iterator<E> iterator() {
        return it;
    }
}
