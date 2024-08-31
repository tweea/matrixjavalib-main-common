/*
 * 版权所有 2023 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * 区域工具。
 */
@ThreadSafe
public final class LocaleMx {
    /**
     * 线程相关的区域。
     */
    private static final ThreadLocal<Locale> CURRENT_HOLDER = ThreadLocal.withInitial(Locale::getDefault);

    /**
     * 线程相关的用于显示的区域。
     */
    private static final ThreadLocal<Locale> CURRENT_DISPLAY_HOLDER = ThreadLocal.withInitial(() -> Locale.getDefault(Locale.Category.DISPLAY));

    /**
     * 线程相关的用于格式化的区域。
     */
    private static final ThreadLocal<Locale> CURRENT_FORMAT_HOLDER = ThreadLocal.withInitial(() -> Locale.getDefault(Locale.Category.FORMAT));

    /**
     * 阻止实例化。
     */
    private LocaleMx() {
    }

    /**
     * 获取线程相关的区域。
     * 初始为系统默认区域。
     */
    @Nonnull
    public static Locale current() {
        return CURRENT_HOLDER.get();
    }

    /**
     * 获取线程相关的特定功能分类的区域。
     * 初始为系统默认区域。
     *
     * @param category
     *     区域的功能分类。
     */
    @Nonnull
    public static Locale current(@Nonnull Locale.Category category) {
        return switch (category) {
        case DISPLAY -> CURRENT_DISPLAY_HOLDER.get();
        case FORMAT -> CURRENT_FORMAT_HOLDER.get();
        default -> throw new UnsupportedOperationException();
        };
    }

    /**
     * 设置线程相关的区域。
     * 同时改变所有功能分类的区域。
     * 参数为 <code>null</code> 时重置为系统默认区域。
     */
    public static void current(@Nullable Locale locale) {
        if (locale == null) {
            CURRENT_HOLDER.remove();
            CURRENT_DISPLAY_HOLDER.remove();
            CURRENT_FORMAT_HOLDER.remove();
        } else {
            CURRENT_HOLDER.set(locale);
            CURRENT_DISPLAY_HOLDER.set(locale);
            CURRENT_FORMAT_HOLDER.set(locale);
        }
    }

    /**
     * 设置线程相关的特定功能分类的区域。
     * 参数为 <code>null</code> 时重置为系统默认区域。
     *
     * @param category
     *     区域的功能分类。
     */
    public static void current(@Nonnull Locale.Category category, @Nullable Locale locale) {
        switch (category) {
        case DISPLAY:
            if (locale == null) {
                CURRENT_DISPLAY_HOLDER.remove();
            } else {
                CURRENT_DISPLAY_HOLDER.set(locale);
            }
            break;
        case FORMAT:
            if (locale == null) {
                CURRENT_FORMAT_HOLDER.remove();
            } else {
                CURRENT_FORMAT_HOLDER.set(locale);
            }
            break;
        default:
            throw new UnsupportedOperationException();
        }
    }
}
