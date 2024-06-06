/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.data.convert;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BinaryStringConverterTest {
    @Test
    void testUTF8() {
        BinaryStringConverter converter = BinaryStringConverter.UTF8;
        String string = "foo message";
        byte[] binary = string.getBytes(StandardCharsets.UTF_8);

        assertThat(converter.toString(binary)).isEqualTo(string);
        assertThat(converter.toBinary(string)).isEqualTo(binary);
    }

    @Test
    void testBIT() {
        BinaryStringConverter converter = BinaryStringConverter.BIT;
        byte[] binary = "foo message".getBytes(StandardCharsets.UTF_8);

        assertThat(converter.toBinary(converter.toString(binary))).isEqualTo(binary);
    }

    @Test
    void testHEX() {
        BinaryStringConverter converter = BinaryStringConverter.HEX;
        byte[] binary = "foo message".getBytes(StandardCharsets.UTF_8);

        assertThat(converter.toBinary(converter.toString(binary))).isEqualTo(binary);
    }

    @Test
    void testBASE64() {
        BinaryStringConverter converter = BinaryStringConverter.BASE64;
        byte[] binary = "foo message".getBytes(StandardCharsets.UTF_8);

        assertThat(converter.toBinary(converter.toString(binary))).isEqualTo(binary);
    }

    @Test
    void testBASE64_URL_SAFE() {
        BinaryStringConverter converter = BinaryStringConverter.BASE64_URL_SAFE;
        byte[] binary = "foo message".getBytes(StandardCharsets.UTF_8);

        assertThat(converter.toBinary(converter.toString(binary))).isEqualTo(binary);
    }
}
