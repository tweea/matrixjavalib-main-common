/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

/**
 * 集合工具。
 */
@ThreadSafe
public final class CollectionMx {
    /**
     * 阻止实例化。
     */
    private CollectionMx() {
    }

    /**
     * 从对象集合构造 {@link List} 形式集合。
     * 
     * @param items
     *     对象集合。
     * @param valueFunction
     *     值映射函数。
     * @return 集合。
     */
    @Nonnull
    public static <V, I> List<V> buildList(@Nonnull Iterable<I> items, @Nonnull Function<? super I, ? extends V> valueFunction) {
        List<V> list = new ArrayList<>();

        for (I item : items) {
            list.add(valueFunction.apply(item));
        }

        return list;
    }

    /**
     * 从对象集合构造 {@link Set} 形式集合。
     * 
     * @param items
     *     对象集合。
     * @param valueFunction
     *     值映射函数。
     * @return 集合。
     */
    @Nonnull
    public static <V, I> Set<V> buildSet(@Nonnull Iterable<I> items, @Nonnull Function<? super I, ? extends V> valueFunction) {
        Set<V> set = new HashSet<>();

        for (I item : items) {
            set.add(valueFunction.apply(item));
        }

        return set;
    }

    /**
     * 从对象集合构造 {@link Map} 形式映射关系，一个键对应一个值。
     * 
     * @param items
     *     对象集合。
     * @param keyFunction
     *     键映射函数。
     * @return 映射关系。
     */
    @Nonnull
    public static <K, I> Map<K, I> buildMap(@Nonnull Iterable<I> items, @Nonnull Function<? super I, ? extends K> keyFunction) {
        Map<K, I> map = new HashMap<>();

        for (I item : items) {
            map.put(keyFunction.apply(item), item);
        }

        return map;
    }

    /**
     * 从对象集合构造 {@link Map} 形式映射关系，一个键对应一个值。
     * 
     * @param items
     *     对象集合。
     * @param keyFunction
     *     键映射函数。
     * @param valueFunction
     *     值映射函数。
     * @return 映射关系。
     */
    @Nonnull
    public static <K, V, I> Map<K, V> buildMap(@Nonnull Iterable<I> items, @Nonnull Function<? super I, ? extends K> keyFunction,
        @Nonnull Function<? super I, ? extends V> valueFunction) {
        Map<K, V> map = new HashMap<>();

        for (I item : items) {
            map.put(keyFunction.apply(item), valueFunction.apply(item));
        }

        return map;
    }

    /**
     * 从对象集合构造 {@link Map} 形式映射关系，一个键对应多个值。
     * 
     * @param items
     *     对象集合。
     * @param keyFunction
     *     键映射函数。
     * @return 映射关系。
     */
    @Nonnull
    public static <K, I> Map<K, List<I>> buildListMap(@Nonnull Iterable<I> items, @Nonnull Function<? super I, ? extends K> keyFunction) {
        Map<K, List<I>> map = new HashMap<>();

        for (I item : items) {
            map.computeIfAbsent(keyFunction.apply(item), key -> new ArrayList<>()).add(item);
        }

        return map;
    }

    /**
     * 从对象集合构造 {@link Map} 形式映射关系，一个键对应多个值。
     * 
     * @param items
     *     对象集合。
     * @param keyFunction
     *     键映射函数。
     * @param valueFunction
     *     值映射函数。
     * @return 映射关系。
     */
    @Nonnull
    public static <K, V, I> Map<K, List<V>> buildListMap(@Nonnull Iterable<I> items, @Nonnull Function<? super I, ? extends K> keyFunction,
        @Nonnull Function<? super I, ? extends V> valueFunction) {
        Map<K, List<V>> map = new HashMap<>();

        for (I item : items) {
            map.computeIfAbsent(keyFunction.apply(item), key -> new ArrayList<>()).add(valueFunction.apply(item));
        }

        return map;
    }

    /**
     * 从对象集合构造 {@link Map} 形式映射关系，一个键对应多个值。
     * 
     * @param items
     *     对象集合。
     * @param keyFunction
     *     键映射函数。
     * @return 映射关系。
     */
    @Nonnull
    public static <K, I> Map<K, Set<I>> buildSetMap(@Nonnull Iterable<I> items, @Nonnull Function<? super I, ? extends K> keyFunction) {
        Map<K, Set<I>> map = new HashMap<>();

        for (I item : items) {
            map.computeIfAbsent(keyFunction.apply(item), key -> new HashSet<>()).add(item);
        }

        return map;
    }

    /**
     * 从对象集合构造 {@link Map} 形式映射关系，一个键对应多个值。
     * 
     * @param items
     *     对象集合。
     * @param keyFunction
     *     键映射函数。
     * @param valueFunction
     *     值映射函数。
     * @return 映射关系。
     */
    @Nonnull
    public static <K, V, I> Map<K, Set<V>> buildSetMap(@Nonnull Iterable<I> items, @Nonnull Function<? super I, ? extends K> keyFunction,
        @Nonnull Function<? super I, ? extends V> valueFunction) {
        Map<K, Set<V>> map = new HashMap<>();

        for (I item : items) {
            map.computeIfAbsent(keyFunction.apply(item), key -> new HashSet<>()).add(valueFunction.apply(item));
        }

        return map;
    }
}
