/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.math;

import java.math.BigDecimal;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * {@link BigDecimal} 工具。
 */
@ThreadSafe
public final class BigDecimalMx {
    /**
     * 阻止实例化。
     */
    private BigDecimalMx() {
    }

    /**
     * 判断两个 {@link BigDecimal} 是否相等。
     * 
     * @param a
     *     一个 {@link BigDecimal}。
     * @param b
     *     另一个 {@link BigDecimal}。
     * @return 是否相等。
     */
    public static boolean equals(@Nullable BigDecimal a, @Nullable BigDecimal b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.compareTo(b) == 0;
    }
}
