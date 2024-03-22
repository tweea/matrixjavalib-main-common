/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.data.convert;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BinaryStringConverterTest {
    @Test
    public void testUTF8() {
        BinaryStringConverter converter = BinaryStringConverter.UTF8;
        String string = "foo message";
        byte[] binary = string.getBytes(StandardCharsets.UTF_8);

        assertThat(converter.toString(binary)).isEqualTo(string);
        assertThat(converter.toBinary(string)).containsExactly(binary);
    }

    @Test
    public void testBIT() {
        BinaryStringConverter converter = BinaryStringConverter.BIT;
        byte[] binary = "foo message".getBytes(StandardCharsets.UTF_8);

        assertThat(converter.toBinary(converter.toString(binary))).containsExactly(binary);
    }

    @Test
    public void testHEX() {
        BinaryStringConverter converter = BinaryStringConverter.HEX;
        byte[] binary = "foo message".getBytes(StandardCharsets.UTF_8);

        assertThat(converter.toBinary(converter.toString(binary))).containsExactly(binary);
    }

    @Test
    public void testBASE64() {
        BinaryStringConverter converter = BinaryStringConverter.BASE64;
        byte[] binary = "foo message".getBytes(StandardCharsets.UTF_8);

        assertThat(converter.toBinary(converter.toString(binary))).containsExactly(binary);
    }

    @Test
    public void testBASE64_URL_SAFE() {
        BinaryStringConverter converter = BinaryStringConverter.BASE64_URL_SAFE;
        byte[] binary = "foo message".getBytes(StandardCharsets.UTF_8);

        assertThat(converter.toBinary(converter.toString(binary))).containsExactly(binary);
    }
}
