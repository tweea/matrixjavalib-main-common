/*
 * 版权所有 2013 Matrix。
 * 保留所有权利。
 */
package net.matrix.text;

import java.util.Locale;

/**
 * 区域工具。
 */
public final class Locales {
    /**
     * 与当前线程关联的区域。
     */
    private static ThreadLocal<Locale> currentHolder = ThreadLocal.withInitial(() -> Locale.getDefault());

    /**
     * 阻止实例化。
     */
    private Locales() {
    }

    /**
     * 获取与当前线程关联的区域。
     */
    public static Locale current() {
        return currentHolder.get();
    }

    /**
     * 设置与当前线程关联的区域。
     */
    public static void current(final Locale current) {
        currentHolder.set(current);
    }
}
