/*
 * 版权所有 2013 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class ExceptionsTest {
	@Test
	public void unchecked() {
		Exception exception = new Exception("my exception");
		RuntimeException runtimeException = Exceptions.unchecked(exception);
		Assert.assertEquals(exception, runtimeException.getCause());

		RuntimeException runtimeException2 = Exceptions.unchecked(runtimeException);
		Assert.assertEquals(runtimeException, runtimeException2);
	}

	@Test
	public void isCausedBy() {
		IOException ioexception = new IOException("my exception");
		IllegalStateException illegalStateException = new IllegalStateException(ioexception);
		RuntimeException runtimeException = new RuntimeException(illegalStateException);

		Assert.assertTrue(Exceptions.isCausedBy(runtimeException, IOException.class));
		Assert.assertTrue(Exceptions.isCausedBy(runtimeException, IllegalStateException.class, IOException.class));
		Assert.assertTrue(Exceptions.isCausedBy(runtimeException, Exception.class));
		Assert.assertFalse(Exceptions.isCausedBy(runtimeException, IllegalAccessException.class));
	}
}
