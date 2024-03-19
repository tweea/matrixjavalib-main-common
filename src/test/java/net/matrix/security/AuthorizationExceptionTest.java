/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorizationExceptionTest {
    @Test
    public void testNew() {
        AuthorizationException exception = new AuthorizationException();
        assertThat(exception.getMessage()).isNull();
        assertThat(exception.getCause()).isNull();
    }

    @Test
    public void testNew_message() {
        String message = "t";

        AuthorizationException exception = new AuthorizationException(message);
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isNull();
    }

    @Test
    public void testNew_cause() {
        Exception cause = new Exception();

        AuthorizationException exception = new AuthorizationException(cause);
        assertThat(exception.getMessage()).isEqualTo(cause.toString());
        assertThat(exception.getCause()).isSameAs(cause);
    }

    @Test
    public void testNew_message_cause() {
        String message = "t";
        Exception cause = new Exception();

        AuthorizationException exception = new AuthorizationException(message, cause);
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isSameAs(cause);
    }
}
