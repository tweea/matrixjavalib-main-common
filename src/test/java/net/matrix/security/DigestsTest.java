/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.security;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;

public class DigestsTest {
    @Test
    public void testSha1_string() {
        String input = "user";
        byte[] salt = Digests.generateSalt(8);

        assertThat(Digests.sha1(input.getBytes())).hasSize(20);
        assertThat(Digests.sha1(input.getBytes(), salt)).hasSize(20);
        assertThat(Digests.sha1(input.getBytes(), salt, 1024)).hasSize(20);
    }

    @Test
    public void testSha1_file()
        throws IOException {
        Resource resource = new ClassPathResource("/log4j2-test.xml");

        assertThat(Digests.sha1(resource.getInputStream())).hasSize(20);
    }
}
