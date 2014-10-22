/*
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
