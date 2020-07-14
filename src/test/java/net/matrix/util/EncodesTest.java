/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.util;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class EncodesTest {
    @Test
    public void base62Encode() {
        long num = 63;

        String result = Encodes.encodeBase62(num);
        Assertions.assertThat(result).isEqualTo("11");
        Assertions.assertThat(Encodes.decodeBase62(result)).isEqualTo(num);
    }

    @Test
    public void urlEncode() {
        String input = "http://locahost/?q=中文&t=1";
        String result = Encodes.urlEncode(input);

        Assertions.assertThat(Encodes.urlDecode(result)).isEqualTo(input);
    }
}
