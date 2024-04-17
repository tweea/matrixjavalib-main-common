/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 树型结构中的节点的默认实现。
 */
public class DefaultTreeNode<DATA>
    implements Serializable, TreeNode<DATA> {
    private static final long serialVersionUID = 1L;

    /**
     * 节点承载的数据。
     */
    private DATA data;

    /**
     * 上级节点。
     */
    private TreeNode<DATA> parent;

    /**
     * 下级节点列表。
     */
    private final List<TreeNode<DATA>> children;

    /**
     * 构造器，没有数据和上级节点。
     */
    public DefaultTreeNode() {
        this(null, null);
    }

    /**
     * 构造器，初始化数据，没有上级节点。
     * 
     * @param data
     *     数据。
     */
    public DefaultTreeNode(DATA data) {
        this(data, null);
    }

    /**
     * 构造器，没有数据，加入上级节点。
     * 
     * @param parent
     *     上级节点。
     */
    public DefaultTreeNode(TreeNode<DATA> parent) {
        this(null, parent);
    }

    /**
     * 构造器，初始化数据，加入上级节点。
     * 
     * @param data
     *     数据。
     * @param parent
     *     上级节点。
     */
    public DefaultTreeNode(DATA data, TreeNode<DATA> parent) {
        this.data = data;
        this.parent = parent;
        this.children = new ArrayList<>();
        if (parent != null) {
            parent.getChildren().add(this);
        }
    }

    @Override
    public DATA getData() {
        return data;
    }

    @Override
    public void setData(DATA data) {
        this.data = data;
    }

    @Override
    public TreeNode<DATA> getParent() {
        return parent;
    }

    @Override
    public void setParent(TreeNode<DATA> parent) {
        if (this.parent == parent) {
            return;
        }
        if (this.parent != null) {
            this.parent.getChildren().remove(this);
        }
        this.parent = parent;
        if (parent != null) {
            parent.getChildren().add(this);
        }
    }

    @Override
    public List<TreeNode<DATA>> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "DefaultTreeNode [data=" + data + ", parent=" + parent + ", children=" + children + "]";
    }

    /**
     * 从数据源生成树型结构。
     * 
     * @param source
     *     数据源。
     * @return 生成的树型结构。
     */
    public static <DATA> List<TreeNode<DATA>> generate(TreeSource<DATA> source) {
        List<DATA> datas = source.getRoots();
        List<TreeNode<DATA>> treeNodes = new ArrayList<>(datas.size());
        for (DATA data : datas) {
            DefaultTreeNode<DATA> treeNode = new DefaultTreeNode<>(data);
            generateChildNode(source, treeNode);
            treeNodes.add(treeNode);
        }
        return treeNodes;
    }

    /**
     * 从数据源生成树型结构的下级节点。
     * 
     * @param source
     *     数据源。
     * @param treeNode
     *     节点。
     */
    private static <DATA> void generateChildNode(TreeSource<DATA> source, DefaultTreeNode<DATA> treeNode) {
        List<DATA> childDatas = source.getChildren(treeNode.data);
        for (DATA childData : childDatas) {
            DefaultTreeNode<DATA> childTreeNode = new DefaultTreeNode<>(childData, treeNode);
            generateChildNode(source, childTreeNode);
        }
    }
}
