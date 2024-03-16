/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.matrix.text.ResourceBundleMessageFormatter;

/**
 * 不会抛出异常到线程池的 {@link Runnable}，防止用户没有捕捉异常导致中断线程池中的线程。
 * 主要用于包装无法控制实现的第三方包中的 {@link Runnable}。
 */
public class WrapExceptionRunnable
    implements Runnable {
    /**
     * 日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(WrapExceptionRunnable.class);

    /**
     * 区域相关资源。
     */
    private static final ResourceBundleMessageFormatter RBMF = new ResourceBundleMessageFormatter(WrapExceptionRunnable.class).useCurrentLocale();

    /**
     * 被包装的 {@link Runnable}。
     */
    private final Runnable runnable;

    /**
     * 包装构造器。
     * 
     * @param runnable
     *     被包装的 {@link Runnable}。
     */
    public WrapExceptionRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        try {
            runnable.run();
        } catch (RuntimeException e) {
            LOG.error(RBMF.get("发生意外错误"), e);
        }
    }
}
