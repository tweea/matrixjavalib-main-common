/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.data;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultTreeNodeTest {
    @Test
    void testNew_data() {
        TreeNode<String> treeNode = new DefaultTreeNode<>("test");
        assertThat(treeNode.getData()).isEqualTo("test");
        assertThat(treeNode.getParent()).isNull();
        assertThat(treeNode.getChildren()).isEmpty();
    }

    @Test
    void testNew_data_parent() {
        TreeNode<String> parentTreeNode = new DefaultTreeNode<>("parent");

        TreeNode<String> treeNode = new DefaultTreeNode<>("test", parentTreeNode);
        assertThat(treeNode.getData()).isEqualTo("test");
        assertThat(treeNode.getParent()).isSameAs(parentTreeNode);
        assertThat(treeNode.getChildren()).isEmpty();
        assertThat(parentTreeNode.getChildren()).containsOnly(treeNode);
    }

    @Test
    void testSetParent() {
        TreeNode<String> parentTreeNode1 = new DefaultTreeNode<>("parent1");
        TreeNode<String> parentTreeNode2 = new DefaultTreeNode<>("parent2");
        TreeNode<String> treeNode = new DefaultTreeNode<>("test");

        treeNode.setParent(parentTreeNode1);
        assertThat(parentTreeNode1.getChildren()).containsOnly(treeNode);
        assertThat(treeNode.getParent()).isSameAs(parentTreeNode1);

        treeNode.setParent(parentTreeNode2);
        assertThat(parentTreeNode1.getChildren()).isEmpty();
        assertThat(parentTreeNode2.getChildren()).containsOnly(treeNode);
        assertThat(treeNode.getParent()).isSameAs(parentTreeNode2);

        treeNode.setParent(null);
        assertThat(parentTreeNode1.getChildren()).isEmpty();
        assertThat(parentTreeNode2.getChildren()).isEmpty();
        assertThat(treeNode.getParent()).isNull();
    }

    static class TestSource
        implements TreeSource<String> {
        @Override
        public List<String> getRoots() {
            List<String> ids = new ArrayList<>();
            ids.add("root");
            return ids;
        }

        @Override
        public List<String> getChildren(String data) {
            List<String> ids = new ArrayList<>();
            if ("root".equals(data)) {
                ids.add("node1");
                ids.add("node2");
            } else if ("node1".equals(data)) {
                ids.add("leaf1");
            } else if ("node2".equals(data)) {
                ids.add("leaf2");
                ids.add("leaf3");
            }
            return ids;
        }
    }

    @Test
    void testGenerate() {
        List<TreeNode<String>> treeNodes = DefaultTreeNode.generate(new TestSource());
        assertThat(treeNodes).hasSize(1);
        assertThat(treeNodes.get(0).getData()).isEqualTo("root");
        assertThat(treeNodes.get(0).getParent()).isNull();
        List<TreeNode<String>> childTreeNodes = treeNodes.get(0).getChildren();
        assertThat(childTreeNodes).hasSize(2);
        assertThat(childTreeNodes.get(0).getData()).isEqualTo("node1");
        assertThat(childTreeNodes.get(0).getParent()).isSameAs(treeNodes.get(0));
        assertThat(childTreeNodes.get(0).getChildren()).hasSize(1);
    }
}
