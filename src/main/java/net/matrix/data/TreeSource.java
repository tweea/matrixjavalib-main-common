/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.data;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * 具有树型结构的数据源。
 */
public interface TreeSource<DATA> {
    /**
     * 获取顶级数据列表。
     * 
     * @return 顶级数据列表。
     */
    @Nonnull
    List<DATA> getRoots();

    /**
     * 获取下级数据列表。
     * 
     * @param data
     *     数据。
     * @return 下级数据列表。
     */
    @Nonnull
    List<DATA> getChildren(@Nonnull DATA data);
}
