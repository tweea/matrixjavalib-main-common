/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * 异常工具。
 */
public final class Exceptions {
    /**
     * 阻止实例化。
     */
    private Exceptions() {
    }

    /**
     * 将 {@link Throwable} 转换为 {@link RuntimeException}。
     * 
     * @param t
     *     异常
     * @return 运行时异常
     */
    public static RuntimeException unchecked(final Throwable t) {
        if (t instanceof Error) {
            throw (Error) t;
        } else if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else {
            return new RuntimeException(t);
        }
    }

    /**
     * 获取异常信息与底层异常信息的组合信息，适用于异常为包装异常，底层异常是根本原因的情况。
     * 
     * @param t
     *     异常
     * @return 组合异常信息
     */
    public static String getMessageWithRootCause(final Throwable t) {
        Throwable rootCause = ExceptionUtils.getRootCause(t);
        if (rootCause == null || rootCause == t) {
            return ExceptionUtils.getMessage(t) + " without root cause";
        } else {
            return ExceptionUtils.getMessage(t) + " with root cause " + ExceptionUtils.getMessage(rootCause);
        }
    }

    /**
     * 判断异常是否由指定类型的底层异常引起。
     * 
     * @param t
     *     异常
     * @param causeTypes
     *     底层异常类型
     * @return 异常链中包含指定异常类型
     */
    public static boolean isCausedBy(final Throwable t, final Class<? extends Throwable>... causeTypes) {
        for (Class<? extends Throwable> causeType : causeTypes) {
            if (ExceptionUtils.indexOfType(t, causeType) >= 0) {
                return true;
            }
        }
        return false;
    }
}
