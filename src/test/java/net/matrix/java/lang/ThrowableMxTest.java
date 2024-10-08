/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ThrowableMxTest {
    @Test
    void testWrap() {
        Exception exception = new Exception("my exception");

        RuntimeException runtimeException = ThrowableMx.wrap(exception);
        assertThat(runtimeException.getCause()).isSameAs(exception);

        RuntimeException runtimeException2 = ThrowableMx.wrap(runtimeException);
        assertThat(runtimeException2).isSameAs(runtimeException);
    }

    @Test
    void testGetMessageWithRootCause() {
        IOException ioException = new IOException("my exception");
        IllegalStateException illegalStateException = new IllegalStateException(ioException);
        RuntimeException runtimeException = new RuntimeException(illegalStateException);

        assertThat(ThrowableMx.getMessageWithRootCause(ioException)).contains(" 没有根源异常");
        assertThat(ThrowableMx.getMessageWithRootCause(runtimeException)).contains(" 具有根源异常 ");
    }

    @Test
    void testIsCausedBy() {
        IOException ioException = new IOException("my exception");
        IllegalStateException illegalStateException = new IllegalStateException(ioException);
        RuntimeException runtimeException = new RuntimeException(illegalStateException);

        assertThat(ThrowableMx.isCausedBy(runtimeException, IOException.class)).isTrue();
        assertThat(ThrowableMx.isCausedBy(runtimeException, IllegalStateException.class, IOException.class)).isTrue();
        assertThat(ThrowableMx.isCausedBy(runtimeException, Exception.class)).isTrue();
        assertThat(ThrowableMx.isCausedBy(runtimeException, IllegalAccessException.class)).isFalse();
    }

    @Test
    void testFindCause() {
        IOException ioException = new IOException("my exception");
        IllegalStateException illegalStateException = new IllegalStateException(ioException);
        RuntimeException runtimeException = new RuntimeException(illegalStateException);

        assertThat(ThrowableMx.findCause(runtimeException, IOException.class)).isSameAs(ioException);
        assertThat(ThrowableMx.findCause(runtimeException, IllegalStateException.class)).isSameAs(illegalStateException);
        assertThat(ThrowableMx.findCause(runtimeException, Exception.class)).isSameAs(runtimeException);
        assertThat(ThrowableMx.findCause(runtimeException, IllegalAccessException.class)).isNull();
    }
}
