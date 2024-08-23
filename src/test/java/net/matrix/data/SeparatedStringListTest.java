/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.data;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SeparatedStringListTest {
    @Test
    void testNew() {
        SeparatedStringList list = new SeparatedStringList();
        assertThat(list.getDelimiter()).isEqualTo(SeparatedStringList.DEFAULT_DELIMITER);
        assertThat(list).isEmpty();
        assertThat(list.toString()).isEmpty();
    }

    @Test
    void testNew_string() {
        SeparatedStringList list = new SeparatedStringList("a,bc,d");
        assertThat(list.getDelimiter()).isEqualTo(SeparatedStringList.DEFAULT_DELIMITER);
        assertThat(list).hasSize(3);
        assertThat(list).hasToString("a,bc,d");
    }

    @Test
    void testNew_stringWithDelimiter() {
        SeparatedStringList list = new SeparatedStringList("a=bc=d", "=");
        assertThat(list.getDelimiter()).isEqualTo("=");
        assertThat(list).hasSize(3);
        assertThat(list).hasToString("a=bc=d");
    }

    @Test
    void testNew_stringArray() {
        SeparatedStringList list = new SeparatedStringList(new String[] {
            "a", "bc", "d"
        });
        assertThat(list.getDelimiter()).isEqualTo(SeparatedStringList.DEFAULT_DELIMITER);
        assertThat(list).hasSize(3);
        assertThat(list).hasToString("a,bc,d");
    }

    @Test
    void testNew_stringArrayWithDelimiter() {
        SeparatedStringList list = new SeparatedStringList(new String[] {
            "a", "bc", "d"
        }, "+");
        assertThat(list.getDelimiter()).isEqualTo("+");
        assertThat(list).hasSize(3);
        assertThat(list).hasToString("a+bc+d");
    }

    @Test
    void testNew_stringList() {
        SeparatedStringList list = new SeparatedStringList(Arrays.asList("a", "bc", "d"));
        assertThat(list.getDelimiter()).isEqualTo(SeparatedStringList.DEFAULT_DELIMITER);
        assertThat(list).hasSize(3);
        assertThat(list).hasToString("a,bc,d");
    }

    @Test
    void testNew_stringListWithDelimiter() {
        SeparatedStringList list = new SeparatedStringList(Arrays.asList("a", "bc", "d"), "-");
        assertThat(list.getDelimiter()).isEqualTo("-");
        assertThat(list).hasSize(3);
        assertThat(list).hasToString("a-bc-d");
    }

    @Test
    void testSubList() {
        SeparatedStringList list = new SeparatedStringList(Arrays.asList("a", "bc", "d"), "-").subList(0, 2);
        assertThat(list.getDelimiter()).isEqualTo("-");
        assertThat(list).hasSize(2);
        assertThat(list).hasToString("a-bc");
    }
}
