/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 树型结构的默认实现。
 * 
 * @param <ID>
 *            数据标识
 * @param <DATA>
 *            数据
 */
public class DefaultTree<ID, DATA>
	implements Serializable, Tree<ID, DATA> {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -21200598521077549L;

	/**
	 * 节点标识。
	 */
	private final Key key;

	/**
	 * 数据标识。
	 */
	private transient ID id;

	/**
	 * 数据。
	 */
	private transient DATA data;

	/**
	 * 父节点。
	 */
	private final DefaultTree<ID, DATA> parent;

	/**
	 * 标识映射。
	 */
	private final transient Map<ID, Key> keyMap;

	/**
	 * 存储所有的节点。
	 */
	private final transient SortedMap<Key, DefaultTree<ID, DATA>> nodes;

	/**
	 * 构造一棵树。
	 * 
	 * @param id
	 *            数据标识
	 * @param data
	 *            数据对象
	 */
	public DefaultTree(final ID id, final DATA data) {
		this.key = new DefaultKey();
		this.id = id;
		this.data = data;
		this.parent = null;

		this.keyMap = Collections.synchronizedMap(new HashMap<ID, Key>());
		this.nodes = Collections.synchronizedSortedMap(new TreeMap<Key, DefaultTree<ID, DATA>>());

		keyMap.put(id, key);
		nodes.put(key, this);
	}

	/**
	 * 构造一个树节点。
	 * 
	 * @param parent
	 *            父节点
	 * @param id
	 *            数据标识
	 * @param data
	 *            数据对象
	 */
	public DefaultTree(final DefaultTree<ID, DATA> parent, final ID id, final DATA data) {
		this.key = new DefaultKey(parent.key, parent.getChildNodes().size());
		this.id = id;
		this.data = data;
		this.parent = parent;

		this.keyMap = parent.keyMap;
		this.nodes = parent.nodes;

		keyMap.put(id, key);
		nodes.put(key, this);
	}

	@Override
	public Key getKey() {
		return key;
	}

	@Override
	public void setId(final ID id) {
		keyMap.remove(this.id);
		this.id = id;
		keyMap.put(id, key);
	}

	@Override
	public ID getId() {
		return id;
	}

	@Override
	public void setData(final DATA data) {
		this.data = data;
	}

	@Override
	public DATA getData() {
		return data;
	}

	@Override
	public Key findKey(final ID nodeId) {
		return keyMap.get(nodeId);
	}

	@Override
	public DefaultTree<ID, DATA> getParent() {
		return parent;
	}

	@Override
	public SortedMap<Key, DefaultTree<ID, DATA>> getAllNodes() {
		return nodes;
	}

	@Override
	public SortedMap<Key, DefaultTree<ID, DATA>> getChildNodes() {
		return nodes.subMap(new DefaultKey(key, 0), new DefaultKey(key, Integer.MAX_VALUE));
	}

	@Override
	public DefaultTree<ID, DATA> getNode(final Key nodeKey) {
		return nodes.get(nodeKey);
	}

	@Override
	public DefaultTree<ID, DATA> getNode(final ID nodeId) {
		Key nodeKey = findKey(nodeId);
		if (nodeKey == null) {
			return null;
		}
		return getNode(nodeKey);
	}

	@Override
	public DefaultTree<ID, DATA> getChildNode(final Key nodeKey) {
		return getChildNodes().get(nodeKey);
	}

	@Override
	public DefaultTree<ID, DATA> getChildNode(final ID nodeId) {
		Key nodeKey = findKey(nodeId);
		if (nodeKey == null) {
			return null;
		}
		return getChildNode(nodeKey);
	}

	@Override
	public DefaultTree<ID, DATA> appendChildNode(final ID nodeId, final DATA nodeData) {
		return new DefaultTree<>(this, nodeId, nodeData);
	}

	@Override
	public void removeChildNode(final Key nodeKey) {
		DefaultTree<ID, DATA> node = getChildNode(nodeKey);
		if (node == null) {
			return;
		}
		for (Key childKey : new HashSet<>(node.getChildNodes().keySet())) {
			node.removeChildNode(childKey);
		}
		keyMap.remove(node.id);
		nodes.remove(nodeKey);
	}

	@Override
	public void removeChildNode(final ID nodeId) {
		Key nodeKey = findKey(nodeId);
		if (nodeKey == null) {
			return;
		}
		removeChildNode(nodeKey);
	}

	@Override
	public boolean isRoot() {
		return parent == null;
	}

	@Override
	public boolean isLeaf() {
		return getChildNodes().isEmpty();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("[id=").append(id);
		sb.append(",key=").append(key);
		sb.append(",parent=");
		if (parent == null) {
			sb.append("null");
		} else {
			sb.append(parent.getKey());
		}
		sb.append(",data=").append(data).append(",subnodes=").append(getChildNodes().values()).append(']');
		return sb.toString();
	}

	/**
	 * 标识节点在树中的位置。
	 */
	public static class DefaultKey
		implements Key {
		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = 35507229935965284L;

		/**
		 * 父节点标识。
		 */
		private final Key parent;

		/**
		 * 级别。
		 */
		private final int level;

		/**
		 * 索引。
		 */
		private final int index;

		/**
		 * 缓存 hashCode() 结果。
		 */
		private int hash;

		/**
		 * 缓存 toString() 结果。
		 */
		private String string;

		/**
		 * 构造新顶级节点实例。
		 */
		public DefaultKey() {
			this(0);
		}

		/**
		 * 使用节点索引构造新顶级节点实例。
		 * 
		 * @param index
		 *            节点索引
		 */
		private DefaultKey(final int index) {
			this.parent = null;
			this.level = 0;
			this.index = index;
		}

		/**
		 * 使用父节点标识和节点索引构造新实例。
		 * 
		 * @param parent
		 *            父节点标识
		 * @param index
		 *            节点索引
		 */
		public DefaultKey(final Key parent, final int index) {
			this.parent = parent;
			this.level = parent.getLevel() + 1;
			this.index = index;
		}

		@Override
		public Key getParent() {
			return parent;
		}

		@Override
		public int getIndex() {
			return index;
		}

		@Override
		public int getLevel() {
			return level;
		}

		@Override
		public int compareTo(final Key o) {
			if (level < o.getLevel()) {
				return -1;
			} else if (level > o.getLevel()) {
				return 1;
			}
			if (level != 0) {
				int p = parent.compareTo(o.getParent());
				if (p != 0) {
					return p;
				}
			}
			if (index < o.getIndex()) {
				return -1;
			} else if (index > o.getIndex()) {
				return 1;
			}
			return 0;
		}

		@Override
		public boolean equals(final Object obj) {
			if (obj instanceof Key) {
				Key ok = (Key) obj;
				if (level != ok.getLevel()) {
					return false;
				}
				if (level != 0 && !parent.equals(ok.getParent())) {
					return false;
				}
				return index == ok.getIndex();
			}
			return false;
		}

		@Override
		public int hashCode() {
			if (hash == 0) {
				if (parent == null) {
					hash = 31 + index;
				} else {
					hash = parent.hashCode() * 31 + index;
				}
			}
			return hash;
		}

		@Override
		public String toString() {
			if (string == null) {
				if (parent == null) {
					string = Integer.toString(index);
				} else {
					string = parent.toString() + "," + index;
				}
			}
			return string;
		}
	}

	/**
	 * 从 TreeSource 生成树状结构。
	 * 
	 * @param <ID>
	 *            数据标识
	 * @param <DATA>
	 *            数据
	 * @param source
	 *            节点构造源
	 * @return 新构造的树
	 */
	public static <ID, DATA> DefaultTree<ID, DATA> generate(final TreeSource<ID, DATA> source) {
		ID rootId = source.getRootId();
		DATA rootData = source.getItem(rootId);
		DefaultTree<ID, DATA> tree = new DefaultTree<>(rootId, rootData);
		generateSubNode(source, tree);
		return tree;
	}

	/**
	 * 向 TreeSource 增加新的节点。
	 * 
	 * @param <ID>
	 *            数据标识
	 * @param <DATA>
	 *            数据
	 * @param source
	 *            节点构造源
	 * @param node
	 *            父节点
	 */
	private static <ID, DATA> void generateSubNode(final TreeSource<ID, DATA> source, final DefaultTree<ID, DATA> node) {
		List<ID> items = source.listChildrenId(node.getId());
		if (items == null || items.isEmpty()) {
			return;
		}
		for (ID id : items) {
			DATA item = source.getItem(id);
			node.appendChildNode(id, item);
		}
		for (DefaultTree<ID, DATA> subNode : new ArrayList<>(node.getChildNodes().values())) {
			generateSubNode(source, subNode);
		}
	}
}
