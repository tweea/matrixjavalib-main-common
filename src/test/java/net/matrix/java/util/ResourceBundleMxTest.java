/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ResourceBundleMxTest {
    @Test
    public void testGetBundle() {
        ResourceBundle bundle = ResourceBundleMx.getBundle("global");
        assertThat(bundle.getString("message")).isEqualTo("一双{0}");
    }

    @Test
    public void testGetBundle_locale() {
        ResourceBundle bundle = ResourceBundleMx.getBundle("global", Locale.US);
        assertThat(bundle.getString("male")).isEqualTo("爷们");
    }

    @Test
    public void testGetBundle_fallback() {
        ResourceBundle bundle = ResourceBundleMx.getBundle("fallback");
        assertThatExceptionOfType(MissingResourceException.class).isThrownBy(() -> bundle.getString("male"));
    }

    @Test
    public void testGetObject() {
        ResourceBundle bundle = ResourceBundleMx.getBundle("global", Locale.US);

        assertThat((String) ResourceBundleMx.getObject(bundle, "male")).isEqualTo("爷们");
        assertThat((String) ResourceBundleMx.getObject(bundle, "yes")).isEqualTo("yes");
        assertThat((String) ResourceBundleMx.getObject(bundle, "OK")).isNull();
    }

    @Test
    public void testGetObject_defaultObject() {
        ResourceBundle bundle = ResourceBundleMx.getBundle("global", Locale.US);

        assertThat(ResourceBundleMx.getObject(bundle, "male", "AA")).isEqualTo("爷们");
        assertThat(ResourceBundleMx.getObject(bundle, "yes", "AA")).isEqualTo("yes");
        assertThat(ResourceBundleMx.getObject(bundle, "OK", "AA")).isEqualTo("AA");
    }
}
