/*
 * 版权所有 2013 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Objects2Test {
    @Test
    public void testIsNull() {
        assertThat(Objects2.isNull("A", "B")).isEqualTo("A");
        assertThat(Objects2.isNull(null, "B")).isEqualTo("B");
    }

    @Test
    public void testNullIf() {
        Object nullValue = null;
        assertThat(Objects2.nullIf(nullValue, nullValue)).isNull();
        assertThat(Objects2.nullIf("A", "A")).isNull();
        assertThat(Objects2.nullIf("A", "B")).isEqualTo("A");
    }
}
