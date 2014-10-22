/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.data;

import java.io.Serializable;
import java.util.SortedMap;

/**
 * 树型结构。
 * 
 * @param <ID>
 *            数据标识
 * @param <DATA>
 *            数据
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
	 *            数据标识
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
	 *            数据
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
	 *            数据标识
	 * @return 节点标识
	 */
	Key findKey(ID id);

	/**
	 * 获得上级节点。
	 * 
	 * @return 上级节点
	 */
	Tree<ID, DATA> getParent();

	/**
	 * 获得所有节点。
	 * 
	 * @return 所有节点
	 */
	SortedMap<Key, ? extends Tree<ID, DATA>> getAllNodes();

	/**
	 * 获得所有子节点。
	 * 
	 * @return 所有子节点
	 */
	SortedMap<Key, ? extends Tree<ID, DATA>> getChildNodes();

	/**
	 * 获得节点。
	 * 
	 * @param key
	 *            节点标识
	 * @return 节点
	 */
	Tree<ID, DATA> getNode(Key key);

	/**
	 * 获得节点。
	 * 
	 * @param id
	 *            数据标识
	 * @return 节点
	 */
	Tree<ID, DATA> getNode(ID id);

	/**
	 * 获得子节点。
	 * 
	 * @param key
	 *            节点标识
	 * @return 子节点
	 */
	Tree<ID, DATA> getChildNode(Key key);

	/**
	 * 获得子节点。
	 * 
	 * @param id
	 *            数据标识
	 * @return 子节点
	 */
	Tree<ID, DATA> getChildNode(ID id);

	/**
	 * 增加新的子节点。
	 * 
	 * @param id
	 *            数据标识
	 * @param data
	 *            数据
	 * @return 子节点
	 */
	Tree<ID, DATA> appendChildNode(ID id, DATA data);

	/**
	 * 移除子节点。
	 * 
	 * @param key
	 *            节点标识
	 */
	void removeChildNode(Key key);

	/**
	 * 移除子节点。
	 * 
	 * @param id
	 *            数据标识
	 */
	void removeChildNode(ID id);

	/**
	 * 是否根节点。
	 * 
	 * @return true 是根节点
	 */
	boolean isRoot();

	/**
	 * 是否叶节点。
	 * 
	 * @return true 是叶节点
	 */
	boolean isLeaf();

	/**
	 * 标识节点在树中的位置。
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
		 * 获取同级节点中的索引。
		 * 
		 * @return 索引
		 */
		int getIndex();

		/**
		 * 获取节点所在级别。
		 * 
		 * @return 级别
		 */
		int getLevel();
	}
}
