/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util.concurrent;

import java.util.concurrent.ExecutorService;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExecutorServiceBuilderTest {
    @Test
    public void testFixed() {
        ExecutorService executorService = new ExecutorServiceBuilder.Fixed().build();
        assertThat(executorService).isNotNull();
        executorService.shutdown();
    }

    @Test
    public void testCached() {
        ExecutorService executorService = new ExecutorServiceBuilder.Cached().build();
        assertThat(executorService).isNotNull();
        executorService.shutdown();
    }

    @Test
    public void testScheduled() {
        ExecutorService executorService = new ExecutorServiceBuilder.Scheduled().build();
        assertThat(executorService).isNotNull();
        executorService.shutdown();
    }
}
