/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import net.matrix.java.lang.reflect.ReflectionMxTestData.TestBean;
import net.matrix.java.lang.reflect.ReflectionMxTestData.TestBean2;
import net.matrix.java.lang.reflect.ReflectionMxTestData.TestBean3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ReflectionMxTest {
    @Test
    public void testMakeAccessible_field()
        throws ReflectiveOperationException {
        Class targetType = (new Object() {
            boolean v;

            @Override
            public String toString() {
                return Boolean.toString(v);
            }
        }).getClass();
        Field field = targetType.getDeclaredField("v");

        assertThat(ReflectionMx.isAccessible(field)).isFalse();
        ReflectionMx.makeAccessible(field);
        assertThat(ReflectionMx.isAccessible(field)).isTrue();
    }

    @Test
    public void testGetAccessibleField() {
        Field field = ReflectionMx.getAccessibleField(TestBean.class, "privateField");
        assertThat(field.getName()).isEqualTo("privateField");

        field = ReflectionMx.getAccessibleField(TestBean.class, "publicField");
        assertThat(field.getName()).isEqualTo("publicField");

        field = ReflectionMx.getAccessibleField(TestBean.class, "privateParentField");
        assertThat(field.getName()).isEqualTo("privateParentField");

        field = ReflectionMx.getAccessibleField(TestBean.class, "publicParentField");
        assertThat(field.getName()).isEqualTo("publicParentField");

        field = ReflectionMx.getAccessibleField(TestBean.class, "notExist");
        assertThat(field).isNull();
    }

    @Test
    public void testGetFieldValue() {
        TestBean bean = new TestBean();

        Object value = ReflectionMx.getFieldValue(bean, "privateField");
        assertThat(value).isEqualTo(1);

        value = ReflectionMx.getFieldValue(bean, "publicField");
        assertThat(value).isEqualTo(1);

        value = ReflectionMx.getFieldValue(bean, "privateParentField");
        assertThat(value).isEqualTo("1");

        value = ReflectionMx.getFieldValue(bean, "publicParentField");
        assertThat(value).isEqualTo(1L);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> ReflectionMx.getFieldValue(bean, "notExist"));
    }

    @Test
    public void testSetFieldValue() {
        TestBean bean = new TestBean();

        ReflectionMx.setFieldValue(bean, "privateField", 2);
        assertThat(bean.inspectPrivateField()).isEqualTo(2);

        ReflectionMx.setFieldValue(bean, "publicField", 2);
        assertThat(bean.inspectPublicField()).isEqualTo(2);

        ReflectionMx.setFieldValue(bean, "privateParentField", "2");
        assertThat(bean.inspectPrivateParentField()).isEqualTo("2");

        ReflectionMx.setFieldValue(bean, "publicParentField", 2L);
        assertThat(bean.inspectPublicParentField()).isEqualTo(2L);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> ReflectionMx.setFieldValue(bean, "notExist", 2));
    }

    @Test
    public void testMakeAccessible_constructor()
        throws ReflectiveOperationException {
        Constructor constructor = TestBean3.class.getDeclaredConstructor();

        assertThat(ReflectionMx.isAccessible(constructor)).isFalse();
        ReflectionMx.makeAccessible(constructor);
        assertThat(ReflectionMx.isAccessible(constructor)).isTrue();
    }

    @Test
    public void testMakeAccessible_method()
        throws ReflectiveOperationException {
        Class targetType = (new Object() {
            boolean v;

            boolean v() {
                return v;
            }

            @Override
            public String toString() {
                return Boolean.toString(v());
            }
        }).getClass();
        Method method = targetType.getDeclaredMethod("v");

        assertThat(ReflectionMx.isAccessible(method)).isFalse();
        ReflectionMx.makeAccessible(method);
        assertThat(ReflectionMx.isAccessible(method)).isTrue();
    }

    @Test
    public void testGetAccessibleMethod() {
        // 使用方法名+参数类型的匹配
        Method method = ReflectionMx.getAccessibleMethod(TestBean.class, "privateMethod", new Class[] {
            String.class
        });
        assertThat(method.getName()).isEqualTo("privateMethod");

        method = ReflectionMx.getAccessibleMethod(TestBean2.class, "privateMethod", new Class[] {
            String.class
        });
        assertThat(method.getName()).isEqualTo("privateMethod");

        // 方法名错
        assertThat(ReflectionMx.getAccessibleMethod(TestBean.class, "notExistMethod", new Class[] {
            String.class
        })).isNull();

        // 参数类型错
        assertThat(ReflectionMx.getAccessibleMethod(TestBean.class, "privateMethod", new Class[] {
            Integer.class
        })).isNull();
    }

    @Test
    public void testGetAccessibleMethodByName() {
        // 仅匹配方法名
        Method method = ReflectionMx.getAccessibleMethodByName(TestBean.class, "privateMethod");
        assertThat(method.getName()).isEqualTo("privateMethod");

        method = ReflectionMx.getAccessibleMethodByName(TestBean2.class, "privateMethod");
        assertThat(method.getName()).isEqualTo("privateMethod");

        // 方法名错
        assertThat(ReflectionMx.getAccessibleMethodByName(TestBean.class, "notExistMethod"));
    }

    @Test
    public void testInvokeMethod() {
        TestBean bean = new TestBean();

        // 使用方法名+参数类型的匹配
        Object value = ReflectionMx.invokeMethod(bean, "privateMethod", new Class[] {
            String.class
        }, new Object[] {
            "tom"
        });
        assertThat(value).isEqualTo("hello tom");

        // 方法名错
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> ReflectionMx.invokeMethod(bean, "notExistMethod", new Class[] {
            String.class
        }, new Object[] {
            "tom"
        }));

        // 参数类型错
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> ReflectionMx.invokeMethod(bean, "privateMethod", new Class[] {
            Integer.class
        }, new Object[] {
            "tom"
        }));
    }

    @Test
    public void testInvokeMethodByName() {
        TestBean bean = new TestBean();

        // 仅匹配方法名
        Object value = ReflectionMx.invokeMethodByName(bean, "privateMethod", new Object[] {
            "tom"
        });
        assertThat(value).isEqualTo("hello tom");

        // 方法名错
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> ReflectionMx.invokeMethodByName(bean, "notExistMethod", new Object[] {
            "tom"
        }));
    }

    @Test
    public void testInvokeGetter() {
        TestBean bean = new TestBean();
        TestBean2 bean4 = new TestBean2();

        Object value = ReflectionMx.invokeGetter(bean, "publicField");
        assertThat(value).isEqualTo(bean.inspectPublicField() + 1);

        value = ReflectionMx.invokeGetter(bean4, "publicField");
        assertThat(value).isEqualTo(bean4.inspectPublicField() + 1);
    }

    @Test
    public void testInvokeSetter() {
        TestBean bean = new TestBean();
        TestBean2 bean4 = new TestBean2();

        ReflectionMx.invokeSetter(bean, "publicField", int.class, 10);
        assertThat(bean.inspectPublicField()).isEqualTo(10 + 1);

        ReflectionMx.invokeSetter(bean4, "publicField", int.class, 10);
        assertThat(bean4.inspectPublicField()).isEqualTo(10 + 1);
    }
}
