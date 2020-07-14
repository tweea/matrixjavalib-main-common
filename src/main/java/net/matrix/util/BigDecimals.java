/*
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.math.BigDecimal;

/**
 * BigDecimal 工具类。
 */
public final class BigDecimals {
    /**
     * 阻止实例化。
     */
    private BigDecimals() {
    }

    /**
     * 比较两个 BigDecimal，使用 compareTo()。
     * 
     * @param bd1
     *     一个 BigDecimal
     * @param bd2
     *     另一个 BigDecimal
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
