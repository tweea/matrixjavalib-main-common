/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.lang;

/**
 * 对象工具。
 */
public final class Objects2 {
    /**
     * 阻止实例化。
     */
    private Objects2() {
    }

    /**
     * 如果对象的值是 null 返回替代对象，否则返回对象。
     * 
     * @param value
     *     对象
     * @param replacement
     *     替代对象
     */
    public static <T> T isNull(final T value, final T replacement) {
        if (value == null) {
            return replacement;
        }
        return value;
    }

    /**
     * 如果两个对象的值中有 null 或相同，返回 null，否则返回对象一。
     * 
     * @param value1
     *     对象一
     * @param value2
     *     对象二
     */
    public static <T> T nullIf(final T value1, final T value2) {
        if (value1 == null || value2 == null) {
            return null;
        }
        if (value1.equals(value2)) {
            return null;
        }
        return value1;
    }
}
