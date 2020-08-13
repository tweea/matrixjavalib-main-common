/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 保证不会有 Exception 抛出到线程池的 Runnable，防止用户没有捕捉异常导致中断了线程池中的线程。
 * 在无法控制第三方包的 Runnalbe 实现时进行包装。
 */
public class WrapExceptionRunnable
    implements Runnable {
    /**
     * 日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(WrapExceptionRunnable.class);

    /**
     * 被包装的 Runnable。
     */
    private final Runnable runnable;

    /**
     * 包装构造器。
     * 
     * @param runnable
     *     被包装的 Runnable
     */
    public WrapExceptionRunnable(final Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        try {
            runnable.run();
        } catch (RuntimeException e) {
            // catch any exception, because the scheduled thread will break if the exception
            // thrown to outside
            LOG.error("Unexpected error occurred in task", e);
        }
    }
}
