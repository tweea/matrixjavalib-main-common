/*
 * 版权所有 2013 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionRuntimeExceptionTest {
    @Test
    public void testReflectionRuntimeExceptionThrowable() {
        Exception cause = new Exception();
        ReflectionRuntimeException exception = new ReflectionRuntimeException(cause);
        assertThat(exception.getCause()).isSameAs(cause);

        Exception target = new Exception();
        cause = new InvocationTargetException(target);
        exception = new ReflectionRuntimeException(cause);
        assertThat(exception.getCause()).isSameAs(target);
    }

    @Test
    public void testInitCauseThrowable() {
        Exception cause = new Exception();
        ReflectionRuntimeException exception = new ReflectionRuntimeException();
        exception.initCause(cause);
        assertThat(exception.getCause()).isSameAs(cause);

        Exception target = new Exception();
        cause = new InvocationTargetException(target);
        exception = new ReflectionRuntimeException();
        exception.initCause(cause);
        assertThat(exception.getCause()).isSameAs(target);
    }
}
