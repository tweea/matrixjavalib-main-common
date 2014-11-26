/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
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

		Assertions.assertThat(Collections3.extractToString(list, "id", ",")).isEqualTo("1,2");
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
		List<Integer> result = Collections3.extractToList(list, "id");
		Assertions.assertThat(result).hasSize(2);
		Assertions.assertThat(result).containsExactly(1, 2);
	}

	@Test
	public void convertCollectionToString() {
		List<String> list = new ArrayList();
		list.add("aa");
		list.add("bb");
		String result = Collections3.convertToString(list, "<li>", "</li>");
		Assertions.assertThat(result).isEqualTo("<li>aa</li><li>bb</li>");
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
