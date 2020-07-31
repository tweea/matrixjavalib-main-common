/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.security;

import java.security.GeneralSecurityException;

/**
 * 身份认证失败异常。
 */
public class AuthenticateException
    extends GeneralSecurityException {
    private static final long serialVersionUID = -8363292424989396451L;

    /**
     * 使用 <code>null</code> 作为详细信息构造异常。原因异常没有初始化，可以随后调用 {@link #initCause} 进行初始化。
     */
    public AuthenticateException() {
        super();
    }

    /**
     * 使用指定详细信息构造异常。原因异常没有初始化，可以随后调用 {@link #initCause} 进行初始化。
     * 
     * @param message
     *     详细信息。详细信息可以通过 {@link #getMessage()} 方法获取。
     */
    public AuthenticateException(final String message) {
        super(message);
    }

    /**
     * 使用指定原因异常构造异常，详细信息指定为 <tt>(cause==null ? null : cause.toString())</tt> （特别地指定原因异常的类和详细信息）。
     * 
     * @param cause
     *     原因异常（使用 {@link #getCause()} 方法获取）。可以使用 <tt>null</tt> 值，指原因异常不存在或未知。
     */
    public AuthenticateException(final Throwable cause) {
        super(cause);
    }

    /**
     * 使用指定详细信息和原因异常构造异常。
     * <p>
     * 注意与 <code>cause</code> 关联的详细信息<i>不会</i>自动出现在本异常的详细信息中。
     * 
     * @param message
     *     详细信息。详细信息可以通过 {@link #getMessage()} 方法获取。
     * @param cause
     *     原因异常（使用 {@link #getCause()} 方法获取）。可以使用 <tt>null</tt> 值，指原因异常不存在或未知。
     */
    public AuthenticateException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
