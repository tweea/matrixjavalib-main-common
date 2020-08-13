/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.text;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalesTest {
    @Test
    public void testCurrent() {
        assertThat(Locales.current()).isEqualTo(Locale.getDefault());

        Locales.current(Locale.CHINA);
        assertThat(Locales.current()).isEqualTo(Locale.CHINA);

        Locales.current(Locale.getDefault());
        assertThat(Locales.current()).isEqualTo(Locale.getDefault());
    }
}
