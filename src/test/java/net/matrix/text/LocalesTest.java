/*
 * 版权所有 2013 Matrix。
 * 保留所有权利。
 */
package net.matrix.text;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

public class LocalesTest {
	@Test
	public void testCurrent() {
		Assert.assertEquals(Locale.getDefault(), Locales.current());

		Locales.current(Locale.CHINA);
		Assert.assertEquals(Locale.CHINA, Locales.current());

		Locales.current(Locale.getDefault());
		Assert.assertEquals(Locale.getDefault(), Locales.current());
	}
}
