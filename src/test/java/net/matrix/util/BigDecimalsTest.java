/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class BigDecimalsTest {
    @Test
    public void equals() {
        Assertions.assertThat(BigDecimals.equals(new BigDecimal("1"), new BigDecimal("1.0"))).isTrue();
        Assertions.assertThat(BigDecimals.equals(new BigDecimal("1"), new BigDecimal("2.0"))).isFalse();
    }
}
