/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang.reflect;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UncheckedReflectiveOperationExceptionTest {
    @Test
    void testNew_cause() {
        ReflectiveOperationException cause = new ReflectiveOperationException();

        UncheckedReflectiveOperationException exception = new UncheckedReflectiveOperationException(cause);
        assertThat(exception.getMessage()).isEqualTo(cause.toString());
        assertThat(exception.getCause()).isSameAs(cause);
    }

    @Test
    void testNew_cause_InvocationTargetException() {
        Exception target = new Exception();
        ReflectiveOperationException cause = new InvocationTargetException(target);

        UncheckedReflectiveOperationException exception = new UncheckedReflectiveOperationException(cause);
        assertThat(exception.getMessage()).isEqualTo(cause.toString());
        assertThat(exception.getCause()).isSameAs(target);
    }

    @Test
    void testNew_message_cause() {
        String message = "t";
        ReflectiveOperationException cause = new ReflectiveOperationException();

        UncheckedReflectiveOperationException exception = new UncheckedReflectiveOperationException(message, cause);
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isSameAs(cause);
    }
}
