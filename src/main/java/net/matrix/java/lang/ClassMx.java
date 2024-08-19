/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * 类工具。
 */
@ThreadSafe
public final class ClassMx {
    /**
     * 阻止实例化。
     */
    private ClassMx() {
    }

    /**
     * 获取类中声明的父类泛型参数数量。
     * 如无法找到，返回 0。
     * 如：
     *
     * <pre>
     * public UserDao extends HibernateDao&lt;User&gt;
     *
     * getParameterizedTypeNumber(UserDao.class) == 1
     * </pre>
     *
     * @param clazz
     *     类。
     * @return 父类泛型参数数量。
     */
    public static int getParameterizedTypeNumber(@Nonnull Class clazz) {
        Type superclassType = clazz.getGenericSuperclass();
        if (!(superclassType instanceof ParameterizedType)) {
            return 0;
        }

        Type[] parameterizedTypes = ((ParameterizedType) superclassType).getActualTypeArguments();
        return parameterizedTypes.length;
    }

    /**
     * 获取类中声明的父类泛型参数类型。
     * 如无法找到，返回 <code>null</code>。
     * 如无法判断，返回 Object.class。
     * 如：
     *
     * <pre>
     * public UserDao extends HibernateDao&lt;User, Long&gt;
     *
     * getParameterizedType(UserDao.class, 0) == User.class
     * getParameterizedType(UserDao.class, 1) == Long.class
     * </pre>
     *
     * @param clazz
     *     类。
     * @param index
     *     父类泛型参数索引，从 0 开始。
     * @return 父类泛型参数类型。
     */
    @Nullable
    public static Class getParameterizedType(@Nonnull Class clazz, int index) {
        Type superclassType = clazz.getGenericSuperclass();
        if (!(superclassType instanceof ParameterizedType)) {
            return null;
        }

        Type[] parameterizedTypes = ((ParameterizedType) superclassType).getActualTypeArguments();
        if (index < 0 || index >= parameterizedTypes.length) {
            return null;
        }

        Type parameterizedType = parameterizedTypes[index];
        if (!(parameterizedType instanceof Class)) {
            return Object.class;
        }

        return (Class) parameterizedType;
    }
}
