/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import org.junit.jupiter.api.Test;

import net.matrix.lang.ReflectionsTestData.TestBean;
import net.matrix.lang.ReflectionsTestData.TestBean4;

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

        value = Reflections.getFieldValue(bean, "privateParentField");
        assertThat(value).isEqualTo("1");

        value = Reflections.getFieldValue(bean, "publicParentField");
        assertThat(value).isEqualTo(1L);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> Reflections.getFieldValue(bean, "notExist"));
    }

    @Test
    public void testSetFieldValue() {
        TestBean bean = new TestBean();

        Reflections.setFieldValue(bean, "privateField", 2);
        assertThat(bean.inspectPrivateField()).isEqualTo(2);

        Reflections.setFieldValue(bean, "publicField", 2);
        assertThat(bean.inspectPublicField()).isEqualTo(2);

        Reflections.setFieldValue(bean, "privateParentField", "2");
        assertThat(bean.inspectPrivateParentField()).isEqualTo("2");

        Reflections.setFieldValue(bean, "publicParentField", 2L);
        assertThat(bean.inspectPublicParentField()).isEqualTo(2L);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> Reflections.setFieldValue(bean, "notExist", 2));
    }

    @Test
    public void testInvokeGetter() {
        TestBean bean = new TestBean();
        TestBean4 bean4 = new TestBean4();

        Object value = Reflections.invokeGetter(bean, "publicField");
        assertThat(value).isEqualTo(bean.inspectPublicField() + 1);

        value = Reflections.invokeGetter(bean4, "publicField");
        assertThat(value).isEqualTo(bean4.inspectPublicField() + 1);
    }

    @Test
    public void testInvokeSetter() {
        TestBean bean = new TestBean();
        TestBean4 bean4 = new TestBean4();

        Reflections.invokeSetter(bean, "publicField", 10);
        assertThat(bean.inspectPublicField()).isEqualTo(10 + 1);

        Reflections.invokeSetter(bean4, "publicField", 10);
        assertThat(bean4.inspectPublicField()).isEqualTo(10 + 1);
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
}
