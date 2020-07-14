/*
 * Copyright(C) 2011 matrix
 * All right reserved.
 */
package net.matrix.data;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class DelimitedStringTest {
    @Test
    public void testCommaSeparatedStringList() {
        List<String> list = new DelimitedString();
        Assertions.assertThat(list).isEmpty();
        Assertions.assertThat(list.toString()).isEmpty();
    }

    @Test
    public void testCommaSeparatedStringListString() {
        List<String> list = new DelimitedString("a,bc,d");
        Assertions.assertThat(list).hasSize(3);
        Assertions.assertThat(list.toString()).isEqualTo("a,bc,d");
    }

    @Test
    public void testCommaSeparatedStringListString_withDelimiter() {
        List<String> list = new DelimitedString("a=bc=d", "=");
        Assertions.assertThat(list).hasSize(3);
        Assertions.assertThat(list.toString()).isEqualTo("a=bc=d");
    }

    @Test
    public void testCommaSeparatedStringListStringArray() {
        List<String> list = new DelimitedString(new String[] {
            "a", "bc", "d"
        });
        Assertions.assertThat(list).hasSize(3);
        Assertions.assertThat(list.toString()).isEqualTo("a,bc,d");
    }

    @Test
    public void testCommaSeparatedStringListStringArray_withDelimiter() {
        List<String> list = new DelimitedString(new String[] {
            "a", "bc", "d"
        }, "+");
        Assertions.assertThat(list).hasSize(3);
        Assertions.assertThat(list.toString()).isEqualTo("a+bc+d");
    }

    @Test
    public void testCommaSeparatedStringListListOfString() {
        List<String> list = new DelimitedString(Arrays.asList("a", "bc", "d"));
        Assertions.assertThat(list).hasSize(3);
        Assertions.assertThat(list.toString()).isEqualTo("a,bc,d");
    }

    @Test
    public void testCommaSeparatedStringListListOfString_withDelimiter() {
        List<String> list = new DelimitedString(Arrays.asList("a", "bc", "d"), "-");
        Assertions.assertThat(list).hasSize(3);
        Assertions.assertThat(list.toString()).isEqualTo("a-bc-d");
    }

    @Test
    public void testSubList() {
        List<String> list = new DelimitedString(Arrays.asList("a", "bc", "d"), "-").subList(0, 2);
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.toString()).isEqualTo("a-bc");
    }
}
