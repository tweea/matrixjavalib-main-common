/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EncodesTest {
    @Test
    public void testBase62Encode() {
        long num = 63;

        String result = Encodes.encodeBase62(num);
        assertThat(result).isEqualTo("11");
        assertThat(Encodes.decodeBase62(result)).isEqualTo(num);
    }

    @Test
    public void testUrlEncode() {
        String input = "http://locahost/?q=中文&t=1";

        String result = Encodes.urlEncode(input);
        assertThat(Encodes.urlDecode(result)).isEqualTo(input);
    }
}
