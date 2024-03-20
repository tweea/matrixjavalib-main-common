/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 枚举工具。
 */
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
    public static <K, E extends Enum<E>> Map<K, E> buildValueMap(Class<E> enumClass, Function<? super E, ? extends K> keyFunction) {
        Map<K, E> map = new HashMap<>();

        for (E value : enumClass.getEnumConstants()) {
            map.put(keyFunction.apply(value), value);
        }

        return map;
    }
}
