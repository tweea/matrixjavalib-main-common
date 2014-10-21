/*
 * $Id: ReflectionsTest.java 801 2013-12-26 06:28:45Z tweea@263.net $
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.lang;

import org.junit.Assert;
import org.junit.Test;

public class ReflectionsTest {
	@Test
	public void getAndSetFieldValue() {
		TestBean bean = new TestBean();
		// 无需getter函数, 直接读取privateField
		Assert.assertEquals(1, Reflections.getFieldValue(bean, "privateField"));
		// 绕过将publicField+1的getter函数,直接读取publicField的原始值
		Assert.assertEquals(1, Reflections.getFieldValue(bean, "publicField"));

		bean = new TestBean();
		// 无需setter函数, 直接设置privateField
		Reflections.setFieldValue(bean, "privateField", 2);
		Assert.assertEquals(2, bean.inspectPrivateField());

		// 绕过将publicField+1的setter函数,直接设置publicField的原始值
		Reflections.setFieldValue(bean, "publicField", 2);

		Assert.assertEquals(2, bean.inspectPublicField());

		try {
			Reflections.getFieldValue(bean, "notExist");
			Assert.fail("should throw exception here");
		} catch (IllegalArgumentException e) {
		}

		try {
			Reflections.setFieldValue(bean, "notExist", 2);
			Assert.fail("should throw exception here");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void invokeGetterAndSetter() {
		TestBean bean = new TestBean();
		Assert.assertEquals(bean.inspectPublicField() + 1, Reflections.invokeGetter(bean, "publicField"));

		bean = new TestBean();
		// 通过setter的函数将+1
		Reflections.invokeSetter(bean, "publicField", 10);
		Assert.assertEquals(10 + 1, bean.inspectPublicField());
	}

	@Test
	public void invokeMethod() {
		TestBean bean = new TestBean();
		// 使用函数名+参数类型的匹配
		Assert.assertEquals(bean.privateMethod("calvin"), Reflections.invokeMethod(bean, "privateMethod", new Class[] {
			String.class
		}, new Object[] {
			"calvin"
		}));

		// 仅匹配函数名
		Assert.assertEquals("hello calvin", Reflections.invokeMethodByName(bean, "privateMethod", new Object[] {
			"calvin"
		}));

		// 函数名错
		try {
			Reflections.invokeMethod(bean, "notExistMethod", new Class[] {
				String.class
			}, new Object[] {
				"calvin"
			});
			Assert.fail("should throw exception here");
		} catch (IllegalArgumentException e) {
		}

		// 参数类型错
		try {
			Reflections.invokeMethod(bean, "privateMethod", new Class[] {
				Integer.class
			}, new Object[] {
				"calvin"
			});
			Assert.fail("should throw exception here");
		} catch (RuntimeException e) {
		}

		// 函数名错
		try {
			Reflections.invokeMethodByName(bean, "notExistMethod", new Object[] {
				"calvin"
			});
			Assert.fail("should throw exception here");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void getClassGenricType() {
		// 获取第1，2个泛型类型
		Assert.assertEquals(String.class, Reflections.getClassGenricType(TestBean.class));
		Assert.assertEquals(Long.class, Reflections.getClassGenricType(TestBean.class, 1));

		// 定义父类时无泛型定义
		Assert.assertEquals(Object.class, Reflections.getClassGenricType(TestBean2.class));

		// 无父类定义
		Assert.assertEquals(Object.class, Reflections.getClassGenricType(TestBean3.class));
	}

	/**
	 * @param <T>
	 *            T
	 * @param <ID>
	 *            ID
	 */
	public static class ParentBean<T, ID> {
	}

	public static class TestBean
		extends ParentBean<String, Long> {
		/** 没有getter/setter的field */
		private int privateField = 1;

		/** 有getter/setter的field */
		private int publicField = 1;

		// 通過getter函數會比屬性值+1
		public int getPublicField() {
			return publicField + 1;
		}

		// 通過setter函數會被比輸入值加1
		public void setPublicField(int publicField) {
			this.publicField = publicField + 1;
		}

		public int inspectPrivateField() {
			return privateField;
		}

		public int inspectPublicField() {
			return publicField;
		}

		private String privateMethod(String text) {
			return "hello " + text;
		}
	}

	public static class TestBean2
		extends ParentBean {
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
