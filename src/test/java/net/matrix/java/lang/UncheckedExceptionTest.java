/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UncheckedExceptionTest {
    @Test
    void testNew_cause() {
        Exception cause = new Exception();

        UncheckedException exception = new UncheckedException(cause);
        assertThat(exception.getMessage()).isEqualTo(cause.toString());
        assertThat(exception.getCause()).isSameAs(cause);
    }

    @Test
    void testNew_message_cause() {
        String message = "t";
        Exception cause = new Exception();

        UncheckedException exception = new UncheckedException(message, cause);
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isSameAs(cause);
    }
}
