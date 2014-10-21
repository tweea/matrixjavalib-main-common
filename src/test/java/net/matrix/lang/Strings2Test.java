/*
 * $Id: Strings2Test.java 684 2013-09-04 08:31:20Z tweea@263.net $
 * 版权所有 2013 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import org.junit.Assert;
import org.junit.Test;

public class Strings2Test {
	@Test
	public void testReplaceAllBetweenDelimiter() {
		Assert.assertEquals("abc<Good>xyz", Strings2.replaceAllBetweenDelimiter("abc<Bad>xyz", "<", ">", "Bad", "Good"));
	}
}
