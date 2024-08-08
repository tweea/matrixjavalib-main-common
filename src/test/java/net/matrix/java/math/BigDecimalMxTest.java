/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BigDecimalMxTest {
    @Test
    void testEquals() {
        assertThat(BigDecimalMx.equals(new BigDecimal("1"), new BigDecimal("1.0"))).isTrue();
        assertThat(BigDecimalMx.equals(new BigDecimal("1"), new BigDecimal("2.0"))).isFalse();
    }

    @Test
    void testRound() {
        assertThat(BigDecimalMx.round(null, 0, RoundingMode.HALF_UP)).isNull();
        assertThat(BigDecimalMx.round(new BigDecimal("1.1"), 0, RoundingMode.HALF_UP)).isEqualTo("1");
    }
}
