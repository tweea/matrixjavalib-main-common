/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util;

import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import org.apache.commons.collections4.CollectionUtils;

/**
 * 列表工具。
 */
@ThreadSafe
public final class ListMx {
    /**
     * 阻止实例化。
     */
    private ListMx() {
    }

    /**
     * 获取列表中的第一个元素。
     * 
     * @param list
     *     列表。
     * @return 第一个元素。
     */
    @Nullable
    public static <E> E getFirst(@Nullable List<E> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list.get(0);
    }

    /**
     * 获取列表中的最后一个元素。
     * 
     * @param list
     *     列表。
     * @return 最后一个元素。
     */
    @Nullable
    public static <E> E getLast(@Nullable List<E> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list.get(list.size() - 1);
    }

    /**
     * 获取列表中的一个元素。
     * 
     * @param list
     *     列表。
     * @param index
     *     索引。
     * @return 一个元素。
     */
    @Nullable
    public static <E> E get(@Nullable List<E> list, int index) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        if (index < 0 || index >= list.size()) {
            return null;
        }

        return list.get(index);
    }
}
