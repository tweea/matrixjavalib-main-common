/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util.concurrent;

import java.util.concurrent.ExecutorService;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExecutorServiceBuilderTest {
    @Test
    void testFixed() {
        ExecutorService executorService = new ExecutorServiceBuilder.Fixed().build();
        assertThat(executorService).isNotNull();
        executorService.shutdown();
    }

    @Test
    void testCached() {
        ExecutorService executorService = new ExecutorServiceBuilder.Cached().build();
        assertThat(executorService).isNotNull();
        executorService.shutdown();
    }

    @Test
    void testScheduled() {
        ExecutorService executorService = new ExecutorServiceBuilder.Scheduled().build();
        assertThat(executorService).isNotNull();
        executorService.shutdown();
    }
}
