/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.util;

import java.time.Duration;
import java.util.Map;

import org.apache.commons.lang3.ThreadUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ThreadLocalMapTest {
    @Test
    void testPut()
        throws InterruptedException {
        Map<String, String> map = new ThreadLocalMap<>();

        map.put("a", "x");
        Thread thread = new Thread(() -> {
            map.put("a", "y");
            map.put("b", "y");
        });
        thread.start();
        ThreadUtils.join(thread, Duration.ofSeconds(1));
        assertThat(map).hasSize(1);
        assertThat(map).isNotEmpty();
        assertThat(map.containsKey("a")).isTrue();
        assertThat(map.containsKey("b")).isFalse();
        assertThat(map.containsValue("x")).isTrue();
        assertThat(map.containsValue("y")).isFalse();
        assertThat(map.get("a")).isEqualTo("x");
    }
}
