/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.util;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程工具。
 */
public final class Threads {
    /**
     * 日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(Threads.class);

    /**
     * 阻止实例化。
     */
    private Threads() {
    }

    /**
     * sleep 等待，单位为毫秒。已捕捉并处理 InterruptedException。
     * 
     * @param durationInMillis
     *     等待毫秒数
     */
    public static void sleep(final long durationInMillis) {
        try {
            Thread.sleep(durationInMillis);
        } catch (InterruptedException e) {
            LOG.trace("", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * sleep 等待。已捕捉并处理 InterruptedException。
     * 
     * @param duration
     *     等待时间
     * @param unit
     *     时间单位
     */
    public static void sleep(final long duration, final TimeUnit unit) {
        try {
            Thread.sleep(unit.toMillis(duration));
        } catch (InterruptedException e) {
            LOG.trace("", e);
            Thread.currentThread().interrupt();
        }
    }
}
