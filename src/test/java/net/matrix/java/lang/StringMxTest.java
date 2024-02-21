/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringMxTest {
    @Test
    public void testReplaceEach() {
        Map<String, String> replacementMap = new LinkedHashMap<>();

        replacementMap.put("a", "b");
        assertThat(StringMx.replaceEach(null, replacementMap)).isNull();
        assertThat(StringMx.replaceEach("", replacementMap)).isEqualTo("");

        replacementMap.clear();
        assertThat(StringMx.replaceEach("aba", null)).isEqualTo("aba");
        assertThat(StringMx.replaceEach("aba", replacementMap)).isEqualTo("aba");

        replacementMap.clear();
        replacementMap.put("a", null);
        assertThat(StringMx.replaceEach("aba", replacementMap)).isEqualTo("aba");

        replacementMap.clear();
        replacementMap.put("a", "");
        assertThat(StringMx.replaceEach("aba", replacementMap)).isEqualTo("b");

        replacementMap.clear();
        replacementMap.put(null, "a");
        assertThat(StringMx.replaceEach("aba", replacementMap)).isEqualTo("aba");

        replacementMap.clear();
        replacementMap.put("ab", "w");
        replacementMap.put("d", "t");
        assertThat(StringMx.replaceEach("abcde", replacementMap)).isEqualTo("wcte");

        replacementMap.clear();
        replacementMap.put("ab", "d");
        replacementMap.put("d", "t");
        assertThat(StringMx.replaceEach("abcde", replacementMap)).isEqualTo("dcte");
    }

    @Test
    public void testReplaceEachRepeatedly() {
        Map<String, String> replacementMap = new LinkedHashMap<>();

        replacementMap.put("a", "b");
        assertThat(StringMx.replaceEachRepeatedly(null, replacementMap)).isNull();
        assertThat(StringMx.replaceEachRepeatedly("", replacementMap)).isEqualTo("");

        replacementMap.clear();
        assertThat(StringMx.replaceEachRepeatedly("aba", null)).isEqualTo("aba");
        assertThat(StringMx.replaceEachRepeatedly("aba", replacementMap)).isEqualTo("aba");

        replacementMap.clear();
        replacementMap.put("a", null);
        assertThat(StringMx.replaceEachRepeatedly("aba", replacementMap)).isEqualTo("aba");

        replacementMap.clear();
        replacementMap.put("a", "");
        assertThat(StringMx.replaceEachRepeatedly("aba", replacementMap)).isEqualTo("b");

        replacementMap.clear();
        replacementMap.put(null, "a");
        assertThat(StringMx.replaceEachRepeatedly("aba", replacementMap)).isEqualTo("aba");

        replacementMap.clear();
        replacementMap.put("ab", "w");
        replacementMap.put("d", "t");
        assertThat(StringMx.replaceEachRepeatedly("abcde", replacementMap)).isEqualTo("wcte");

        replacementMap.clear();
        replacementMap.put("ab", "d");
        replacementMap.put("d", "t");
        assertThat(StringMx.replaceEachRepeatedly("abcde", replacementMap)).isEqualTo("tcte");
    }
}
