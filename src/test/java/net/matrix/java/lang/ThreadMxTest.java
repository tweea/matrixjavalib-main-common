/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadMxTest {
    @Test
    public void testSleep_millis() {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        ThreadMx.sleep(10L);
        stopWatch.stop();
        assertThat(stopWatch.getTime()).isGreaterThanOrEqualTo(10L);
    }

    @Test
    public void testSleep_unit() {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        ThreadMx.sleep(10L, TimeUnit.MILLISECONDS);
        stopWatch.stop();
        assertThat(stopWatch.getTime()).isGreaterThanOrEqualTo(10L);
    }

    @Test
    public void testSleep_duration() {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        ThreadMx.sleep(Duration.ofMillis(10L));
        stopWatch.stop();
        assertThat(stopWatch.getTime()).isGreaterThanOrEqualTo(10L);
    }
}
