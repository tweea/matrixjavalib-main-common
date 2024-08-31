/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import java.lang.reflect.UndeclaredThrowableException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import org.apache.commons.lang3.exception.ExceptionUtils;

import net.matrix.text.ResourceBundleMessageFormatter;

/**
 * 异常工具。
 */
@ThreadSafe
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
     * 将检查异常转换为非检查异常。
     *
     * @param throwable
     *     异常。
     * @return 非检查异常。
     */
    @Nonnull
    public static RuntimeException wrap(@Nonnull Throwable throwable) {
        if (throwable instanceof Error error) {
            throw error;
        }
        if (throwable instanceof RuntimeException runtimeException) {
            return runtimeException;
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
    @Nonnull
    public static String getMessageWithRootCause(@Nonnull Throwable throwable) {
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
    public static boolean isCausedBy(@Nonnull Throwable throwable, @Nonnull Class<? extends Throwable>... causeTypes) {
        for (Class<? extends Throwable> causeType : causeTypes) {
            if (ExceptionUtils.indexOfType(throwable, causeType) >= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找异常的指定类型的原因异常。
     *
     * @param throwable
     *     异常。
     * @param causeType
     *     原因异常类型。
     */
    @Nullable
    public static <T extends Throwable> T findCause(@Nonnull Throwable throwable, @Nonnull Class<T> causeType) {
        while (throwable != null) {
            if (causeType.isAssignableFrom(throwable.getClass())) {
                return (T) throwable;
            }
            throwable = throwable.getCause();
        }
        return null;
    }
}
