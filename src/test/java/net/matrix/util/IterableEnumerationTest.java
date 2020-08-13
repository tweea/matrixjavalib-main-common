/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.util;

import java.util.Collections;
import java.util.Enumeration;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IterableEnumerationTest {
    @Test
    public void testIterableEnumeration() {
        Enumeration<String> enumeration = Collections.enumeration(Collections.singletonList("a"));

        IterableEnumeration<String> iterableEnumeration = new IterableEnumeration<>(enumeration);
        assertThat(iterableEnumeration).containsExactly("a");
    }
}
