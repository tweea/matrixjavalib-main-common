/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.data;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 树型结构中的节点。
 */
public interface TreeNode<DATA> {
    /**
     * 获取节点承载的数据。
     * 
     * @return 数据。
     */
    @Nonnull
    DATA getData();

    /**
     * 设置节点承载的数据。
     * 
     * @param data
     *     数据。
     */
    void setData(@Nonnull DATA data);

    /**
     * 获取上级节点。
     * 
     * @return 上级节点。
     */
    @Nullable
    TreeNode<DATA> getParent();

    /**
     * 设置上级节点。
     * 
     * @param parent
     *     上级节点。
     */
    void setParent(@Nullable TreeNode<DATA> parent);

    /**
     * 是否有上级节点。
     */
    default boolean hasParent() {
        return getParent() != null;
    }

    /**
     * 获取下级节点列表。
     * 
     * @return 下级节点列表。
     */
    @Nonnull
    List<TreeNode<DATA>> getChildren();

    /**
     * 是否有下级节点。
     */
    default boolean hasChild() {
        return !getChildren().isEmpty();
    }
}
