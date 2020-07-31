/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class Collecitons3Test {
    @Test
    public void testExtractToMap() {
        TestBean3 bean1 = new TestBean3();
        bean1.setId(1);
        TestBean3 bean2 = new TestBean3();
        bean2.setId(2);
        List list = new ArrayList();
        list.add(bean1);
        list.add(bean2);

        Map<Integer, Integer> result = Collections3.extractToMap(list, "id", "id");
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(entry(1, 1), entry(2, 2));
    }

    @Test
    public void testExtractToList() {
        TestBean3 bean1 = new TestBean3();
        bean1.setId(1);
        TestBean3 bean2 = new TestBean3();
        bean2.setId(2);
        List list = new ArrayList();
        list.add(bean1);
        list.add(bean2);

        List<Integer> result = Collections3.extractToList(list, "id");
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(1, 2);
    }

    @Test
    public void testExtractToString() {
        TestBean3 bean1 = new TestBean3();
        bean1.setId(1);
        TestBean3 bean2 = new TestBean3();
        bean2.setId(2);
        List list = new ArrayList();
        list.add(bean1);
        list.add(bean2);

        assertThat(Collections3.extractToString(list, "id", ",")).isEqualTo("1,2");
    }

    @Test
    public void testConvertToString() {
        List<String> list = new ArrayList();
        list.add("aa");
        list.add("bb");

        String result = Collections3.convertToString(list, "<li>", "</li>");
        assertThat(result).isEqualTo("<li>aa</li><li>bb</li>");
    }

    public static class TestBean3 {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
