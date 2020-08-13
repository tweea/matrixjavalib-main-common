/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

/**
 * 可以重置状态的对象。
 */
public interface Resettable {
    /**
     * 重置状态。
     */
    void reset();
}
