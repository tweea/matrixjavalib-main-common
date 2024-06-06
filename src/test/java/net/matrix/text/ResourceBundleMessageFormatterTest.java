/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.text;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

import net.matrix.java.util.LocaleMx;
import net.matrix.java.util.ResourceBundleMx;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceBundleMessageFormatterTest {
    @Test
    void testNew() {
        ResourceBundleMessageFormatter formatter = new ResourceBundleMessageFormatter("global");
        assertThat(formatter.getBaseName()).isEqualTo("global");
        assertThat(formatter.getLocale()).isSameAs(Locale.getDefault(Locale.Category.FORMAT));
    }

    @Test
    void testNew_clazz() {
        ResourceBundleMessageFormatter formatter = new ResourceBundleMessageFormatter(String.class);
        assertThat(formatter.getBaseName()).isEqualTo("java.lang.String");
        assertThat(formatter.getLocale()).isSameAs(Locale.getDefault(Locale.Category.FORMAT));
    }

    @Test
    void testUseLocale() {
        ResourceBundleMessageFormatter formatter = new ResourceBundleMessageFormatter("global");

        formatter.useLocale(Locale.US);
        assertThat(formatter.getLocale()).isSameAs(Locale.US);
    }

    @Test
    void testUseCurrentLocale() {
        ResourceBundleMessageFormatter formatter = new ResourceBundleMessageFormatter("global");

        formatter.useCurrentLocale();
        LocaleMx.current(Locale.US);
        try {
            assertThat(formatter.getLocale()).isSameAs(Locale.US);
        } finally {
            LocaleMx.current((Locale) null);
        }
    }

    @Test
    void testGetResourceBundle() {
        ResourceBundleMessageFormatter formatter = new ResourceBundleMessageFormatter("global");

        ResourceBundle bundle = formatter.getResourceBundle();
        assertThat(bundle.getString("message")).isEqualTo("一双{0}");
    }

    @Test
    void testGet() {
        ResourceBundleMessageFormatter formatter = new ResourceBundleMessageFormatter("global");

        assertThat(formatter.get("message")).isEqualTo("一双{0}");
        assertThat(formatter.get("1")).isEqualTo("1");
    }

    @Test
    void testFormat() {
        ResourceBundleMessageFormatter formatter = new ResourceBundleMessageFormatter("global");

        assertThat(formatter.format("message", "绣花鞋")).isEqualTo("一双绣花鞋");
        assertThat(formatter.format("1, {0}, {1}", "2", 3)).isEqualTo("1, 2, 3");
    }

    @Test
    void testGet_bundle() {
        ResourceBundle bundle = ResourceBundleMx.getBundle("global", Locale.CHINA);

        assertThat(ResourceBundleMessageFormatter.get(bundle, "message")).isEqualTo("一双{0}");
        assertThat(ResourceBundleMessageFormatter.get(bundle, "1")).isEqualTo("1");
    }

    @Test
    void testFormat_bundle() {
        ResourceBundle bundle = ResourceBundleMx.getBundle("global", Locale.CHINA);

        assertThat(ResourceBundleMessageFormatter.format(bundle, "message", "绣花鞋")).isEqualTo("一双绣花鞋");
        assertThat(ResourceBundleMessageFormatter.format(bundle, "1, {0}, {1}", "2", 3)).isEqualTo("1, 2, 3");
    }
}
