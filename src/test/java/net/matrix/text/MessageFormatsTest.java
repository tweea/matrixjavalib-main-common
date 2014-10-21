/*
 * $Id: MessageFormatsTest.java 685 2013-09-04 08:53:35Z tweea@263.net $
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.text;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Test;

public class MessageFormatsTest {
	@Test
	public void testFormatFallback() {
		Assert.assertEquals("1, 2, 3", MessageFormats.formatFallback("1", "2", 3));
	}

	@Test
	public void testFormat() {
		Assert.assertEquals("1 + 2 = 3", MessageFormats.format("{0} + {1} = {2}", Locale.CHINA, 1, 2, 3));
	}

	@Test
	public void testFormatBundle() {
		ResourceBundle bundle = ResourceBundles.getBundle("global", Locale.CHINA);
		Assert.assertEquals("一双绣花鞋", MessageFormats.format(bundle, "message", "绣花鞋"));
	}

	@Test
	public void testFormatBundle_fallback() {
		ResourceBundle bundle = ResourceBundles.getBundle("global", Locale.CHINA);
		Assert.assertEquals("1, 2, 3", MessageFormats.format(bundle, "1", "2", 3));
	}
}
