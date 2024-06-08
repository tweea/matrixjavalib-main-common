/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.data;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 具有树型结构的数据。
 */
public interface TreeData<ID, DATA extends TreeData<ID, DATA>> {
    /**
     * 获取数据的唯一标识。
     * 
     * @return 唯一标识。
     */
    @Nonnull
    ID getId();

    /**
     * 获取上级数据的唯一标识。
     * 
     * @return 唯一标识。
     */
    @Nullable
    ID getParentId();

    /**
     * 是否有上级数据。
     */
    default boolean hasParent() {
        return getParentId() != null;
    }

    /**
     * 获取下级数据列表。
     * 
     * @return 下级数据列表。
     */
    @Nonnull
    List<DATA> getChildren();

    /**
     * 是否有下级数据。
     */
    default boolean hasChild() {
        return !getChildren().isEmpty();
    }
}
