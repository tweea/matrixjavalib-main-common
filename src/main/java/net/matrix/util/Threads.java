/*
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程相关工具。
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
	 *            等待毫秒数
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
	 *            等待时间
	 * @param unit
	 *            时间单位
	 */
	public static void sleep(final long duration, final TimeUnit unit) {
		try {
			Thread.sleep(unit.toMillis(duration));
		} catch (InterruptedException e) {
			LOG.trace("", e);
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * 保证不会有 Exception 抛出到线程池的 Runnable，防止用户没有捕捉异常导致中断了线程池中的线程。
	 */
	public static class WrapExceptionRunnable
		implements Runnable {
		/**
		 * 被包装的 Runnable。
		 */
		private final Runnable runnable;

		/**
		 * 包装构造器。
		 * 
		 * @param runnable
		 *            被包装的 Runnable
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
}
