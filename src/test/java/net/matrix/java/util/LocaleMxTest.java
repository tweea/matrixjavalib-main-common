/*
 * 版权所有 2023 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocaleMxTest {
    @Test
    void testCurrent() {
        assertThat(LocaleMx.current()).isEqualTo(Locale.getDefault());
        assertThat(LocaleMx.current(Locale.Category.DISPLAY)).isEqualTo(Locale.getDefault(Locale.Category.DISPLAY));

        LocaleMx.current(Locale.CHINA);
        assertThat(LocaleMx.current()).isEqualTo(Locale.CHINA);
        assertThat(LocaleMx.current(Locale.Category.DISPLAY)).isEqualTo(Locale.CHINA);

        LocaleMx.current(Locale.FRANCE);
        assertThat(LocaleMx.current()).isEqualTo(Locale.FRANCE);
        assertThat(LocaleMx.current(Locale.Category.DISPLAY)).isEqualTo(Locale.FRANCE);

        LocaleMx.current((Locale) null);
        assertThat(LocaleMx.current()).isEqualTo(Locale.getDefault());
        assertThat(LocaleMx.current(Locale.Category.DISPLAY)).isEqualTo(Locale.getDefault(Locale.Category.DISPLAY));
    }

    @Test
    void testCurrent_category() {
        assertThat(LocaleMx.current()).isEqualTo(Locale.getDefault());
        assertThat(LocaleMx.current(Locale.Category.DISPLAY)).isEqualTo(Locale.getDefault(Locale.Category.DISPLAY));

        LocaleMx.current(Locale.Category.DISPLAY, Locale.CHINA);
        assertThat(LocaleMx.current()).isEqualTo(Locale.getDefault());
        assertThat(LocaleMx.current(Locale.Category.DISPLAY)).isEqualTo(Locale.CHINA);

        LocaleMx.current(Locale.Category.DISPLAY, Locale.FRANCE);
        assertThat(LocaleMx.current()).isEqualTo(Locale.getDefault());
        assertThat(LocaleMx.current(Locale.Category.DISPLAY)).isEqualTo(Locale.FRANCE);

        LocaleMx.current(Locale.Category.DISPLAY, null);
        assertThat(LocaleMx.current()).isEqualTo(Locale.getDefault());
        assertThat(LocaleMx.current(Locale.Category.DISPLAY)).isEqualTo(Locale.getDefault(Locale.Category.DISPLAY));
    }
}
