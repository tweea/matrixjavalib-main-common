/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthenticationExceptionTest {
    @Test
    void testNew() {
        AuthenticationException exception = new AuthenticationException();
        assertThat(exception.getMessage()).isNull();
        assertThat(exception.getCause()).isNull();
    }

    @Test
    void testNew_message() {
        String message = "t";

        AuthenticationException exception = new AuthenticationException(message);
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isNull();
    }

    @Test
    void testNew_cause() {
        Exception cause = new Exception();

        AuthenticationException exception = new AuthenticationException(cause);
        assertThat(exception.getMessage()).isEqualTo(cause.toString());
        assertThat(exception.getCause()).isSameAs(cause);
    }

    @Test
    void testNew_message_cause() {
        String message = "t";
        Exception cause = new Exception();

        AuthenticationException exception = new AuthenticationException(message, cause);
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isSameAs(cause);
    }
}
