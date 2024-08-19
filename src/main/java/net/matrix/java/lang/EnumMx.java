/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.google.common.collect.Maps;

/**
 * 枚举工具。
 */
@ThreadSafe
public final class EnumMx {
    /**
     * 阻止实例化。
     */
    private EnumMx() {
    }

    /**
     * 从枚举值集合构造 {@link Map} 形式映射关系，一个键对应一个值。
     *
     * @param enumClass
     *     枚举类型。
     * @param keyFunction
     *     键映射函数。
     * @return 映射关系。
     */
    @Nonnull
    public static <K, E extends Enum<E>> Map<K, E> buildValueMap(@Nonnull Class<E> enumClass, @Nonnull Function<? super E, ? extends K> keyFunction) {
        E[] values = enumClass.getEnumConstants();

        Map<K, E> map = Maps.newHashMapWithExpectedSize(values.length);
        for (E value : values) {
            map.put(keyFunction.apply(value), value);
        }
        return map;
    }

    /**
     * 判断枚举值是否与任意参数枚举值相等。
     *
     * @param value
     *     枚举值。
     * @param searchValues
     *     参数枚举值列表。
     * @return 是否相等。
     */
    public static <E extends Enum<E>> boolean equalsAny(@Nullable E value, @Nonnull E... searchValues) {
        for (E searchValue : searchValues) {
            if (value == searchValue) {
                return true;
            }
        }
        return false;
    }
}
