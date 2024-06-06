/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util;

import java.util.Collections;
import java.util.Enumeration;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnumerationIterableTest {
    @Test
    void testIterator() {
        Enumeration<String> enumeration = Collections.enumeration(Collections.singletonList("a"));

        EnumerationIterable<String> iterable = new EnumerationIterable<>(enumeration);
        assertThat(iterable).containsExactly("a");
    }
}
