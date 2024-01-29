/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.text;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageFormatMxTest {
    @Test
    public void testFormat() {
        assertThat(MessageFormatMx.format("{0} + {1} = {2}", 1, 2, 3)).isEqualTo("1 + 2 = 3");
        assertThat(MessageFormatMx.format("{0", 1)).isEqualTo("{0，1");
    }

    @Test
    public void testFormat_locale() {
        assertThat(MessageFormatMx.format("{0} + {1} = {2}", Locale.CHINA, 1, 2, 3)).isEqualTo("1 + 2 = 3");
        assertThat(MessageFormatMx.format("{0", Locale.CHINA, 1)).isEqualTo("{0，1");
    }

    @Test
    public void testFormatFallback() {
        assertThat(MessageFormatMx.formatFallback("1", "2", 3)).isEqualTo("1，2，3");
    }
}
