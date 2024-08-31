/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.util;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PatternPoolTest {
    @Test
    void testOf() {
        String regex = "a+";
        PatternPool pool = new PatternPool();

        Pattern pattern = pool.of(regex);
        assertThat(pool.of(regex)).isSameAs(pattern);
        assertThat(pattern.matcher("aaa").matches()).isTrue();
        assertThat(pattern.matcher("aab").matches()).isFalse();
    }

    @Test
    void testOf_flags() {
        String regex = "a+";
        PatternPool pool = new PatternPool();

        Pattern pattern = pool.of(regex, Pattern.CASE_INSENSITIVE);
        assertThat(pool.of(regex, Pattern.CASE_INSENSITIVE)).isSameAs(pattern);
        assertThat(pattern.matcher("AaA").matches()).isTrue();
        assertThat(pattern.matcher("AaB").matches()).isFalse();
    }

    @Test
    void testClear() {
        String regex = "a+";
        PatternPool pool = new PatternPool();
        Pattern pattern = pool.of(regex);

        pool.clear();
        assertThat(pool.of(regex)).isNotSameAs(pattern);
    }
}
