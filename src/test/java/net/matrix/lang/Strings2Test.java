/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Strings2Test {
    @Test
    public void testReplaceAllBetweenDelimiter() {
        assertThat(Strings2.replaceAllBetweenDelimiter("abc<Bad>xyz", "<", ">", "Bad", "Good")).isEqualTo("abc<Good>xyz");
    }
}
