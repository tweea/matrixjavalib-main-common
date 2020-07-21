/*
 * Copyright(C) 2011 matrix
 * All right reserved.
 */
package net.matrix.data;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DelimitedStringTest {
    @Test
    public void testCommaSeparatedStringList() {
        List<String> list = new DelimitedString();
        assertThat(list).isEmpty();
        assertThat(list.toString()).isEmpty();
    }

    @Test
    public void testCommaSeparatedStringListString() {
        List<String> list = new DelimitedString("a,bc,d");
        assertThat(list).hasSize(3);
        assertThat(list.toString()).isEqualTo("a,bc,d");
    }

    @Test
    public void testCommaSeparatedStringListString_withDelimiter() {
        List<String> list = new DelimitedString("a=bc=d", "=");
        assertThat(list).hasSize(3);
        assertThat(list.toString()).isEqualTo("a=bc=d");
    }

    @Test
    public void testCommaSeparatedStringListStringArray() {
        List<String> list = new DelimitedString(new String[] {
            "a", "bc", "d"
        });
        assertThat(list).hasSize(3);
        assertThat(list.toString()).isEqualTo("a,bc,d");
    }

    @Test
    public void testCommaSeparatedStringListStringArray_withDelimiter() {
        List<String> list = new DelimitedString(new String[] {
            "a", "bc", "d"
        }, "+");
        assertThat(list).hasSize(3);
        assertThat(list.toString()).isEqualTo("a+bc+d");
    }

    @Test
    public void testCommaSeparatedStringListListOfString() {
        List<String> list = new DelimitedString(Arrays.asList("a", "bc", "d"));
        assertThat(list).hasSize(3);
        assertThat(list.toString()).isEqualTo("a,bc,d");
    }

    @Test
    public void testCommaSeparatedStringListListOfString_withDelimiter() {
        List<String> list = new DelimitedString(Arrays.asList("a", "bc", "d"), "-");
        assertThat(list).hasSize(3);
        assertThat(list.toString()).isEqualTo("a-bc-d");
    }

    @Test
    public void testSubList() {
        List<String> list = new DelimitedString(Arrays.asList("a", "bc", "d"), "-").subList(0, 2);
        assertThat(list).hasSize(2);
        assertThat(list.toString()).isEqualTo("a-bc");
    }
}
