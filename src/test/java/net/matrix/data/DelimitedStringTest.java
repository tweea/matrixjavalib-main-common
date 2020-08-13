/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.data;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DelimitedStringTest {
    @Test
    public void testDelimitedString() {
        DelimitedString list = new DelimitedString();
        assertThat(list).isEmpty();
        assertThat(list.toString()).isEmpty();
    }

    @Test
    public void testDelimitedString_string() {
        DelimitedString list = new DelimitedString("a,bc,d");
        assertThat(list).hasSize(3);
        assertThat(list.toString()).isEqualTo("a,bc,d");
    }

    @Test
    public void testDelimitedString_stringWithDelimiter() {
        DelimitedString list = new DelimitedString("a=bc=d", "=");
        assertThat(list).hasSize(3);
        assertThat(list.toString()).isEqualTo("a=bc=d");
    }

    @Test
    public void testDelimitedString_stringArray() {
        DelimitedString list = new DelimitedString(new String[] {
            "a", "bc", "d"
        });
        assertThat(list).hasSize(3);
        assertThat(list.toString()).isEqualTo("a,bc,d");
    }

    @Test
    public void testDelimitedString_stringArrayWithDelimiter() {
        DelimitedString list = new DelimitedString(new String[] {
            "a", "bc", "d"
        }, "+");
        assertThat(list).hasSize(3);
        assertThat(list.toString()).isEqualTo("a+bc+d");
    }

    @Test
    public void testDelimitedString_stringList() {
        DelimitedString list = new DelimitedString(Arrays.asList("a", "bc", "d"));
        assertThat(list).hasSize(3);
        assertThat(list.toString()).isEqualTo("a,bc,d");
    }

    @Test
    public void testDelimitedString_stringListWithDelimiter() {
        DelimitedString list = new DelimitedString(Arrays.asList("a", "bc", "d"), "-");
        assertThat(list).hasSize(3);
        assertThat(list.toString()).isEqualTo("a-bc-d");
    }

    @Test
    public void testSubList() {
        DelimitedString list = new DelimitedString(Arrays.asList("a", "bc", "d"), "-").subList(0, 2);
        assertThat(list).hasSize(2);
        assertThat(list.toString()).isEqualTo("a-bc");
    }
}
