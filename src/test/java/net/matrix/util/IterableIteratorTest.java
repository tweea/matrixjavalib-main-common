/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.util;

import java.util.Collections;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IterableIteratorTest {
    @Test
    public void testIterableIterator() {
        Iterator<String> iterator = Collections.singletonList("a").iterator();

        IterableIterator<String> iterableIterator = new IterableIterator<>(iterator);
        assertThat(iterableIterator).containsExactly("a");
    }
}
