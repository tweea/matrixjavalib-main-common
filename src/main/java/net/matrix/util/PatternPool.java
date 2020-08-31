/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * {@link Pattern} 对象池。
 */
public class PatternPool {
    private final Map<String, Pattern> pool = new HashMap<>();

    public Pattern forPattern(String patternString) {
        return pool.computeIfAbsent(patternString, Pattern::compile);
    }

    public void clear() {
        pool.clear();
    }
}
