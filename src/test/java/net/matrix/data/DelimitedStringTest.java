/*
 * Copyright(C) 2011 matrix
 * All right reserved.
 */
package net.matrix.data;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class DelimitedStringTest {
	@Test
	public void testCommaSeparatedStringList() {
		List<String> list = new DelimitedString();
		Assert.assertEquals(0, list.size());
		Assert.assertEquals("", list.toString());
	}

	@Test
	public void testCommaSeparatedStringListString() {
		List<String> list = new DelimitedString("a,bc,d");
		Assert.assertEquals(3, list.size());
		Assert.assertEquals("a,bc,d", list.toString());
	}

	@Test
	public void testCommaSeparatedStringListString_withDelimiter() {
		List<String> list = new DelimitedString("a=bc=d", "=");
		Assert.assertEquals(3, list.size());
		Assert.assertEquals("a=bc=d", list.toString());
	}

	@Test
	public void testCommaSeparatedStringListStringArray() {
		List<String> list = new DelimitedString(new String[] {
			"a", "bc", "d"
		});
		Assert.assertEquals(3, list.size());
		Assert.assertEquals("a,bc,d", list.toString());
	}

	@Test
	public void testCommaSeparatedStringListStringArray_withDelimiter() {
		List<String> list = new DelimitedString(new String[] {
			"a", "bc", "d"
		}, "+");
		Assert.assertEquals(3, list.size());
		Assert.assertEquals("a+bc+d", list.toString());
	}

	@Test
	public void testCommaSeparatedStringListListOfString() {
		List<String> list = new DelimitedString(Arrays.asList("a", "bc", "d"));
		Assert.assertEquals(3, list.size());
		Assert.assertEquals("a,bc,d", list.toString());
	}

	@Test
	public void testCommaSeparatedStringListListOfString_withDelimiter() {
		List<String> list = new DelimitedString(Arrays.asList("a", "bc", "d"), "-");
		Assert.assertEquals(3, list.size());
		Assert.assertEquals("a-bc-d", list.toString());
	}

	@Test
	public void testSubList() {
		List<String> list = new DelimitedString(Arrays.asList("a", "bc", "d"), "-").subList(0, 2);
		Assert.assertEquals(2, list.size());
		Assert.assertEquals("a-bc", list.toString());
	}
}
