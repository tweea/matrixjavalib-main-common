/*
 * $Id: Objects2Test.java 683 2013-09-04 08:19:39Z tweea@263.net $
 * 版权所有 2013 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import org.junit.Assert;
import org.junit.Test;

public class Objects2Test {
	@Test
	public void testIsNull() {
		Assert.assertEquals("A", Objects2.isNull("A", "B"));
		Assert.assertEquals("B", Objects2.isNull(null, "B"));
	}

	@Test
	public void testNullIf() {
		Assert.assertNull(Objects2.nullIf(null, null));
		Assert.assertNull(Objects2.nullIf("A", "A"));
		Assert.assertEquals("A", Objects2.nullIf("A", "B"));
	}
}
