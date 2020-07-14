/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.lang;

/**
 * 可以重置状态的对象。
 */
public interface Resettable {
    /**
     * 重置对象状态为原始状态。
     */
    void reset();
}
