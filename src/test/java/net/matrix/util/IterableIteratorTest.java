/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
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
