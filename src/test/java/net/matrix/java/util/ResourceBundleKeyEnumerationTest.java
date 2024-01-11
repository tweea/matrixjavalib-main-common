/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util;

import java.util.ListResourceBundle;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Sets;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceBundleKeyEnumerationTest {
    @Test
    public void testNew() {
        ResourceBundleKeyEnumeration enumeration = new ResourceBundleKeyEnumeration(Sets.newHashSet("a", "b"), null);
        assertThat(enumeration.hasMoreElements()).isTrue();
        assertThat(enumeration.nextElement()).isEqualTo("a");
        assertThat(enumeration.hasMoreElements()).isTrue();
        assertThat(enumeration.nextElement()).isEqualTo("b");
        assertThat(enumeration.hasMoreElements()).isFalse();
    }

    @Test
    public void testNew_parent() {
        ResourceBundleKeyEnumeration enumeration = new ResourceBundleKeyEnumeration(Sets.newHashSet("a", "b"), new ListResourceBundle() {
            @Override
            protected Object[][] getContents() {
                return new Object[][] {
                    {
                        "a", "1"
                    }, {
                        "c", "3"
                    }
                };
            }
        });
        assertThat(enumeration.hasMoreElements()).isTrue();
        assertThat(enumeration.nextElement()).isEqualTo("a");
        assertThat(enumeration.hasMoreElements()).isTrue();
        assertThat(enumeration.nextElement()).isEqualTo("b");
        assertThat(enumeration.hasMoreElements()).isTrue();
        assertThat(enumeration.nextElement()).isEqualTo("c");
        assertThat(enumeration.hasMoreElements()).isFalse();
    }
}
