/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.text;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

import net.matrix.java.util.ResourceBundleMx;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageFormatsTest {
    @Test
    public void testFormatFallback() {
        assertThat(MessageFormats.formatFallback("1", "2", 3)).isEqualTo("1, 2, 3");
    }

    @Test
    public void testFormat() {
        assertThat(MessageFormats.format("{0} + {1} = {2}", Locale.CHINA, 1, 2, 3)).isEqualTo("1 + 2 = 3");
    }

    @Test
    public void testFormatBundle() {
        ResourceBundle bundle = ResourceBundleMx.getBundle("global", Locale.CHINA);

        assertThat(MessageFormats.format(bundle, "message", "绣花鞋")).isEqualTo("一双绣花鞋");
    }

    @Test
    public void testFormatBundle_fallback() {
        ResourceBundle bundle = ResourceBundleMx.getBundle("global", Locale.CHINA);

        assertThat(MessageFormats.format(bundle, "1", "2", 3)).isEqualTo("1, 2, 3");
    }
}
