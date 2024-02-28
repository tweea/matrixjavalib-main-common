/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import java.lang.reflect.UndeclaredThrowableException;

import org.apache.commons.lang3.exception.ExceptionUtils;

import net.matrix.text.ResourceBundleMessageFormatter;

/**
 * 异常工具。
 */
public final class ThrowableMx {
    /**
     * 区域相关资源。
     */
    private static final ResourceBundleMessageFormatter RBMF = new ResourceBundleMessageFormatter(ThrowableMx.class).useCurrentLocale();

    /**
     * 阻止实例化。
     */
    private ThrowableMx() {
    }

    /**
     * 将异常转换为运行时异常。
     * 
     * @param throwable
     *     异常。
     * @return 运行时异常。
     */
    public static RuntimeException wrap(Throwable throwable) {
        if (throwable instanceof Error) {
            throw (Error) throwable;
        }
        if (throwable instanceof RuntimeException) {
            return (RuntimeException) throwable;
        }
        return new UndeclaredThrowableException(throwable);
    }

    /**
     * 获取异常信息与根源异常信息的组合信息。
     * 
     * @param throwable
     *     异常。
     * @return 组合的异常信息。
     */
    public static String getMessageWithRootCause(Throwable throwable) {
        Throwable rootCause = ExceptionUtils.getRootCause(throwable);
        if (rootCause == null || rootCause == throwable) {
            return RBMF.format("{0} 没有根源异常", ExceptionUtils.getMessage(throwable));
        } else {
            return RBMF.format("{0} 具有根源异常 {1}", ExceptionUtils.getMessage(throwable), ExceptionUtils.getMessage(rootCause));
        }
    }

    /**
     * 判断异常是否由指定类型的原因异常引起。
     * 
     * @param throwable
     *     异常。
     * @param causeTypes
     *     原因异常类型。
     */
    public static boolean isCausedBy(Throwable throwable, Class<? extends Throwable>... causeTypes) {
        for (Class<? extends Throwable> causeType : causeTypes) {
            if (ExceptionUtils.indexOfType(throwable, causeType) >= 0) {
                return true;
            }
        }
        return false;
    }
}
