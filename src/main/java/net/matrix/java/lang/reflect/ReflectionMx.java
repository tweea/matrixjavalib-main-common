/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import net.matrix.text.ResourceBundleMessageFormatter;

/**
 * 反射工具。
 */
public final class ReflectionMx {
    /**
     * 区域相关资源。
     */
    private static final ResourceBundleMessageFormatter RBMF = new ResourceBundleMessageFormatter(ReflectionMx.class).useCurrentLocale();

    /**
     * 属性获取方法前缀。
     */
    private static final String GETTER_PREFIX = "get";

    /**
     * 布尔类型属性获取方法前缀。
     */
    private static final String IS_PREFIX = "is";

    /**
     * 属性设置方法前缀。
     */
    private static final String SETTER_PREFIX = "set";

    /**
     * 阻止实例化。
     */
    private ReflectionMx() {
    }

    /**
     * 获取反射对象的 {@code accessible} 标识。
     * 
     * @param accessibleObject
     *     反射对象。
     * @return {@code accessible} 标识。
     */
    public static boolean isAccessible(AccessibleObject accessibleObject) {
        return accessibleObject.isAccessible();
    }

    /**
     * 设置忽略字段的访问控制。
     * 
     * @param field
     *     字段。
     */
    public static void makeAccessible(Field field) {
        if (isAccessible(field)) {
            return;
        }

        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
            || Modifier.isFinal(field.getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * 按名称获取字段，并设置忽略字段的访问控制。
     * 
     * @param targetType
     *     目标类型。
     * @param name
     *     字段名。
     * @return 字段。
     */
    public static Field getAccessibleField(Class<?> targetType, String name) {
        return FieldUtils.getField(targetType, name, true);
    }

    /**
     * 按名称获取目标对象字段值。
     * 
     * @param target
     *     目标对象。
     * @param name
     *     字段名。
     * @return 字段值。
     */
    public static <T> T getFieldValue(Object target, String name) {
        try {
            return (T) FieldUtils.readField(target, name, true);
        } catch (IllegalAccessException e) {
            throw new UncheckedReflectiveOperationException(e);
        }
    }

    /**
     * 获取目标对象字段值。用于反复调用的场景。
     * 
     * @param target
     *     目标对象。
     * @param field
     *     字段。
     * @return 字段值。
     */
    public static <T> T getFieldValue(Object target, Field field) {
        try {
            return (T) field.get(target);
        } catch (IllegalAccessException e) {
            throw new UncheckedReflectiveOperationException(e);
        }
    }

    /**
     * 按名称设置目标对象字段值。
     * 
     * @param target
     *     目标对象。
     * @param name
     *     字段名。
     * @param value
     *     字段值。
     */
    public static void setFieldValue(Object target, String name, Object value) {
        try {
            FieldUtils.writeField(target, name, value, true);
        } catch (IllegalAccessException e) {
            throw new UncheckedReflectiveOperationException(e);
        }
    }

    /**
     * 设置目标对象字段值。用于反复调用的场景。
     * 
     * @param target
     *     目标对象。
     * @param field
     *     字段。
     * @param value
     *     字段值。
     */
    public static void setFieldValue(Object target, Field field, Object value) {
        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            throw new UncheckedReflectiveOperationException(e);
        }
    }

    /**
     * 设置忽略方法的访问控制。
     * 
     * @param method
     *     方法。
     */
    public static void makeAccessible(Method method) {
        if (isAccessible(method)) {
            return;
        }

        if (!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
            method.setAccessible(true);
        }
    }

    /**
     * 按名称和参数类型获取方法，并设置忽略方法的访问控制。
     * 
     * @param targetType
     *     目标类型。
     * @param name
     *     方法名。
     * @param parameterTypes
     *     参数类型。
     * @return 方法。
     */
    public static Method getAccessibleMethod(Class<?> targetType, String name, Class<?>... parameterTypes) {
        Method method = MethodUtils.getMatchingMethod(targetType, name, parameterTypes);
        if (method == null) {
            return null;
        }

        makeAccessible(method);
        return method;
    }

    /**
     * 按名称获取方法，并设置忽略方法的访问控制。
     * 
     * @param targetType
     *     目标类型。
     * @param name
     *     方法名。
     * @return 方法。
     */
    public static Method getAccessibleMethodByName(Class<?> targetType, String name) {
        for (Class<?> searchType = targetType; searchType != null; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(name)) {
                    makeAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 按名称和参数类型调用目标对象方法，根据传入参数的实际类型进行匹配。
     * 性能较差，仅用于单次调用。
     * 
     * @param target
     *     目标对象。
     * @param name
     *     方法名。
     * @param parameterValues
     *     参数值。
     * @return 方法返回值。
     */
    public static <T> T invokeMethod(Object target, String name, Object... parameterValues) {
        parameterValues = ArrayUtils.nullToEmpty(parameterValues);
        Class<?>[] parameterTypes = ClassUtils.toClass(parameterValues);
        return invokeMethod(target, name, parameterTypes, parameterValues);
    }

    /**
     * 按名称和参数类型调用目标对象方法。
     * 性能较差，仅用于单次调用。
     * 
     * @param target
     *     目标对象。
     * @param name
     *     方法名。
     * @param parameterTypes
     *     参数类型。
     * @param parameterValues
     *     参数值。
     * @return 方法返回值。
     */
    public static <T> T invokeMethod(Object target, String name, Class<?>[] parameterTypes, Object[] parameterValues) {
        Class<?> targetType = target.getClass();
        Method method = getAccessibleMethod(targetType, name, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException(RBMF.format("在类 {0} 中找不到方法 {1}", targetType, name));
        }

        return invokeMethod(target, method, parameterValues);
    }

    /**
     * 按名称调用目标对象方法，只匹配方法名，如果有多个同名方法调用第一个。
     * 性能较差，仅用于单次调用。
     * 
     * @param target
     *     目标对象。
     * @param name
     *     方法名。
     * @param parameterValues
     *     参数值。
     * @return 方法返回值。
     */
    public static <T> T invokeMethodByName(Object target, String name, Object... parameterValues) {
        Class<?> targetType = target.getClass();
        Method method = getAccessibleMethodByName(targetType, name);
        if (method == null) {
            throw new IllegalArgumentException(RBMF.format("在类 {0} 中找不到方法 {1}", targetType, name));
        }

        return invokeMethod(target, method, parameterValues);
    }

    /**
     * 调用目标对象方法，用于反复调用的场景。
     * 
     * @param target
     *     目标对象。
     * @param method
     *     方法。
     * @param parameterValues
     *     参数值。
     * @return 方法返回值。
     */
    public static <T> T invokeMethod(Object target, Method method, Object... parameterValues) {
        try {
            return (T) method.invoke(target, parameterValues);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new UncheckedReflectiveOperationException(e);
        }
    }

    /**
     * 按属性名获取属性获取方法，并设置忽略方法的访问控制。
     * 
     * @param targetType
     *     目标类型。
     * @param propertyName
     *     属性名。
     * @return 方法。
     */
    public static Method getAccessibleGetterMethod(Class<?> targetType, String propertyName) {
        String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(propertyName);
        Method method = getAccessibleMethod(targetType, getterMethodName);
        if (method == null) {
            getterMethodName = IS_PREFIX + StringUtils.capitalize(propertyName);
            method = getAccessibleMethod(targetType, getterMethodName);
        }
        return method;
    }

    /**
     * 按属性名获取属性设置方法，并设置忽略方法的访问控制。
     * 
     * @param targetType
     *     目标类型。
     * @param propertyName
     *     属性名。
     * @param parameterType
     *     属性类型。
     * @return 方法。
     */
    public static Method getAccessibleSetterMethod(Class<?> targetType, String propertyName, Class<?> parameterType) {
        String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(propertyName);
        return getAccessibleMethod(targetType, setterMethodName, parameterType);
    }

    /**
     * 调用属性获取方法。
     * 
     * @param target
     *     目标对象。
     * @param propertyName
     *     属性名。
     * @return 属性值。
     */
    public static <T> T invokeGetter(Object target, String propertyName) {
        Class<?> targetType = target.getClass();
        Method method = getAccessibleGetterMethod(targetType, propertyName);
        if (method == null) {
            throw new IllegalArgumentException(RBMF.format("在类 {0} 中找不到属性获取方法 {1}", targetType, propertyName));
        }

        return invokeMethod(target, method);
    }

    /**
     * 调用属性设置方法。
     * 
     * @param target
     *     目标对象。
     * @param propertyName
     *     属性名。
     * @param parameterType
     *     属性类型。
     * @param propertyValue
     *     属性值
     */
    public static void invokeSetter(Object target, String propertyName, Class<?> parameterType, Object propertyValue) {
        Class<?> targetType = target.getClass();
        Method method = getAccessibleSetterMethod(targetType, propertyName, parameterType);
        if (method == null) {
            throw new IllegalArgumentException(RBMF.format("在类 {0} 中找不到属性设置方法 {1}", targetType, propertyName));
        }

        invokeMethod(target, method, propertyValue);
    }
}
