/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.text;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

import net.matrix.java.util.ResourceBundleMx;

import static org.assertj.core.api.Assertions.assertThat;

public class LocaleMessageFormatMxTest {
    @Test
    public void testFormat() {
        assertThat(LocaleMessageFormatMx.format("{0} + {1} = {2}", Locale.CHINA, 1, 2, 3)).isEqualTo("1 + 2 = 3");
        assertThat(LocaleMessageFormatMx.format("{0", Locale.CHINA, 1)).isEqualTo("{0, 1");
    }

    @Test
    public void testFormatFallback() {
        assertThat(LocaleMessageFormatMx.formatFallback("1", "2", 3)).isEqualTo("1, 2, 3");
    }

    @Test
    public void testFormatBundle() {
        ResourceBundle bundle = ResourceBundleMx.getBundle("global", Locale.CHINA);

        assertThat(LocaleMessageFormatMx.format(bundle, "message", "绣花鞋")).isEqualTo("一双绣花鞋");
        assertThat(LocaleMessageFormatMx.format(bundle, "1", "2", 3)).isEqualTo("1, 2, 3");
    }
}
