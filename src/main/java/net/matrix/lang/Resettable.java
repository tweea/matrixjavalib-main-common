/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

/**
 * 可以重置到初始状态的对象。
 */
public interface Resettable {
    /**
     * 重置到初始状态。
     */
    void reset();
}
