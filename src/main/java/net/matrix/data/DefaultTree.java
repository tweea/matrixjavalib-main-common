/*
 * 版权所有 2020 Matrix。
 * 保留所有权利。
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
 *     数据标识
 * @param <DATA>
 *     数据
 */
public class DefaultTree<ID, DATA>
    implements Serializable, Tree<ID, DATA> {
    private static final long serialVersionUID = 6200293084826291564L;

    /**
     * 节点标识。
     */
    private final DefaultKey key;

    /**
     * 数据标识。
     */
    private ID id;

    /**
     * 数据。
     */
    private DATA data;

    /**
     * 上级节点。
     */
    private final DefaultTree<ID, DATA> parent;

    /**
     * 所有数据标识到节点标识的映射。
     */
    private final Map<ID, Key> keyMap;

    /**
     * 所有节点。
     */
    private final SortedMap<Key, DefaultTree<ID, DATA>> nodes;

    /**
     * 构造根节点实例。
     * 
     * @param id
     *     数据标识
     * @param data
     *     数据
     */
    public DefaultTree(final ID id, final DATA data) {
        this.key = new DefaultKey();
        this.id = id;
        this.data = data;
        this.parent = null;

        this.keyMap = Collections.synchronizedMap(new HashMap<ID, Key>());
        this.nodes = Collections.synchronizedSortedMap(new TreeMap<Key, DefaultTree<ID, DATA>>());

        this.keyMap.put(id, key);
        this.nodes.put(key, this);
    }

    /**
     * 构造节点实例。
     * 
     * @param parent
     *     上级节点
     * @param id
     *     数据标识
     * @param data
     *     数据
     */
    public DefaultTree(final DefaultTree<ID, DATA> parent, final ID id, final DATA data) {
        this.key = new DefaultKey(parent.key, parent.getChildNodes().size());
        this.id = id;
        this.data = data;
        this.parent = parent;

        this.keyMap = parent.keyMap;
        this.nodes = parent.nodes;

        this.keyMap.put(id, key);
        this.nodes.put(key, this);
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
    public Key findKey(final ID theId) {
        return keyMap.get(theId);
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
    public DefaultTree<ID, DATA> getNode(final Key theKey) {
        return nodes.get(theKey);
    }

    @Override
    public DefaultTree<ID, DATA> getNode(final ID theId) {
        Key theKey = findKey(theId);
        if (theKey == null) {
            return null;
        }
        return getNode(theKey);
    }

    @Override
    public DefaultTree<ID, DATA> getChildNode(final Key theKey) {
        return getChildNodes().get(theKey);
    }

    @Override
    public DefaultTree<ID, DATA> getChildNode(final ID theId) {
        Key theKey = findKey(theId);
        if (theKey == null) {
            return null;
        }
        return getChildNode(theKey);
    }

    @Override
    public DefaultTree<ID, DATA> appendChildNode(final ID newId, final DATA newData) {
        return new DefaultTree<>(this, newId, newData);
    }

    @Override
    public void removeChildNode(final Key theKey) {
        DefaultTree<ID, DATA> node = getChildNode(theKey);
        if (node == null) {
            return;
        }
        for (Key childKey : new HashSet<>(node.getChildNodes().keySet())) {
            node.removeChildNode(childKey);
        }
        keyMap.remove(node.id);
        nodes.remove(theKey);
    }

    @Override
    public void removeChildNode(final ID theId) {
        Key theKey = findKey(theId);
        if (theKey == null) {
            return;
        }
        removeChildNode(theKey);
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
        sb.append(",data=").append(data).append(",nodes=").append(getChildNodes().values()).append(']');
        return sb.toString();
    }

    /**
     * 节点标识的默认实现。
     */
    public static class DefaultKey
        implements Key {
        private static final long serialVersionUID = 5986109247641634972L;

        /**
         * 上级节点标识。
         */
        private final DefaultKey parent;

        /**
         * 级别。
         */
        private final int level;

        /**
         * 索引。
         */
        private final int index;

        /**
         * 缓存 {@link #hashCode()} 结果。
         */
        private int hash;

        /**
         * 缓存 {@link #toString()} 结果。
         */
        private String string;

        /**
         * 构造根节点实例。
         */
        public DefaultKey() {
            this.parent = null;
            this.level = 0;
            this.index = 0;
        }

        /**
         * 构造节点实例。
         * 
         * @param parent
         *     上级节点标识
         * @param index
         *     索引
         */
        public DefaultKey(final DefaultKey parent, final int index) {
            this.parent = parent;
            this.level = parent.level + 1;
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
            if (obj == null) {
                return false;
            }
            if (this.getClass() == obj.getClass()) {
                DefaultKey ok = (DefaultKey) obj;
                if (level != ok.level) {
                    return false;
                }
                if (level != 0 && !parent.equals(ok.parent)) {
                    return false;
                }
                return index == ok.index;
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
                    string = parent.toString() + ',' + index;
                }
            }
            return string;
        }
    }

    /**
     * 从树型结构数据源生成树型结构。
     * 
     * @param <ID>
     *     数据标识
     * @param <DATA>
     *     数据
     * @param source
     *     树型结构数据源
     * @return 生成的树型结构
     */
    public static <ID, DATA> DefaultTree<ID, DATA> generate(final TreeSource<ID, DATA> source) {
        ID rootId = source.getRootId();
        DATA rootData = source.getItem(rootId);
        DefaultTree<ID, DATA> tree = new DefaultTree<>(rootId, rootData);
        generateChildNode(source, tree);
        return tree;
    }

    /**
     * 从树型结构数据源生成已有节点下级树型结构。
     * 
     * @param <ID>
     *     数据标识
     * @param <DATA>
     *     数据
     * @param source
     *     树型结构数据源
     * @param node
     *     已有节点
     */
    private static <ID, DATA> void generateChildNode(final TreeSource<ID, DATA> source, final DefaultTree<ID, DATA> node) {
        List<ID> childIds = source.listChildrenId(node.getId());
        if (childIds.isEmpty()) {
            return;
        }
        for (ID childId : childIds) {
            DATA childData = source.getItem(childId);
            node.appendChildNode(childId, childData);
        }
        for (DefaultTree<ID, DATA> childNode : new ArrayList<>(node.getChildNodes().values())) {
            generateChildNode(source, childNode);
        }
    }
}
