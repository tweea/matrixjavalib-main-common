/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClassMxTest {
    static class A {
    }

    static class B
        extends HashMap<String, Integer> {
        private static final long serialVersionUID = 1L;
    }

    @Test
    void testGetParameterizedTypeNumber() {
        assertThat(ClassMx.getParameterizedTypeNumber(A.class)).isEqualTo(0);

        assertThat(ClassMx.getParameterizedTypeNumber(B.class)).isEqualTo(2);
    }

    @Test
    void testGetParameterizedType() {
        assertThat(ClassMx.getParameterizedType(A.class, -1)).isNull();
        assertThat(ClassMx.getParameterizedType(A.class, 0)).isNull();
        assertThat(ClassMx.getParameterizedType(A.class, 1)).isNull();

        assertThat(ClassMx.getParameterizedType(B.class, -1)).isNull();
        assertThat(ClassMx.getParameterizedType(B.class, 0)).isSameAs(String.class);
        assertThat(ClassMx.getParameterizedType(B.class, 1)).isSameAs(Integer.class);
        assertThat(ClassMx.getParameterizedType(B.class, 2)).isNull();
    }
}
