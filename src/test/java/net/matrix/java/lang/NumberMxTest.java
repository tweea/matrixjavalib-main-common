/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NumberMxTest {
    @Test
    void testToInt() {
        assertThat(NumberMx.toInt(null)).isEqualTo(0);
        assertThat(NumberMx.toInt("  \n")).isEqualTo(0);
        assertThat(NumberMx.toInt("5")).isEqualTo(5);
    }

    @Test
    void testToInt_defaultValue() {
        assertThat(NumberMx.toInt(null, 1)).isEqualTo(1);
        assertThat(NumberMx.toInt("  \n", 1)).isEqualTo(1);
        assertThat(NumberMx.toInt("5", 1)).isEqualTo(5);
    }

    @Test
    void testToLong() {
        assertThat(NumberMx.toLong(null)).isEqualTo(0);
        assertThat(NumberMx.toLong("  \n")).isEqualTo(0);
        assertThat(NumberMx.toLong("5")).isEqualTo(5);
    }

    @Test
    void testToLong_defaultValue() {
        assertThat(NumberMx.toLong(null, 1)).isEqualTo(1);
        assertThat(NumberMx.toLong("  \n", 1)).isEqualTo(1);
        assertThat(NumberMx.toLong("5", 1)).isEqualTo(5);
    }

    @Test
    void testToFloat() {
        assertThat(NumberMx.toFloat(null)).isEqualTo(0);
        assertThat(NumberMx.toFloat("  \n")).isEqualTo(0);
        assertThat(NumberMx.toFloat("5")).isEqualTo(5);
    }

    @Test
    void testToFloat_defaultValue() {
        assertThat(NumberMx.toFloat(null, 1)).isEqualTo(1);
        assertThat(NumberMx.toFloat("  \n", 1)).isEqualTo(1);
        assertThat(NumberMx.toFloat("5", 1)).isEqualTo(5);
    }

    @Test
    void testToDouble() {
        assertThat(NumberMx.toDouble(null)).isEqualTo(0);
        assertThat(NumberMx.toDouble("  \n")).isEqualTo(0);
        assertThat(NumberMx.toDouble("5")).isEqualTo(5);
    }

    @Test
    void testToDouble_defaultValue() {
        assertThat(NumberMx.toDouble(null, 1)).isEqualTo(1);
        assertThat(NumberMx.toDouble("  \n", 1)).isEqualTo(1);
        assertThat(NumberMx.toDouble("5", 1)).isEqualTo(5);
    }

    @Test
    void testParseInteger() {
        assertThat(NumberMx.parseInteger(null)).isNull();
        assertThat(NumberMx.parseInteger("  \n")).isNull();
        assertThat(NumberMx.parseInteger("5")).isEqualTo(5);
    }

    @Test
    void testParseInteger_defaultValue() {
        assertThat(NumberMx.parseInteger(null, 1)).isEqualTo(1);
        assertThat(NumberMx.parseInteger("  \n", 1)).isEqualTo(1);
        assertThat(NumberMx.parseInteger("5", 1)).isEqualTo(5);
    }

    @Test
    void testParseLong() {
        assertThat(NumberMx.parseLong(null)).isNull();
        assertThat(NumberMx.parseLong("  \n")).isNull();
        assertThat(NumberMx.parseLong("5")).isEqualTo(5);
    }

    @Test
    void testParseLong_defaultValue() {
        assertThat(NumberMx.parseLong(null, 1L)).isEqualTo(1);
        assertThat(NumberMx.parseLong("  \n", 1L)).isEqualTo(1);
        assertThat(NumberMx.parseLong("5", 1L)).isEqualTo(5);
    }

    @Test
    void testParseFloat() {
        assertThat(NumberMx.parseFloat(null)).isNull();
        assertThat(NumberMx.parseFloat("  \n")).isNull();
        assertThat(NumberMx.parseFloat("5")).isEqualTo(5);
    }

    @Test
    void testParseFloat_defaultValue() {
        assertThat(NumberMx.parseFloat(null, 1F)).isEqualTo(1);
        assertThat(NumberMx.parseFloat("  \n", 1F)).isEqualTo(1);
        assertThat(NumberMx.parseFloat("5", 1F)).isEqualTo(5);
    }

    @Test
    void testParseDouble() {
        assertThat(NumberMx.parseDouble(null)).isNull();
        assertThat(NumberMx.parseDouble("  \n")).isNull();
        assertThat(NumberMx.parseDouble("5")).isEqualTo(5);
    }

    @Test
    void testParseDouble_defaultValue() {
        assertThat(NumberMx.parseDouble(null, 1D)).isEqualTo(1);
        assertThat(NumberMx.parseDouble("  \n", 1D)).isEqualTo(1);
        assertThat(NumberMx.parseDouble("5", 1D)).isEqualTo(5);
    }

    @Test
    void testParseBigInteger() {
        assertThat(NumberMx.parseBigInteger(null)).isNull();
        assertThat(NumberMx.parseBigInteger("  \n")).isNull();
        assertThat(NumberMx.parseBigInteger("5")).isEqualTo("5");
    }

    @Test
    void testParseBigInteger_defaultValue() {
        assertThat(NumberMx.parseBigInteger(null, BigInteger.ONE)).isEqualTo(1);
        assertThat(NumberMx.parseBigInteger("  \n", BigInteger.ONE)).isEqualTo(1);
        assertThat(NumberMx.parseBigInteger("5", BigInteger.ONE)).isEqualTo(5);
    }

    @Test
    void testParseBigDecimal() {
        assertThat(NumberMx.parseBigDecimal(null)).isNull();
        assertThat(NumberMx.parseBigDecimal("  \n")).isNull();
        assertThat(NumberMx.parseBigDecimal("5")).isEqualTo("5");
    }

    @Test
    void testParseBigDecimal_defaultValue() {
        assertThat(NumberMx.parseBigDecimal(null, BigDecimal.ONE)).isEqualTo("1");
        assertThat(NumberMx.parseBigDecimal("  \n", BigDecimal.ONE)).isEqualTo("1");
        assertThat(NumberMx.parseBigDecimal("5", BigDecimal.ONE)).isEqualTo("5");
    }

    @Test
    void testZeroIfNull_Integer() {
        assertThat(NumberMx.zeroIfNull((Integer) null)).isEqualTo(0);
        assertThat(NumberMx.zeroIfNull(5)).isEqualTo(5);
    }

    @Test
    void testZeroIfNull_Long() {
        assertThat(NumberMx.zeroIfNull((Long) null)).isEqualTo(0);
        assertThat(NumberMx.zeroIfNull(5L)).isEqualTo(5);
    }

    @Test
    void testZeroIfNull_Float() {
        assertThat(NumberMx.zeroIfNull((Float) null)).isEqualTo(0);
        assertThat(NumberMx.zeroIfNull(5F)).isEqualTo(5);
    }

    @Test
    void testZeroIfNull_Double() {
        assertThat(NumberMx.zeroIfNull((Double) null)).isEqualTo(0);
        assertThat(NumberMx.zeroIfNull(5D)).isEqualTo(5);
    }

    @Test
    void testZeroIfNull_BigInteger() {
        assertThat(NumberMx.zeroIfNull((BigInteger) null)).isEqualTo(BigInteger.ZERO);
        assertThat(NumberMx.zeroIfNull(BigInteger.valueOf(5))).isEqualTo("5");
    }

    @Test
    void testZeroIfNull_BigDecimal() {
        assertThat(NumberMx.zeroIfNull((BigDecimal) null)).isEqualTo(BigDecimal.ZERO);
        assertThat(NumberMx.zeroIfNull(BigDecimal.valueOf(5))).isEqualTo("5");
    }
}
