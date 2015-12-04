/*
 * Copyright(C) 2015 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/**
 * 创建 ThreadPool 的工具类。
 * 
 * 对比 JDK 中 Executors 的 newFixedThreadPool() 和 newCachedThreadPool() 函数，提供更多有用的配置项。
 * 
 * 使用示例：
 * 
 * <pre>
 * ExecutorService ExecutorService = new FixedThreadPoolBuilder().setPoolSize(10).build();
 * </pre>
 */
public interface ThreadPoolBuilder {
	/**
	 * 默认策略为 AbortPolicy，抛出 RejectedExecutionException 异常。
	 */
	RejectedExecutionHandler DEFAULT_REJECT_HANDLER = new AbortPolicy();

	ExecutorService build();

	/**
	 * 创建 FixedThreadPool。
	 * 
	 * 1. 任务提交时，如果线程数还没达到 poolSize 即创建新线程并绑定任务（即 poolSize 次提交后线程总数必达到 poolSize，不会重用之前的线程）
	 * 
	 * 1.a poolSize 是必填项，不能忽略。
	 * 
	 * 2. 第 poolSize 次任务提交后，新增任务放入 Queue 中，Pool 中的所有线程从 Queue 中 take 任务执行。
	 * 
	 * 2.a Queue 默认为无限长的 LinkedBlockingQueue， 也可以设置 queueSize 换成有界的队列。
	 * 
	 * 2.b 如果使用有界队列，当队列满了之后，会调用 RejectHandler 进行处理，默认为 AbortPolicy，抛出 RejectedExecutionException 异常。
	 * 其他可选的 Policy 包括静默放弃当前任务（Discard），放弃 Queue 里最老的任务（DisacardOldest），或由主线程来直接执行（CallerRuns）。
	 * 
	 * 3. 因为线程全部为 core 线程，所以不会在空闲回收。
	 */
	class FixedThreadPoolBuilder
		implements ThreadPoolBuilder {
		private int poolSize = 0;

		private int queueSize = 0;

		private ThreadFactory threadFactory;

		private RejectedExecutionHandler rejectHandler;

		public FixedThreadPoolBuilder setPoolSize(final int poolSize) {
			this.poolSize = poolSize;
			return this;
		}

		public FixedThreadPoolBuilder setQueueSize(final int queueSize) {
			this.queueSize = queueSize;
			return this;
		}

		public FixedThreadPoolBuilder setThreadFactory(final ThreadFactory threadFactory) {
			this.threadFactory = threadFactory;
			return this;
		}

		public FixedThreadPoolBuilder setRejectHanlder(final RejectedExecutionHandler rejectHandler) {
			this.rejectHandler = rejectHandler;
			return this;
		}

		@Override
		public ExecutorService build() {
			if (poolSize < 1) {
				throw new IllegalArgumentException("size not set");
			}

			BlockingQueue<Runnable> queue;
			if (queueSize == 0) {
				queue = new LinkedBlockingQueue<>();
			} else {
				queue = new ArrayBlockingQueue<>(queueSize);
			}

			if (threadFactory == null) {
				threadFactory = Executors.defaultThreadFactory();
			}

			if (rejectHandler == null) {
				rejectHandler = DEFAULT_REJECT_HANDLER;
			}

			return new ThreadPoolExecutor(poolSize, poolSize, 0L, TimeUnit.MILLISECONDS, queue, threadFactory, rejectHandler);
		}
	}

	/**
	 * 创建 CachedThreadPool。
	 * 
	 * 1. 任务提交时，如果线程数还没达到 minSize 即创建新线程并绑定任务（即 minSize 次提交后线程总数必达到 minSize，不会重用之前的线程）
	 * 
	 * 1.a minSize 默认为 0 ，可进行设置保证有基本的线程处理请求。
	 * 
	 * 2. 第 minSize 次任务提交后，新增任务提交进 SynchronousQueue 后，如果没有空闲线程立刻处理，则会创建新的线程，直到总线程数达到上限。
	 * 
	 * 2.a maxSize 默认为 Integer.Max，可进行设置。
	 * 
	 * 2.b 如果设置了 maxSize，当总线程数达到上限，会调用 RejectHandler 进行处理，默认为 AbortPolicy，抛出
	 * RejectedExecutionException 异常。
	 * 其他可选的 Policy 包括静默放弃当前任务（Discard），或由主线程来直接执行（CallerRuns）。
	 * 
	 * 3. minSize 以上，maxSize 以下的线程，如果在 keepAliveTime 中都 poll 不到任务执行将会被结束掉，keeAliveTime 默认为 60 秒，可设置。
	 */
	class CachedThreadPoolBuilder
		implements ThreadPoolBuilder {
		private int minSize = 0;

		private int maxSize = Integer.MAX_VALUE;

		private int keepAlive = 60;

		private ThreadFactory threadFactory;

		private RejectedExecutionHandler rejectHandler;

		public CachedThreadPoolBuilder setMinSize(final int minSize) {
			this.minSize = minSize;
			return this;
		}

		public CachedThreadPoolBuilder setMaxSize(final int maxSize) {
			this.maxSize = maxSize;
			return this;
		}

		public CachedThreadPoolBuilder setKeepAlive(final int keepAlive) {
			this.keepAlive = keepAlive;
			return this;
		}

		public CachedThreadPoolBuilder setThreadFactory(final ThreadFactory threadFactory) {
			this.threadFactory = threadFactory;
			return this;
		}

		public CachedThreadPoolBuilder setRejectHanlder(final RejectedExecutionHandler rejectHandler) {
			this.rejectHandler = rejectHandler;
			return this;
		}

		@Override
		public ExecutorService build() {
			if (threadFactory == null) {
				threadFactory = Executors.defaultThreadFactory();
			}

			if (rejectHandler == null) {
				rejectHandler = DEFAULT_REJECT_HANDLER;
			}

			return new ThreadPoolExecutor(minSize, maxSize, keepAlive, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), threadFactory, rejectHandler);
		}
	}
}
