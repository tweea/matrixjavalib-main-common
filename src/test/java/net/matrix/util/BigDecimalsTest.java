/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BigDecimalsTest {
    @Test
    public void testEquals() {
        assertThat(BigDecimals.equals(new BigDecimal("1"), new BigDecimal("1.0"))).isTrue();
        assertThat(BigDecimals.equals(new BigDecimal("1"), new BigDecimal("2.0"))).isFalse();
    }
}
