/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
 */
package net.matrix.data;

import java.io.Serializable;
import java.util.SortedMap;

/**
 * 树型结构。
 * 
 * @param <ID>
 *     数据标识
 * @param <DATA>
 *     数据
 */
public interface Tree<ID, DATA> {
    /**
     * 获取节点标识。
     * 
     * @return 节点标识
     */
    Key getKey();

    /**
     * 更改数据标识。
     * 
     * @param id
     *     数据标识
     */
    void setId(ID id);

    /**
     * 获取数据标识。
     * 
     * @return 数据标识
     */
    ID getId();

    /**
     * 更改数据。
     * 
     * @param data
     *     数据
     */
    void setData(DATA data);

    /**
     * 获取数据。
     * 
     * @return 数据
     */
    DATA getData();

    /**
     * 通过数据标识查找节点标识。
     * 
     * @param id
     *     数据标识
     * @return 节点标识
     */
    Key findKey(ID id);

    /**
     * 获取上级节点。
     * 
     * @return 上级节点
     */
    Tree<ID, DATA> getParent();

    /**
     * 获取所有节点。
     * 
     * @return 所有节点
     */
    SortedMap<Key, ? extends Tree<ID, DATA>> getAllNodes();

    /**
     * 获取所有子节点。
     * 
     * @return 所有子节点
     */
    SortedMap<Key, ? extends Tree<ID, DATA>> getChildNodes();

    /**
     * 获取节点。
     * 
     * @param key
     *     节点标识
     * @return 节点
     */
    Tree<ID, DATA> getNode(Key key);

    /**
     * 获取节点。
     * 
     * @param id
     *     数据标识
     * @return 节点
     */
    Tree<ID, DATA> getNode(ID id);

    /**
     * 获取子节点。
     * 
     * @param key
     *     节点标识
     * @return 子节点
     */
    Tree<ID, DATA> getChildNode(Key key);

    /**
     * 获取子节点。
     * 
     * @param id
     *     数据标识
     * @return 子节点
     */
    Tree<ID, DATA> getChildNode(ID id);

    /**
     * 增加子节点。
     * 
     * @param id
     *     数据标识
     * @param data
     *     数据
     * @return 子节点
     */
    Tree<ID, DATA> appendChildNode(ID id, DATA data);

    /**
     * 移除子节点。
     * 
     * @param key
     *     节点标识
     */
    void removeChildNode(Key key);

    /**
     * 移除子节点。
     * 
     * @param id
     *     数据标识
     */
    void removeChildNode(ID id);

    /**
     * 是否根节点。
     */
    boolean isRoot();

    /**
     * 是否叶节点。
     */
    boolean isLeaf();

    /**
     * 节点标识，表示节点在树型结构中的位置。
     */
    interface Key
        extends Comparable<Key>, Serializable {
        /**
         * 获取上级节点标识。
         * 
         * @return 上级节点标识
         */
        Key getParent();

        /**
         * 获取节点在上级节点中的索引。
         * 
         * @return 索引
         */
        int getIndex();

        /**
         * 获取节点在树型结构中的级别。
         * 
         * @return 级别
         */
        int getLevel();
    }
}
