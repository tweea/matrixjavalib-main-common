/*
 * $Id: EncodesTest.java 686 2013-09-04 09:01:06Z tweea@263.net $
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.util;

import org.junit.Assert;
import org.junit.Test;

public class EncodesTest {
	@Test
	public void base62Encode() {
		long num = 63;

		String result = Encodes.encodeBase62(num);
		Assert.assertEquals("11", result);
		Assert.assertEquals(num, Encodes.decodeBase62(result));
	}

	@Test
	public void urlEncode() {
		String input = "http://locahost/?q=中文&t=1";
		String result = Encodes.urlEncode(input);
		System.out.println(result);

		Assert.assertEquals(input, Encodes.urlDecode(result));
	}
}
