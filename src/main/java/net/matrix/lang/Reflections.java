/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.matrix.java.lang.reflect.UncheckedReflectiveOperationException;

/**
 * 反射工具。
 * 提供调用 getter/setter 方法，访问私有成员，调用私有方法，获取泛型类型 Class，被 AOP 过的真实类等工具。
 */
public final class Reflections {
    /**
     * 日志记录器。
     */
    private static final Logger LOG = LoggerFactory.getLogger(Reflections.class);

    /**
     * Setter 方法前缀。
     */
    private static final String SETTER_PREFIX = "set";

    /**
     * Getter 方法前缀。
     */
    private static final String GETTER_PREFIX = "get";

    /**
     * Is 方法前缀。
     */
    private static final String IS_PREFIX = "is";

    /**
     * 阻止实例化。
     */
    private Reflections() {
    }

    /**
     * 直接获取目标对象成员值，无视 private/protected 修饰符，不经过 getter 方法。
     * 
     * @param target
     *     目标对象
     * @param name
     *     成员名
     * @return 成员值
     */
    public static <T> T getFieldValue(final Object target, final String name) {
        try {
            return (T) FieldUtils.readField(target, name, true);
        } catch (IllegalAccessException e) {
            throw new ImpossibleException(e);
        }
    }

    /**
     * 使用已获取的 Field，直接读取对象成员值，不经过 getter 方法。
     * 用于反复调用的场景。
     */
    public static <T> T getFieldValue(final Object target, final Field field) {
        try {
            return (T) field.get(target);
        } catch (IllegalAccessException e) {
            throw new ImpossibleException(e);
        }
    }

    /**
     * 直接设置目标对象成员值，无视 private/protected 修饰符，不经过 setter 方法。
     * 
     * @param target
     *     目标对象
     * @param name
     *     成员名
     * @param value
     *     成员值
     */
    public static void setFieldValue(final Object target, final String name, final Object value) {
        try {
            FieldUtils.writeField(target, name, value, true);
        } catch (IllegalAccessException e) {
            throw new ImpossibleException(e);
        }
    }

    /**
     * 使用预先获取的 Field，直接读取对象成员值，不经过 setter 方法。
     * 用于反复调用的场景。
     */
    public static void setFieldValue(final Object target, final Field field, final Object value) {
        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            throw new ImpossibleException(e);
        }
    }

    /**
     * 调用 Getter 方法。
     * 
     * @param target
     *     目标对象
     * @param name
     *     属性名
     * @return 属性值
     */
    public static <T> T invokeGetter(final Object target, final String name) {
        Method method = getAccessibleGetterMethod(target.getClass(), name);
        if (method == null) {
            throw new IllegalArgumentException("Could not find getter method [" + name + "] on target [" + target + ']');
        }
        return invokeMethod(target, method);
    }

    /**
     * 调用 Setter 方法，仅匹配方法名。
     * 
     * @param target
     *     目标对象
     * @param name
     *     属性名
     * @param value
     *     属性值
     */
    public static void invokeSetter(final Object target, final String name, final Object value) {
        Class valueType;
        if (value == null) {
            valueType = null;
        } else {
            valueType = value.getClass();
        }

        Method method = getAccessibleSetterMethod(target.getClass(), name, valueType);
        if (method == null) {
            throw new IllegalArgumentException("Could not find getter method [" + name + "] on target [" + target + ']');
        }
        invokeMethod(target, method, value);
    }

    /**
     * 反射调用对象方法，无视 private/protected 修饰符。
     * 根据传入参数的实际类型进行匹配，支持方法参数定义是接口、父类、原子类型等情况。
     * 性能较差，仅用于单次调用。
     */
    public static <T> T invokeMethod(Object target, String name, Object... parameterValues) {
        parameterValues = ArrayUtils.nullToEmpty(parameterValues);
        final Class<?>[] parameterTypes = ClassUtils.toClass(parameterValues);
        return invokeMethod(target, name, parameterTypes, parameterValues);
    }

    /**
     * 直接调用目标对象方法，无视 private/protected 修饰符。
     * 用于一次性调用的情况，否则应使用 getAccessibleMethod() 方法获得 Method 后反复调用。
     * 同时匹配方法名 + 参数类型。
     * 
     * @param target
     *     目标对象
     * @param name
     *     方法名
     * @param parameterTypes
     *     参数类型
     * @param parameterValues
     *     参数值
     * @return 返回值
     */
    public static <T> T invokeMethod(final Object target, final String name, final Class<?>[] parameterTypes, final Object[] parameterValues) {
        Method method = getAccessibleMethod(target.getClass(), name, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + name + "] on target [" + target + ']');
        }

        return invokeMethod(target, method, parameterValues);
    }

    /**
     * 直接调用目标对象方法，无视 private/protected 修饰符。
     * 用于一次性调用的情况，否则应使用 getAccessibleMethodByName() 方法获得 Method 后反复调用。
     * 只匹配方法名，如果有多个同名方法调用第一个。
     * 
     * @param target
     *     目标对象
     * @param name
     *     方法名
     * @param parameterValues
     *     参数值
     * @return 返回值
     */
    public static <T> T invokeMethodByName(final Object target, final String name, final Object[] parameterValues) {
        Method method = getAccessibleMethodByName(target.getClass(), name);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + name + "] on target [" + target + ']');
        }

        return invokeMethod(target, method, parameterValues);
    }

    /**
     * 调用预先获取的 Method，用于反复调用的场景。
     */
    public static <T> T invokeMethod(final Object target, Method method, Object... parameterValues) {
        try {
            return (T) method.invoke(target, parameterValues);
        } catch (ReflectiveOperationException e) {
            throw new UncheckedReflectiveOperationException(e);
        }
    }

    /**
     * 循环向上转型，获取对象的 DeclaredField，并强制设置为可访问。
     * 如向上转型到 Object 仍无法找到，返回 null。
     * 
     * @param targetType
     *     目标对象
     * @param name
     *     成员名
     * @return 成员
     */
    public static Field getAccessibleField(final Class<?> targetType, final String name) {
        for (Class<?> searchType = targetType; searchType != Object.class; searchType = searchType.getSuperclass()) {
            try {
                Field field = searchType.getDeclaredField(name);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {
                // Field 不在当前类定义，继续向上转型
                LOG.trace("", e);
            }
        }
        return null;
    }

    /**
     * 循环向上转型，获取对象的 DeclaredMethod，并强制设置为可访问。
     * 如向上转型到 Object 仍无法找到，返回 null。
     * 匹配方法名 + 参数类型。
     * 用于方法需要被多次调用的情况。先使用本方法先取得 Method，然后调用 Method.invoke(Object obj, Object... parameterValues)
     * 
     * @param targetType
     *     目标对象
     * @param name
     *     方法名
     * @param parameterTypes
     *     参数类型
     * @return 方法
     */
    public static Method getAccessibleMethod(final Class<?> targetType, final String name, final Class<?>... parameterTypes) {
        Method method = MethodUtils.getMatchingMethod(targetType, name, parameterTypes);
        if (method == null) {
            return null;
        }

        makeAccessible(method);
        return method;
    }

    /**
     * 循环向上转型，获取对象的 DeclaredMethod，并强制设置为可访问。
     * 如向上转型到 Object 仍无法找到，返回 null。
     * 只匹配方法名。
     * 用于方法需要被多次调用的情况。先使用本方法先取得 Method，然后调用 Method.invoke(Object obj, Object... parameterValues)
     * 
     * @param targetType
     *     目标对象
     * @param name
     *     方法名
     * @return 方法
     */
    public static Method getAccessibleMethodByName(final Class<?> targetType, final String name) {
        for (Class<?> searchType = targetType; searchType != Object.class; searchType = searchType.getSuperclass()) {
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
     * 循环遍历，按属性名获取前缀为 set 的方法，并设为可访问。
     */
    public static Method getAccessibleSetterMethod(Class<?> clazz, String propertyName, Class<?> parameterType) {
        String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(propertyName);
        if (parameterType == null) {
            return getAccessibleMethodByName(clazz, setterMethodName);
        }
        return getAccessibleMethod(clazz, setterMethodName, parameterType);
    }

    /**
     * 循环遍历，按属性名获取前缀为 get 或 is 的方法，并设为可访问。
     */
    public static Method getAccessibleGetterMethod(Class<?> clazz, String propertyName) {
        String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(propertyName);
        Method method = getAccessibleMethodByName(clazz, getterMethodName);
        if (method == null) {
            getterMethodName = IS_PREFIX + StringUtils.capitalize(propertyName);
            method = getAccessibleMethodByName(clazz, getterMethodName);
        }
        return method;
    }

    /**
     * 改变 private/protected 的成员为 public，尽量不调用实际改动的语句，避免 JDK 的 SecurityManager 抱怨。
     * 
     * @param field
     *     成员
     */
    public static void makeAccessible(final Field field) {
        if (field.isAccessible()) {
            return;
        }
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
            || Modifier.isFinal(field.getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * 改变 private/protected 的方法为 public，尽量不调用实际改动的语句，避免 JDK 的 SecurityManager 抱怨。
     * 
     * @param method
     *     方法
     */
    public static void makeAccessible(final Method method) {
        if (method.isAccessible()) {
            return;
        }
        if (!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
            method.setAccessible(true);
        }
    }
}
