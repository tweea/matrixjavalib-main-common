/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

/**
 * {@link Pattern} 对象池，缓存编译后的正则表达式。
 */
@ThreadSafe
public class PatternPool {
    /**
     * 缓存。
     */
    private final Map<String, Pattern> pool = new ConcurrentHashMap<>();

    /**
     * 获取 {@link Pattern} 对象。
     * 
     * @param regex
     *     正则表达式。
     * @return {@link Pattern} 对象。
     */
    @Nonnull
    public Pattern of(@Nonnull String regex) {
        return pool.computeIfAbsent(regex, Pattern::compile);
    }

    /**
     * 清除缓存。
     */
    public void clear() {
        pool.clear();
    }
}
