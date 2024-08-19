/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * 包装检查异常的非检查异常。
 */
@Immutable
public class UncheckedException
    extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 使用指定原因异常构造异常，详细信息指定为 <code>cause.toString()</code> （特别地指定原因异常的类和详细信息）。
     *
     * @param cause
     *     原因异常（使用 {@link #getCause()} 方法获取）。
     */
    public UncheckedException(@Nonnull Throwable cause) {
        super(cause.toString());
        initCause(getInitCause(cause));
    }

    /**
     * 使用指定详细信息和原因异常构造异常。<br>
     * 注意与 <code>cause</code> 关联的详细信息<i>不会</i>自动出现在本异常的详细信息中。
     *
     * @param message
     *     详细信息。详细信息可以通过 {@link #getMessage()} 方法获取。
     * @param cause
     *     原因异常（使用 {@link #getCause()} 方法获取）。
     */
    public UncheckedException(@Nullable String message, @Nonnull Throwable cause) {
        super(message);
        Objects.requireNonNull(cause);
        initCause(getInitCause(cause));
    }

    /**
     * 获取原因异常。
     */
    @Nonnull
    protected Throwable getInitCause(@Nonnull Throwable cause) {
        return cause;
    }
}
