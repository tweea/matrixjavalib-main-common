/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 使用 {@link ThreadLocal} 存储的 {@link Map}，一般作为静态变量使用。
 */
public class ThreadLocalMap<K, V>
    implements Map<K, V> {
    private final ThreadLocal<Map<K, V>> internal = ThreadLocal.withInitial(HashMap::new);

    @Override
    public int size() {
        return internal.get().size();
    }

    @Override
    public boolean isEmpty() {
        return internal.get().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return internal.get().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return internal.get().containsValue(value);
    }

    @Override
    public V get(Object key) {
        return internal.get().get(key);
    }

    @Override
    public V put(K key, V value) {
        return internal.get().put(key, value);
    }

    @Override
    public V remove(Object key) {
        return internal.get().remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        internal.get().putAll(m);
    }

    @Override
    public void clear() {
        internal.get().clear();
    }

    @Override
    public Set<K> keySet() {
        return internal.get().keySet();
    }

    @Override
    public Collection<V> values() {
        return internal.get().values();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return internal.get().entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return internal.get().equals(o);
    }

    @Override
    public int hashCode() {
        return internal.get().hashCode();
    }
}
