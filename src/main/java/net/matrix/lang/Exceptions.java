/*
 * 版权所有 2013 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * 关于异常的工具类。
 */
public final class Exceptions {
	/**
	 * 阻止实例化。
	 */
	private Exceptions() {
	}

	/**
	 * 将 Exception 转换为 RuntimeException。
	 * 
	 * @param t
	 *            原始异常
	 * @return 运行时异常
	 */
	public static RuntimeException unchecked(final Throwable t) {
		if (t instanceof RuntimeException) {
			return (RuntimeException) t;
		} else {
			return new RuntimeException(t);
		}
	}

	/**
	 * 获取组合本异常信息与底层异常信息的异常描述， 适用于本异常为统一包装异常类，底层异常才是根本原因的情况。
	 * 
	 * @param t
	 *            异常
	 * @return 组合异常信息
	 */
	public static String getMessageWithRootCause(final Throwable t) {
		return ExceptionUtils.getMessage(t) + " root cause is " + ExceptionUtils.getRootCauseMessage(t);
	}

	/**
	 * 判断异常是否由某些底层的异常引起。
	 * 
	 * @param t
	 *            异常
	 * @param causeTypes
	 *            异常类型
	 * @return 异常链中包含参数中的类型为 true
	 */
	public static boolean isCausedBy(final Throwable t, final Class<? extends Throwable>... causeTypes) {
		for (Class<? extends Throwable> type : causeTypes) {
			if (ExceptionUtils.indexOfType(t, type) >= 0) {
				return true;
			}
		}
		return false;
	}
}
