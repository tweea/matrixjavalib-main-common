/*
 * $Id: Reflections.java 829 2013-12-29 05:29:07Z tweea@263.net $
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.lang;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具类。
 * 提供调用 getter/setter 方法，访问私有成员，调用私有方法，获取泛型类型 Class，被 AOP 过的真实类等工具函数。
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
	 * 阻止实例化。
	 */
	private Reflections() {
	}

	/**
	 * 调用 Getter 方法。
	 * 
	 * @param target
	 *            目标对象
	 * @param name
	 *            属性名
	 * @param <T>
	 *            期待的属性值类型
	 * @return 属性值
	 */
	public static <T> T invokeGetter(final Object target, final String name) {
		String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
		return (T) invokeMethod(target, getterMethodName, new Class[] {}, new Object[] {});
	}

	/**
	 * 调用 Setter 方法，仅匹配方法名。
	 * 
	 * @param target
	 *            目标对象
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 */
	public static void invokeSetter(final Object target, final String name, final Object value) {
		String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(name);
		invokeMethodByName(target, setterMethodName, new Object[] {
			value
		});
	}

	/**
	 * 直接读取对象成员值，无视 private/protected 修饰符，不经过 getter 函数。
	 * 
	 * @param target
	 *            目标对象
	 * @param name
	 *            成员名
	 * @param <T>
	 *            期待的成员值类型
	 * @return 成员值
	 */
	public static <T> T getFieldValue(final Object target, final String name) {
		Field field = FieldUtils.getDeclaredField(target.getClass(), name, true);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + name + "] on target [" + target + "]");
		}

		T result = null;
		try {
			result = (T) field.get(target);
		} catch (IllegalAccessException e) {
			throw new ImpossibleException(e);
		}
		return result;
	}

	/**
	 * 直接设置对象成员值，无视 private/protected 修饰符，不经过 setter 函数。
	 * 
	 * @param target
	 *            目标对象
	 * @param name
	 *            成员名
	 * @param value
	 *            成员值
	 */
	public static void setFieldValue(final Object target, final String name, final Object value) {
		Field field = FieldUtils.getDeclaredField(target.getClass(), name, true);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + name + "] on target [" + target + "]");
		}

		try {
			field.set(target, value);
		} catch (IllegalAccessException e) {
			throw new ImpossibleException(e);
		}
	}

	/**
	 * 直接调用对象方法，无视 private/protected 修饰符。
	 * 用于一次性调用的情况，否则应使用 getAccessibleMethod() 函数获得 Method 后反复调用。
	 * 同时匹配方法名 + 参数类型。
	 * 
	 * @param target
	 *            目标对象
	 * @param name
	 *            方法名
	 * @param parameterTypes
	 *            参数类型
	 * @param parameterValues
	 *            参数值
	 * @param <T>
	 *            期待的返回值类型
	 * @return 返回值
	 */
	public static <T> T invokeMethod(final Object target, final String name, final Class<?>[] parameterTypes, final Object[] parameterValues) {
		Method method = getAccessibleMethod(target, name, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + name + "] on target [" + target + "]");
		}

		try {
			return (T) method.invoke(target, parameterValues);
		} catch (IllegalAccessException e) {
			throw new ReflectionRuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new ReflectionRuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new ReflectionRuntimeException(e);
		}
	}

	/**
	 * 直接调用对象方法，无视 private/protected 修饰符。
	 * 用于一次性调用的情况，否则应使用 getAccessibleMethodByName() 函数获得 Method 后反复调用。
	 * 只匹配函数名，如果有多个同名函数调用第一个。
	 * 
	 * @param target
	 *            目标对象
	 * @param name
	 *            方法名
	 * @param parameterValues
	 *            参数值
	 * @param <T>
	 *            期待的返回值类型
	 * @return 返回值
	 */
	public static <T> T invokeMethodByName(final Object target, final String name, final Object[] parameterValues) {
		Method method = getAccessibleMethodByName(target, name);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + name + "] on target [" + target + "]");
		}

		try {
			return (T) method.invoke(target, parameterValues);
		} catch (IllegalAccessException e) {
			throw new ReflectionRuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new ReflectionRuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new ReflectionRuntimeException(e);
		}
	}

	/**
	 * 循环向上转型，获取对象的 DeclaredField，并强制设置为可访问。
	 * 如向上转型到 Object 仍无法找到，返回 null。
	 * 
	 * @param target
	 *            目标对象
	 * @param name
	 *            成员名
	 * @return 成员
	 */
	public static Field getAccessibleField(final Object target, final String name) {
		for (Class<?> superClass = target.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(name);
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
	 * 匹配函数名 + 参数类型。
	 * 用于方法需要被多次调用的情况。先使用本函数先取得 Method，然后调用 Method.invoke(Object obj, Object... args)
	 * 
	 * @param target
	 *            目标对象
	 * @param name
	 *            方法名
	 * @param parameterTypes
	 *            参数类型
	 * @return 方法
	 */
	public static Method getAccessibleMethod(final Object target, final String name, final Class<?>... parameterTypes) {
		for (Class<?> searchType = target.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
			try {
				Method method = searchType.getDeclaredMethod(name, parameterTypes);
				makeAccessible(method);
				return method;
			} catch (NoSuchMethodException e) {
				// Method不在当前类定义,继续向上转型
				LOG.trace("", e);
			}
		}
		return null;
	}

	/**
	 * 循环向上转型，获取对象的 DeclaredMethod，并强制设置为可访问。
	 * 如向上转型到 Object 仍无法找到，返回 null。
	 * 只匹配函数名。
	 * 用于方法需要被多次调用的情况。先使用本函数先取得 Method，然后调用 Method.invoke(Object obj, Object... args)
	 * 
	 * @param target
	 *            目标对象
	 * @param name
	 *            方法名
	 * @return 方法
	 */
	public static Method getAccessibleMethodByName(final Object target, final String name) {
		for (Class<?> searchType = target.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
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
	 * 改变 private/protected 的方法为 public，尽量不调用实际改动的语句，避免 JDK 的 securityManager 抱怨。
	 * 
	 * @param method
	 *            方法
	 */
	public static void makeAccessible(final Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
			method.setAccessible(true);
		}
	}

	/**
	 * 改变 private/protected 的成员为 public，尽量不调用实际改动的语句，避免 JDK 的 securityManager 抱怨。
	 * 
	 * @param field
	 *            成员
	 */
	public static void makeAccessible(final Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers()))
			&& !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 * 通过反射，获得 Class 定义中声明的泛型参数的类型，注意泛型必须定义在父类处。
	 * 如无法找到，返回 Object.class。
	 * eg.
	 * public UserDao extends HibernateDao<User>
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @param <T>
	 *            自动转型
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	public static <T> Class<T> getClassGenricType(final Class clazz) {
		return getClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射，获得 Class 定义中声明的父类的泛型参数的类型。
	 * 如无法找到，返回 Object.class。
	 * 如：
	 * public UserDao extends HibernateDao<User, Long>
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	public static Class getClassGenricType(final Class clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			LOG.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			LOG.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			LOG.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}
}
