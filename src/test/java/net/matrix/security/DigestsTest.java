/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.security;

import java.io.IOException;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class DigestsTest {
    @Test
    public void digestString() {
        String input = "user";
        Digests.sha1(input.getBytes());

        byte[] salt = Digests.generateSalt(8);
        Digests.sha1(input.getBytes(), salt);
        Digests.sha1(input.getBytes(), salt, 1024);
    }

    @Test
    public void digestFile()
        throws IOException {
        Resource resource = new ClassPathResource("/log4j2-test.xml");
        Digests.md5(resource.getInputStream());
        Digests.sha1(resource.getInputStream());
    }
}
