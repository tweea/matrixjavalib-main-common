/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.util;

import java.math.BigDecimal;

/**
 * {@link BigDecimal} 工具。
 */
public final class BigDecimals {
    /**
     * 阻止实例化。
     */
    private BigDecimals() {
    }

    /**
     * 比较两个 {@link BigDecimal}，使用 compareTo()。
     * 
     * @param bd1
     *     第一个 {@link BigDecimal}
     * @param bd2
     *     第二个 {@link BigDecimal}
     * @return 是否相等
     */
    public static boolean equals(final BigDecimal bd1, final BigDecimal bd2) {
        if (bd1 == null && bd2 == null) {
            return true;
        }
        if (bd1 == null || bd2 == null) {
            return false;
        }
        return bd1.compareTo(bd2) == 0;
    }
}
