/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.data;

import java.util.List;

/**
 * 树型结构数据源。
 * 
 * @param <ID>
 *     数据标识
 * @param <DATA>
 *     数据
 */
public interface TreeSource<ID, DATA> {
    /**
     * 获取根数据标识。
     * 
     * @return 根数据标识
     */
    ID getRootId();

    /**
     * 列出上级节点数据标识的所有子节点数据标识。
     * 
     * @param parentId
     *     上级节点数据标识
     * @return 子节点数据标识
     */
    List<ID> listChildrenId(ID parentId);

    /**
     * 获取指定数据标识对应的数据。
     * 
     * @param id
     *     数据标识
     * @return 数据
     */
    DATA getItem(ID id);
}
