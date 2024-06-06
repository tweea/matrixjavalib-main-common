/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class EnumMxTest {
    enum TestEnum {
        A("a"), B("b");

        public final String v;

        TestEnum(String v) {
            this.v = v;
        }
    }

    @Test
    void testBuildValueMap() {
        Map<String, TestEnum> map = EnumMx.buildValueMap(TestEnum.class, t -> t.v);
        assertThat(map).containsOnly(entry("a", TestEnum.A), entry("b", TestEnum.B));
    }
}
