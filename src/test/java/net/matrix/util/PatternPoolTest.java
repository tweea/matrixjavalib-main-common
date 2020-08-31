/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.util;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PatternPoolTest {
    @Test
    public void testForPattern() {
        String patternString = "a+";
        PatternPool pool = new PatternPool();

        Pattern pattern = pool.forPattern(patternString);
        assertThat(pattern).isSameAs(pool.forPattern(patternString));
        assertThat(pattern.matcher("aaa").matches()).isTrue();
        assertThat(pattern.matcher("aab").matches()).isFalse();
    }

    @Test
    public void testClear() {
        String patternString = "a+";
        PatternPool pool = new PatternPool();
        Pattern pattern = pool.forPattern(patternString);

        pool.clear();
        assertThat(pattern).isNotSameAs(pool.forPattern(patternString));
    }
}
