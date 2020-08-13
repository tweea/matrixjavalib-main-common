/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.io;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RelativeResourceTest {
    @Test
    public void testRelativeResource_root() {
        RelativeResource resource = new RelativeResource("a", "b");
        assertThat(resource.getRoot()).isEqualTo("a");
        assertThat(resource.getPath()).isEqualTo("b");
    }

    @Test
    public void testRelativeResource_parent() {
        RelativeResource parent = new RelativeResource("a", "b");

        RelativeResource resource = new RelativeResource(parent, "c");
        assertThat(resource.getRoot()).isEqualTo("a");
        assertThat(resource.getPath()).isEqualTo("b/c");
    }
}
