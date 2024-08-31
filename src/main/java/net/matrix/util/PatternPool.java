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

import org.apache.commons.collections4.keyvalue.MultiKey;

/**
 * {@link Pattern} 对象池，缓存编译后的正则表达式。
 */
@ThreadSafe
public class PatternPool {
    /**
     * 缓存。
     */
    private final Map<MultiKey, Pattern> pool = new ConcurrentHashMap<>();

    /**
     * 获取 {@link Pattern} 对象。
     *
     * @param regex
     *     正则表达式。
     * @return {@link Pattern} 对象。
     */
    @Nonnull
    public Pattern of(@Nonnull String regex) {
        return of(regex, 0);
    }

    /**
     * 获取 {@link Pattern} 对象。
     *
     * @param regex
     *     正则表达式。
     * @param flags
     *     匹配模式。
     * @return {@link Pattern} 对象。
     * @see Pattern#compile(String, int)
     */
    @Nonnull
    public Pattern of(@Nonnull String regex, int flags) {
        return pool.computeIfAbsent(new MultiKey(regex, flags), key -> Pattern.compile(regex, flags));
    }

    /**
     * 清除缓存。
     */
    public void clear() {
        pool.clear();
    }
}
