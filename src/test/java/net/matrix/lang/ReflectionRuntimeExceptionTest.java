/*
 * 版权所有 2013 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import java.lang.reflect.InvocationTargetException;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ReflectionRuntimeExceptionTest {
    @Test
    public void testReflectionRuntimeExceptionThrowable() {
        Exception cause = new Exception();
        ReflectionRuntimeException exception = new ReflectionRuntimeException(cause);
        Assertions.assertThat(exception.getCause()).isSameAs(cause);

        Exception target = new Exception();
        cause = new InvocationTargetException(target);
        exception = new ReflectionRuntimeException(cause);
        Assertions.assertThat(exception.getCause()).isSameAs(target);
    }

    @Test
    public void testInitCauseThrowable() {
        Exception cause = new Exception();
        ReflectionRuntimeException exception = new ReflectionRuntimeException();
        exception.initCause(cause);
        Assertions.assertThat(exception.getCause()).isSameAs(cause);

        Exception target = new Exception();
        cause = new InvocationTargetException(target);
        exception = new ReflectionRuntimeException();
        exception.initCause(cause);
        Assertions.assertThat(exception.getCause()).isSameAs(target);
    }
}
