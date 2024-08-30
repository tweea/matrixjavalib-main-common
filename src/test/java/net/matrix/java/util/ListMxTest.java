/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ListMxTest {
    @Test
    void testGetFirst() {
        assertThat(ListMx.getFirst((List) null)).isNull();
        assertThat(ListMx.getFirst(Arrays.asList("a"))).isEqualTo("a");
        assertThat(ListMx.getFirst(Arrays.asList("a", "b"))).isEqualTo("a");
    }

    @Test
    void testGetLast() {
        assertThat(ListMx.getLast((List) null)).isNull();
        assertThat(ListMx.getLast(Arrays.asList("a"))).isEqualTo("a");
        assertThat(ListMx.getLast(Arrays.asList("a", "b"))).isEqualTo("b");
    }

    @Test
    void testGet() {
        assertThat(ListMx.get((List) null, 0)).isNull();
        assertThat(ListMx.get(Arrays.asList("a"), 0)).isEqualTo("a");
        assertThat(ListMx.get(Arrays.asList("a"), -1)).isNull();
        assertThat(ListMx.get(Arrays.asList("a"), 1)).isNull();
        assertThat(ListMx.get(Arrays.asList("a", "b", "c"), 1)).isEqualTo("b");
    }
}
