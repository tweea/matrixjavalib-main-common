/*
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.lang;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ReflectionsTest {
    @Test
    public void testGetFieldValue() {
        TestBean bean = new TestBean();

        Object value = Reflections.getFieldValue(bean, "privateField");
        assertThat(value).isEqualTo(1);

        value = Reflections.getFieldValue(bean, "publicField");
        assertThat(value).isEqualTo(1);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> Reflections.getFieldValue(bean, "notExist"));
    }

    @Test
    public void testSetFieldValue() {
        TestBean bean = new TestBean();

        Reflections.setFieldValue(bean, "privateField", 2);
        assertThat(bean.inspectPrivateField()).isEqualTo(2);

        Reflections.setFieldValue(bean, "publicField", 2);
        assertThat(bean.inspectPublicField()).isEqualTo(2);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> Reflections.setFieldValue(bean, "notExist", 2));
    }

    @Test
    public void testInvokeGetter() {
        TestBean bean = new TestBean();

        Object value = Reflections.invokeGetter(bean, "publicField");
        assertThat(value).isEqualTo(bean.inspectPublicField() + 1);
    }

    @Test
    public void testInvokeSetter() {
        TestBean bean = new TestBean();

        Reflections.invokeSetter(bean, "publicField", 10);
        assertThat(bean.inspectPublicField()).isEqualTo(10 + 1);
    }

    @Test
    public void testInvokeMethod() {
        TestBean bean = new TestBean();

        // 使用方法名+参数类型的匹配
        Object value = Reflections.invokeMethod(bean, "privateMethod", new Class[] {
            String.class
        }, new Object[] {
            "calvin"
        });
        assertThat(value).isEqualTo("hello calvin");

        // 方法名错
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> Reflections.invokeMethod(bean, "notExistMethod", new Class[] {
            String.class
        }, new Object[] {
            "calvin"
        }));

        // 参数类型错
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> Reflections.invokeMethod(bean, "privateMethod", new Class[] {
            Integer.class
        }, new Object[] {
            "calvin"
        }));
    }

    @Test
    public void testInvokeMethodByName() {
        TestBean bean = new TestBean();

        // 仅匹配方法名
        Object value = Reflections.invokeMethodByName(bean, "privateMethod", new Object[] {
            "calvin"
        });
        assertThat(value).isEqualTo("hello calvin");

        // 方法名错
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> Reflections.invokeMethodByName(bean, "notExistMethod", new Object[] {
            "calvin"
        }));
    }

    @Test
    public void testGetClassGenricType() {
        // 获取第1，2个泛型类型
        assertThat(Reflections.getClassGenricType(TestBean.class)).isEqualTo(String.class);
        assertThat(Reflections.getClassGenricType(TestBean.class, 0)).isEqualTo(String.class);
        assertThat(Reflections.getClassGenricType(TestBean.class, 1)).isEqualTo(Long.class);

        // 定义父类时无泛型定义
        assertThat(Reflections.getClassGenricType(TestBean2.class)).isEqualTo(Object.class);

        // 无父类定义
        assertThat(Reflections.getClassGenricType(TestBean3.class)).isEqualTo(Object.class);
    }

    /**
     * @param <T>
     *     T
     * @param <ID>
     *     ID
     */
    public static class ParentBean<T, ID> {
    }

    public static class TestBean
        extends ParentBean<String, Long> {
        private int privateField = 1;

        private int publicField = 1;

        public int getPublicField() {
            return publicField + 1;
        }

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

        public String publicMethod(String text) {
            return privateMethod(text);
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
