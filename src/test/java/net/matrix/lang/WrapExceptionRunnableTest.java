/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WrapExceptionRunnableTest {
    @Test
    public void testRun() {
        MutableBoolean flag = new MutableBoolean();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                flag.setTrue();
            }
        };

        new WrapExceptionRunnable(runnable).run();
        assertThat(flag.getValue()).isTrue();
    }

    @Test
    public void testRun_exception() {
        MutableBoolean flag = new MutableBoolean();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (flag.isFalse()) {
                    throw new RuntimeException();
                }
                flag.setTrue();
            }
        };

        new WrapExceptionRunnable(runnable).run();
        assertThat(flag.getValue()).isFalse();
    }
}
