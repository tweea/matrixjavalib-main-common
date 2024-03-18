/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.util;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PatternPoolTest {
    @Test
    public void testOf() {
        String regex = "a+";
        PatternPool pool = new PatternPool();

        Pattern pattern = pool.of(regex);
        assertThat(pool.of(regex)).isSameAs(pattern);
        assertThat(pattern.matcher("aaa").matches()).isTrue();
        assertThat(pattern.matcher("aab").matches()).isFalse();
    }

    @Test
    public void testClear() {
        String regex = "a+";
        PatternPool pool = new PatternPool();
        Pattern pattern = pool.of(regex);

        pool.clear();
        assertThat(pool.of(regex)).isNotSameAs(pattern);
    }
}
