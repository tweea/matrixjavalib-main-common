/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringMxTest {
    @Test
    void testEqualsIfEmpty() {
        assertThat(StringMx.equalsIfEmpty(null, "")).isTrue();
        assertThat(StringMx.equalsIfEmpty(null, "  \n")).isFalse();
        assertThat(StringMx.equalsIfEmpty(null, "  n")).isFalse();
    }

    @Test
    void testEqualsIfBlank() {
        assertThat(StringMx.equalsIfBlank(null, "")).isTrue();
        assertThat(StringMx.equalsIfBlank(null, "  \n")).isTrue();
        assertThat(StringMx.equalsIfBlank(null, "  n")).isFalse();
    }

    @Test
    void testEmptyIfNull() {
        assertThat(StringMx.emptyIfNull(null)).isEmpty();
        assertThat(StringMx.emptyIfNull("5")).isEqualTo("5");
    }

    @Test
    void testReplaceEach() {
        Map<String, String> replacementMap = new LinkedHashMap<>();

        replacementMap.put("a", "b");
        assertThat(StringMx.replaceEach(null, replacementMap)).isNull();
        assertThat(StringMx.replaceEach("", replacementMap)).isEmpty();

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
    void testReplaceEachRepeatedly() {
        Map<String, String> replacementMap = new LinkedHashMap<>();

        replacementMap.put("a", "b");
        assertThat(StringMx.replaceEachRepeatedly(null, replacementMap)).isNull();
        assertThat(StringMx.replaceEachRepeatedly("", replacementMap)).isEmpty();

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

    @Test
    void testJoin() {
        List<String> list = new ArrayList();
        list.add("aa");
        list.add("bb");

        String result = StringMx.join(list, "<li>", "</li>", ",");
        assertThat(result).isEqualTo("<li>aa</li>,<li>bb</li>");
    }
}
