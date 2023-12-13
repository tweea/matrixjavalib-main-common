/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.text;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

import net.matrix.java.util.LocaleMx;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceBundlesTest {
    @Test
    public void testGetBundle() {
        LocaleMx.current(Locale.CHINA);

        ResourceBundle bundle = ResourceBundles.getBundle("global");
        assertThat(bundle.getString("male")).isEqualTo("男性");
    }

    @Test
    public void testGetBundle_locale() {
        ResourceBundle bundle = ResourceBundles.getBundle("global", Locale.US);
        assertThat(bundle.getString("male")).isEqualTo("爷们");
    }

    @Test
    public void testGetBundle_fallback() {
        ResourceBundle bundle = ResourceBundles.getBundle("fallback");
        assertThat(bundle.getString("male")).isEqualTo("male");
    }

    @Test
    public void testGetProperty() {
        ResourceBundle bundle = ResourceBundles.getBundle("global", Locale.US);

        assertThat(ResourceBundles.getProperty(bundle, "male")).isEqualTo("爷们");
        assertThat(ResourceBundles.getProperty(bundle, "yes")).isEqualTo("yes");
        assertThat(ResourceBundles.getProperty(bundle, "OK")).isEqualTo("OK");
    }
}
