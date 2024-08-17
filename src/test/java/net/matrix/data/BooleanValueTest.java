/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.data;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BooleanValueTest {
    @Test
    void testNew() {
        BooleanValue bv = new BooleanValue("1", "2");
        assertThat(bv.getTrueValue()).isEqualTo("1");
        assertThat(bv.getFalseValue()).isEqualTo("2");
        assertThat(bv.getNullValue()).isNull();
    }

    @Test
    void testNew_nullValue() {
        BooleanValue bv = new BooleanValue("1", "2", "3");
        assertThat(bv.getTrueValue()).isEqualTo("1");
        assertThat(bv.getFalseValue()).isEqualTo("2");
        assertThat(bv.getNullValue()).isEqualTo("3");
    }

    @Test
    void testIsTrue() {
        BooleanValue bv = new BooleanValue("1", "2", "3");
        assertThat(bv.isTrue(null)).isFalse();
        assertThat(bv.isTrue("1")).isTrue();
        assertThat(bv.isTrue("2")).isFalse();
        assertThat(bv.isTrue("3")).isFalse();
    }

    @Test
    void testIsTrueOrNull() {
        BooleanValue bv = new BooleanValue("1", "2", "3");
        assertThat(bv.isTrueOrNull(null)).isTrue();
        assertThat(bv.isTrueOrNull("1")).isTrue();
        assertThat(bv.isTrueOrNull("2")).isFalse();
        assertThat(bv.isTrueOrNull("3")).isTrue();
    }

    @Test
    void testIsFalse() {
        BooleanValue bv = new BooleanValue("1", "2", "3");
        assertThat(bv.isFalse(null)).isFalse();
        assertThat(bv.isFalse("1")).isFalse();
        assertThat(bv.isFalse("2")).isTrue();
        assertThat(bv.isFalse("3")).isFalse();
    }

    @Test
    void testIsFalseOrNull() {
        BooleanValue bv = new BooleanValue("1", "2", "3");
        assertThat(bv.isFalseOrNull(null)).isTrue();
        assertThat(bv.isFalseOrNull("1")).isFalse();
        assertThat(bv.isFalseOrNull("2")).isTrue();
        assertThat(bv.isFalseOrNull("3")).isTrue();
    }

    @Test
    void testToBoolean() {
        BooleanValue bv = new BooleanValue("1", "2", "3");
        assertThat(bv.toBoolean(null)).isNull();
        assertThat(bv.toBoolean("1")).isTrue();
        assertThat(bv.toBoolean("2")).isFalse();
        assertThat(bv.toBoolean("3")).isNull();
    }

    @Test
    void testToValue() {
        BooleanValue bv = new BooleanValue("1", "2", "3");
        assertThat(bv.toValue(null)).isEqualTo("3");
        assertThat(bv.toValue(Boolean.TRUE)).isEqualTo("1");
        assertThat(bv.toValue(Boolean.FALSE)).isEqualTo("2");
    }
}
