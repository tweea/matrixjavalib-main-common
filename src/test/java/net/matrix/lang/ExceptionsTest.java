/*
 * 版权所有 2013 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExceptionsTest {
    @Test
    public void unchecked() {
        Exception exception = new Exception("my exception");
        RuntimeException runtimeException = Exceptions.unchecked(exception);
        assertThat(runtimeException.getCause()).isSameAs(exception);

        RuntimeException runtimeException2 = Exceptions.unchecked(runtimeException);
        assertThat(runtimeException2).isSameAs(runtimeException);
    }

    @Test
    public void isCausedBy() {
        IOException ioexception = new IOException("my exception");
        IllegalStateException illegalStateException = new IllegalStateException(ioexception);
        RuntimeException runtimeException = new RuntimeException(illegalStateException);

        assertThat(Exceptions.isCausedBy(runtimeException, IOException.class)).isTrue();
        assertThat(Exceptions.isCausedBy(runtimeException, IllegalStateException.class, IOException.class)).isTrue();
        assertThat(Exceptions.isCausedBy(runtimeException, Exception.class)).isTrue();
        assertThat(Exceptions.isCausedBy(runtimeException, IllegalAccessException.class)).isFalse();
    }
}
