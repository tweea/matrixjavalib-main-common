/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class Collecitons3Test {
	@Test
	public void convertElementPropertyToString() {
		TestBean3 bean1 = new TestBean3();
		bean1.setId(1);
		TestBean3 bean2 = new TestBean3();
		bean2.setId(2);

		List list = new ArrayList();
		list.add(bean1);
		list.add(bean2);

		Assert.assertEquals("1,2", Collections3.extractToString(list, "id", ","));
	}

	@Test
	public void convertElementPropertyToList() {
		TestBean3 bean1 = new TestBean3();
		bean1.setId(1);
		TestBean3 bean2 = new TestBean3();
		bean2.setId(2);

		List list = new ArrayList();
		list.add(bean1);
		list.add(bean2);
		List<String> result = Collections3.extractToList(list, "id");
		Assert.assertEquals(2, result.size());
		Assert.assertEquals(1, result.get(0));
	}

	@Test
	public void convertCollectionToString() {
		List<String> list = new ArrayList();
		list.add("aa");
		list.add("bb");
		String result = Collections3.convertToString(list, "<li>", "</li>");
		Assert.assertEquals("<li>aa</li><li>bb</li>", result);
	}

	public static class TestBean3 {
		private int id;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
	}
}
