/*
 * $Id: ThreadsTest.java 336 2012-03-11 04:01:56Z tweea $
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadsTest {
	@Test
	public void gracefulShutdown()
		throws InterruptedException {
		Logger logger = LoggerFactory.getLogger("test");

		// time enough to shutdown
		ExecutorService pool = Executors.newSingleThreadExecutor();
		Runnable task = new Task(logger, 500, 0);
		pool.execute(task);
		Threads.gracefulShutdown(pool, 1000, 1000, TimeUnit.MILLISECONDS);
		Assert.assertTrue(pool.isTerminated());

		// time not enough to shutdown,call shutdownNow
		pool = Executors.newSingleThreadExecutor();
		task = new Task(logger, 1000, 0);
		pool.execute(task);
		Threads.gracefulShutdown(pool, 500, 1000, TimeUnit.MILLISECONDS);
		Assert.assertTrue(pool.isTerminated());

		// self thread interrupt while calling gracefulShutdown
		final ExecutorService self = Executors.newSingleThreadExecutor();
		task = new Task(logger, 100000, 0);
		self.execute(task);

		final CountDownLatch lock = new CountDownLatch(1);
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				lock.countDown();
				Threads.gracefulShutdown(self, 200000, 200000, TimeUnit.MILLISECONDS);
			}
		});
		thread.start();
		lock.await();
		thread.interrupt();
		Threads.sleep(500);
	}

	@Test
	public void normalShutdown() {
		Logger logger = LoggerFactory.getLogger("test");

		// time not enough to shutdown,write error log.
		ExecutorService pool = Executors.newSingleThreadExecutor();
		Runnable task = new Task(logger, 1000, 0);
		pool.execute(task);
		Threads.normalShutdown(pool, 500, TimeUnit.MILLISECONDS);
		Assert.assertTrue(pool.isTerminated());
	}

	static class Task
		implements Runnable {
		private Logger logger;

		private int runTime = 0;

		private int sleepTime;

		Task(Logger logger, int sleepTime, int runTime) {
			this.logger = logger;
			this.sleepTime = sleepTime;
			this.runTime = runTime;
		}

		@Override
		public void run() {
			System.out.println("start task");
			if (runTime > 0) {
				long start = System.currentTimeMillis();
				while (System.currentTimeMillis() - start < runTime) {
				}
			}

			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				logger.warn("InterruptedException");
			}
		}
	}
}
