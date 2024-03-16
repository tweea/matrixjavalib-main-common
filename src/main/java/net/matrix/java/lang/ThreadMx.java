/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.matrix.text.ResourceBundleMessageFormatter;

/**
 * 线程工具。
 */
public final class ThreadMx {
    /**
     * 日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(ThreadMx.class);

    /**
     * 区域相关资源。
     */
    private static final ResourceBundleMessageFormatter RBMF = new ResourceBundleMessageFormatter(ThreadMx.class).useCurrentLocale();

    /**
     * 阻止实例化。
     */
    private ThreadMx() {
    }

    /**
     * 使当前执行线程睡眠等待指定时间，时间单位为毫秒，已捕捉并处理中断异常。
     * 
     * @param durationInMillis
     *     等待毫秒数。
     */
    public static void sleep(long durationInMillis) {
        try {
            Thread.sleep(durationInMillis);
        } catch (InterruptedException e) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(RBMF.get("线程睡眠被中断"), e);
            }
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 使当前执行线程睡眠等待指定时间，已捕捉并处理中断异常。
     * 
     * @param duration
     *     等待时间数。
     * @param unit
     *     时间单位。
     */
    public static void sleep(long duration, TimeUnit unit) {
        try {
            Thread.sleep(unit.toMillis(duration));
        } catch (InterruptedException e) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(RBMF.get("线程睡眠被中断"), e);
            }
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 使当前执行线程睡眠等待指定时间，已捕捉并处理中断异常。
     * 
     * @param duration
     *     等待时间。
     */
    public static void sleep(Duration duration) {
        try {
            ThreadUtils.sleep(duration);
        } catch (InterruptedException e) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(RBMF.get("线程睡眠被中断"), e);
            }
            Thread.currentThread().interrupt();
        }
    }
}
