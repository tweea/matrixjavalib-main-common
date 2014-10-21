/*
 * $Id: ReflectionRuntimeExceptionTest.java 582 2013-03-08 02:52:38Z tweea $
 * 版权所有 2013 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

public class ReflectionRuntimeExceptionTest {
	@Test
	public void testReflectionRuntimeExceptionThrowable() {
		Exception cause = new Exception();
		ReflectionRuntimeException exception = new ReflectionRuntimeException(cause);
		Assert.assertSame(cause, exception.getCause());

		Exception target = new Exception();
		cause = new InvocationTargetException(target);
		exception = new ReflectionRuntimeException(cause);
		Assert.assertSame(target, exception.getCause());
	}

	@Test
	public void testInitCauseThrowable() {
		Exception cause = new Exception();
		ReflectionRuntimeException exception = new ReflectionRuntimeException();
		exception.initCause(cause);
		Assert.assertSame(cause, exception.getCause());

		Exception target = new Exception();
		cause = new InvocationTargetException(target);
		exception = new ReflectionRuntimeException();
		exception.initCause(cause);
		Assert.assertSame(target, exception.getCause());
	}
}
