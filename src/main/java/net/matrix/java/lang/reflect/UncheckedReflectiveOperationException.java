/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang.reflect;

import java.lang.reflect.InvocationTargetException;

import net.matrix.java.lang.UncheckedException;

/**
 * 包装反射操作异常的非检查异常。
 */
public class UncheckedReflectiveOperationException
    extends UncheckedException {
    private static final long serialVersionUID = 1L;

    /**
     * 使用指定原因异常构造异常，详细信息指定为 <code>cause.toString()</code> （特别地指定原因异常的类和详细信息）。
     * 
     * @param cause
     *     原因异常（使用 {@link #getCause()} 方法获取）。
     */
    public UncheckedReflectiveOperationException(ReflectiveOperationException cause) {
        super(cause);
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
    public UncheckedReflectiveOperationException(String message, ReflectiveOperationException cause) {
        super(message, cause);
    }

    @Override
    protected Throwable getInitCause(Throwable cause) {
        if (cause instanceof InvocationTargetException) {
            return ((InvocationTargetException) cause).getTargetException();
        }
        return cause;
    }
}
