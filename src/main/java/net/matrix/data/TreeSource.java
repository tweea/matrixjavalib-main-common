/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.data;

import java.util.List;

/**
 * 产生树型结构上各个节点的接口。
 * 
 * @param <ID>
 *            数据标识
 * @param <DATA>
 *            数据
 */
public interface TreeSource<ID, DATA> {
	/**
	 * 获取根数据标识。
	 * 
	 * @return 根数据标识
	 */
	ID getRootId();

	/**
	 * 列出父节点数据标识下属的所有子节点数据标识。
	 * 
	 * @param parentId
	 *            父节点数据标识
	 * @return 子节点数据标识
	 */
	List<ID> listChildrenId(ID parentId);

	/**
	 * 构造特定数据标识对应的数据。
	 * 
	 * @param id
	 *            数据标识
	 * @return 数据
	 */
	DATA getItem(ID id);
}
