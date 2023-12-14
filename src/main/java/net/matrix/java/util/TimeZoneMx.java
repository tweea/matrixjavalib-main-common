/*
 * 版权所有 2023 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util;

import java.util.TimeZone;

/**
 * 时区工具。
 */
public final class TimeZoneMx {
    /**
     * 线程相关的时区。
     */
    private static final ThreadLocal<TimeZone> CURRENT_HOLDER = ThreadLocal.withInitial(TimeZone::getDefault);

    /**
     * 阻止实例化。
     */
    private TimeZoneMx() {
    }

    /**
     * 获取线程相关的时区。
     * 初始为系统默认时区。
     */
    public static TimeZone current() {
        return CURRENT_HOLDER.get();
    }

    /**
     * 设置线程相关的时区。
     * 参数为 <code>null</code> 时重置为系统默认时区。
     */
    public static void current(TimeZone timeZone) {
        if (timeZone == null) {
            CURRENT_HOLDER.remove();
        } else {
            CURRENT_HOLDER.set(timeZone);
        }
    }
}
