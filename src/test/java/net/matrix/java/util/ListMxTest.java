/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ListMxTest {
    @Test
    void testGetFirst() {
        assertThat(ListMx.getFirst((List) null)).isNull();
        assertThat(ListMx.getFirst(List.of("a"))).isEqualTo("a");
        assertThat(ListMx.getFirst(List.of("a", "b"))).isEqualTo("a");
    }

    @Test
    void testGetLast() {
        assertThat(ListMx.getLast((List) null)).isNull();
        assertThat(ListMx.getLast(List.of("a"))).isEqualTo("a");
        assertThat(ListMx.getLast(List.of("a", "b"))).isEqualTo("b");
    }

    @Test
    void testGet() {
        assertThat(ListMx.get((List) null, 0)).isNull();
        assertThat(ListMx.get(List.of("a"), 0)).isEqualTo("a");
        assertThat(ListMx.get(List.of("a"), -1)).isNull();
        assertThat(ListMx.get(List.of("a"), 1)).isNull();
        assertThat(ListMx.get(List.of("a", "b", "c"), 1)).isEqualTo("b");
    }
}
