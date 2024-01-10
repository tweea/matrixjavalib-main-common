/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

public class XMLPropertyResourceBundleTest {
    @Test
    public void testNew()
        throws IOException {
        XMLPropertyResourceBundle bundle = new XMLPropertyResourceBundle(new ClassPathResource("global.xml").getInputStream());
        assertThat(bundle.getString("male")).isEqualTo("男性");
    }

    @Test
    public void testControl()
        throws IOException {
        ResourceBundle bundle = XMLPropertyResourceBundle.Control.INSTANCE.newBundle("global", Locale.US, "xml", getClass().getClassLoader(), true);
        assertThat(bundle.getString("male")).isEqualTo("爷们");

        bundle = XMLPropertyResourceBundle.Control.INSTANCE.newBundle("global", Locale.CHINA, "xml", getClass().getClassLoader(), true);
        assertThat(bundle).isNull();
    }
}
