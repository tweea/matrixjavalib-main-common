/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImpossibleExceptionTest {
    @Test
    void testNew() {
        ImpossibleException exception = new ImpossibleException();
        assertThat(exception.getMessage()).isNull();
        assertThat(exception.getCause()).isNull();
    }

    @Test
    void testNew_message() {
        String message = "t";

        ImpossibleException exception = new ImpossibleException(message);
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isNull();
    }

    @Test
    void testNew_cause() {
        Exception cause = new Exception();

        ImpossibleException exception = new ImpossibleException(cause);
        assertThat(exception.getMessage()).isEqualTo(cause.toString());
        assertThat(exception.getCause()).isSameAs(cause);
    }

    @Test
    void testNew_message_cause() {
        String message = "t";
        Exception cause = new Exception();

        ImpossibleException exception = new ImpossibleException(message, cause);
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isSameAs(cause);
    }
}
