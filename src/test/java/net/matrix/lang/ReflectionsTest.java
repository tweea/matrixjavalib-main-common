/*
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.lang;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionsTest {
	private static final Logger LOG = LoggerFactory.getLogger(ReflectionsTest.class);

	@Test
	public void getAndSetFieldValue() {
		TestBean bean = new TestBean();
		// 无需getter函数, 直接读取privateField
		Object value = Reflections.getFieldValue(bean, "privateField");
		Assertions.assertThat(value).isEqualTo(1);
		// 绕过将publicField+1的getter函数,直接读取publicField的原始值
		value = Reflections.getFieldValue(bean, "publicField");
		Assertions.assertThat(value).isEqualTo(1);

		bean = new TestBean();
		// 无需setter函数, 直接设置privateField
		Reflections.setFieldValue(bean, "privateField", 2);
		Assertions.assertThat(bean.inspectPrivateField()).isEqualTo(2);

		// 绕过将publicField+1的setter函数,直接设置publicField的原始值
		Reflections.setFieldValue(bean, "publicField", 2);
		Assertions.assertThat(bean.inspectPublicField()).isEqualTo(2);

		try {
			Reflections.getFieldValue(bean, "notExist");
			Assertions.fail("should throw exception here");
		} catch (IllegalArgumentException e) {
			LOG.trace("", e);
		}

		try {
			Reflections.setFieldValue(bean, "notExist", 2);
			Assertions.fail("should throw exception here");
		} catch (IllegalArgumentException e) {
			LOG.trace("", e);
		}
	}

	@Test
	public void invokeGetterAndSetter() {
		TestBean bean = new TestBean();
		Object value = Reflections.invokeGetter(bean, "publicField");
		Assertions.assertThat(value).isEqualTo(bean.inspectPublicField() + 1);

		bean = new TestBean();
		// 通过setter的函数将+1
		Reflections.invokeSetter(bean, "publicField", 10);
		Assertions.assertThat(bean.inspectPublicField()).isEqualTo(10 + 1);
	}

	@Test
	public void invokeMethod() {
		TestBean bean = new TestBean();
		// 使用函数名+参数类型的匹配
		Object value = Reflections.invokeMethod(bean, "privateMethod", new Class[] {
			String.class
		}, new Object[] {
			"calvin"
		});
		Assertions.assertThat(value).isEqualTo(bean.privateMethod("calvin"));

		// 仅匹配函数名
		value = Reflections.invokeMethodByName(bean, "privateMethod", new Object[] {
			"calvin"
		});
		Assertions.assertThat(value).isEqualTo("hello calvin");

		// 函数名错
		try {
			Reflections.invokeMethod(bean, "notExistMethod", new Class[] {
				String.class
			}, new Object[] {
				"calvin"
			});
			Assertions.fail("should throw exception here");
		} catch (IllegalArgumentException e) {
			LOG.trace("", e);
		}

		// 参数类型错
		try {
			Reflections.invokeMethod(bean, "privateMethod", new Class[] {
				Integer.class
			}, new Object[] {
				"calvin"
			});
			Assertions.fail("should throw exception here");
		} catch (RuntimeException e) {
			LOG.trace("", e);
		}

		// 函数名错
		try {
			Reflections.invokeMethodByName(bean, "notExistMethod", new Object[] {
				"calvin"
			});
			Assertions.fail("should throw exception here");
		} catch (IllegalArgumentException e) {
			LOG.trace("", e);
		}
	}

	@Test
	public void getClassGenricType() {
		// 获取第1，2个泛型类型
		Assertions.assertThat(Reflections.getClassGenricType(TestBean.class)).isEqualTo(String.class);
		Assertions.assertThat(Reflections.getClassGenricType(TestBean.class, 1)).isEqualTo(Long.class);

		// 定义父类时无泛型定义
		Assertions.assertThat(Reflections.getClassGenricType(TestBean2.class)).isEqualTo(Object.class);

		// 无父类定义
		Assertions.assertThat(Reflections.getClassGenricType(TestBean3.class)).isEqualTo(Object.class);
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
		extends
		ParentBean<String, Long> {
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
		extends
		ParentBean {
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
