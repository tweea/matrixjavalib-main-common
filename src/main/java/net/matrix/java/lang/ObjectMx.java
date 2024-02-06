/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 对象工具。
 */
public final class ObjectMx {
    /**
     * 阻止实例化。
     */
    private ObjectMx() {
    }

    /**
     * 如果对象的值是 <code>null</code>，执行指定的操作。
     * 
     * @param object
     *     对象。
     * @param action
     *     操作。
     */
    public static void ifNull(Object object, Runnable action) {
        if (object == null) {
            action.run();
        }
    }

    /**
     * 如果对象的值不是 <code>null</code>，执行指定的操作。
     * 
     * @param object
     *     对象。
     * @param action
     *     操作。
     */
    public static <T> void ifNotNull(T object, Consumer<? super T> action) {
        if (object != null) {
            action.accept(object);
        }
    }

    /**
     * 如果对象的值是 <code>null</code>，执行指定的操作，否则执行指定的另一操作。
     * 
     * @param object
     *     对象。
     * @param action
     *     操作。
     * @param elseAction
     *     另一操作。
     */
    public static <T> void ifNullOrElse(T object, Runnable action, Consumer<? super T> elseAction) {
        if (object == null) {
            action.run();
        } else {
            elseAction.accept(object);
        }
    }

    /**
     * 如果对象的值是 <code>null</code>，返回真，否则返回指定的断言结果。
     * 
     * @param object
     *     对象。
     * @param predicate
     *     断言。
     */
    public static <T> boolean ifNullOrTest(T object, Predicate<? super T> predicate) {
        if (object == null) {
            return true;
        }

        return predicate.test(object);
    }

    /**
     * 如果对象的值是 <code>null</code>，返回假，否则返回指定的断言结果。
     * 
     * @param object
     *     对象。
     * @param predicate
     *     断言。
     */
    public static <T> boolean ifNotNullAndTest(T object, Predicate<? super T> predicate) {
        if (object == null) {
            return false;
        }

        return predicate.test(object);
    }

    /**
     * 如果对象的值不是 <code>null</code>，返回指定的映射操作结果，否则返回 <code>null</code>。
     * 
     * @param object
     *     对象。
     * @param mapper
     *     映射操作。
     */
    public static <T, U> U ifNotNullMap(T object, Function<? super T, ? extends U> mapper) {
        if (object == null) {
            return null;
        }

        return mapper.apply(object);
    }

    /**
     * 如果对象的值是 <code>null</code>，掷出指定的异常，否则返回对象。
     * 
     * @param object
     *     对象。
     * @param exceptionSupplier
     *     异常。
     */
    public static <T, X extends Throwable> T ifNullThrow(T object, Supplier<? extends X> exceptionSupplier)
        throws X {
        if (object == null) {
            throw exceptionSupplier.get();
        }

        return object;
    }

    /**
     * 如果断言为真，返回对象一，否则返回对象二。
     * 
     * @param predicate
     *     断言。
     * @param object1
     *     对象一。
     * @param object2
     *     对象二。
     */
    public static <T> T ifElse(boolean predicate, T object1, T object2) {
        if (predicate) {
            return object1;
        } else {
            return object2;
        }
    }

    /**
     * 如果断言为真，返回对象一，否则返回对象二。
     * 
     * @param predicate
     *     断言。
     * @param supplier1
     *     对象一。
     * @param supplier2
     *     对象二。
     */
    public static <T> T ifElseGet(boolean predicate, Supplier<? extends T> supplier1, Supplier<? extends T> supplier2) {
        if (predicate) {
            return supplier1.get();
        } else {
            return supplier2.get();
        }
    }
}
