/*
 * Copyright(C) 2011 matrix
 * All right reserved.
 */
package net.matrix.data;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import org.junit.jupiter.api.Test;

import net.matrix.data.Tree.Key;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultTreeTest {
    @Test
    public void testDefaultTreeIDDATA() {
        Tree<String, String> tree = new DefaultTree<>("root", "test");
        assertThat(tree.getKey()).isEqualTo(new DefaultTree.DefaultKey());
        assertThat(tree.getId()).isEqualTo("root");
        assertThat(tree.getData()).isEqualTo("test");
    }

    @Test
    public void testDefaultTreeDefaultTreeOfIDDATAIDDATA() {
        DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
        DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
        DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test2");
        Tree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
        Tree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
        Tree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

        assertThat(node1.getKey()).isEqualTo(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(), 0));
        assertThat(node1.getId()).isEqualTo("node1");
        assertThat(node1.getData()).isEqualTo("test1");
        assertThat(node2.getKey()).isEqualTo(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(), 1));
        assertThat(node2.getId()).isEqualTo("node2");
        assertThat(node2.getData()).isEqualTo("test2");
        assertThat(leaf1.getKey()).isEqualTo(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(), 0), 0));
        assertThat(leaf1.getId()).isEqualTo("leaf1");
        assertThat(leaf1.getData()).isEqualTo("value1");
        assertThat(leaf2.getKey()).isEqualTo(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(), 1), 0));
        assertThat(leaf2.getId()).isEqualTo("leaf2");
        assertThat(leaf2.getData()).isEqualTo("value2");
        assertThat(leaf3.getKey()).isEqualTo(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(), 1), 1));
        assertThat(leaf3.getId()).isEqualTo("leaf3");
        assertThat(leaf3.getData()).isEqualTo("value3");
    }

    @Test
    public void testFindKey() {
        DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
        DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
        DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test1");
        Tree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
        Tree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
        Tree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

        assertThat(tree.findKey("root")).isEqualTo(tree.getKey());
        assertThat(tree.findKey("node1")).isEqualTo(node1.getKey());
        assertThat(tree.findKey("node2")).isEqualTo(node2.getKey());
        assertThat(tree.findKey("leaf1")).isEqualTo(leaf1.getKey());
        assertThat(tree.findKey("leaf2")).isEqualTo(leaf2.getKey());
        assertThat(tree.findKey("leaf3")).isEqualTo(leaf3.getKey());
    }

    @Test
    public void testGetParent() {
        DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
        DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
        DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test1");
        Tree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
        Tree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
        Tree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

        assertThat(tree.getParent()).isNull();
        assertThat(node1.getParent()).isEqualTo(tree);
        assertThat(node2.getParent()).isEqualTo(tree);
        assertThat(leaf1.getParent()).isEqualTo(node1);
        assertThat(leaf2.getParent()).isEqualTo(node2);
        assertThat(leaf3.getParent()).isEqualTo(node2);
    }

    @Test
    public void testGetAllNodes() {
        DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
        DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
        DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test1");
        DefaultTree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
        DefaultTree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
        DefaultTree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

        SortedMap<Key, DefaultTree<String, String>> allNodes = tree.getAllNodes();
        assertThat(allNodes).hasSize(6);
        assertThat(allNodes).containsValue(tree);
        assertThat(allNodes).containsValue(node1);
        assertThat(allNodes).containsValue(node2);
        assertThat(allNodes).containsValue(leaf1);
        assertThat(allNodes).containsValue(leaf2);
        assertThat(allNodes).containsValue(leaf3);
    }

    @Test
    public void testGetChildNodes() {
        DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
        DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
        DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test1");
        DefaultTree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
        DefaultTree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
        DefaultTree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

        SortedMap<Key, DefaultTree<String, String>> childNodes = tree.getChildNodes();
        assertThat(childNodes).hasSize(2);
        assertThat(childNodes).containsValue(node1);
        assertThat(childNodes).containsValue(node2);

        childNodes = node1.getChildNodes();
        assertThat(childNodes).hasSize(1);
        assertThat(childNodes).containsValue(leaf1);

        childNodes = node2.getChildNodes();
        assertThat(childNodes).hasSize(2);
        assertThat(childNodes).containsValue(leaf2);
        assertThat(childNodes).containsValue(leaf3);

        childNodes = leaf1.getChildNodes();
        assertThat(childNodes).isEmpty();
    }

    @Test
    public void testGetNodeID() {
        DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
        DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
        DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test1");
        DefaultTree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
        DefaultTree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
        DefaultTree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

        assertThat(tree.getNode("root")).isEqualTo(tree);
        assertThat(tree.getNode("node1")).isEqualTo(node1);
        assertThat(tree.getNode("node2")).isEqualTo(node2);
        assertThat(tree.getNode("leaf1")).isEqualTo(leaf1);
        assertThat(tree.getNode("leaf2")).isEqualTo(leaf2);
        assertThat(tree.getNode("leaf3")).isEqualTo(leaf3);
    }

    @Test
    public void testGetChildNodeID() {
        DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
        DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
        DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test1");
        DefaultTree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
        DefaultTree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
        DefaultTree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

        assertThat(tree.getChildNode("root")).isNull();
        assertThat(tree.getChildNode("node1")).isEqualTo(node1);
        assertThat(tree.getChildNode("node2")).isEqualTo(node2);
        assertThat(tree.getChildNode("leaf1")).isNull();
        assertThat(tree.getChildNode("leaf2")).isNull();

        assertThat(node1.getChildNode("root")).isNull();
        assertThat(node1.getChildNode("leaf2")).isNull();
        assertThat(node1.getChildNode("leaf1")).isEqualTo(leaf1);

        assertThat(node2.getChildNode("root")).isNull();
        assertThat(node2.getChildNode("leaf1")).isNull();
        assertThat(node2.getChildNode("leaf2")).isEqualTo(leaf2);
        assertThat(node2.getChildNode("leaf3")).isEqualTo(leaf3);

        assertThat(leaf1.getChildNode("root")).isNull();
        assertThat(leaf1.getChildNode("node1")).isNull();
        assertThat(leaf1.getChildNode("leaf2")).isNull();
    }

    @Test
    public void testAppendChildNode() {
        DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
        DefaultTree<String, String> node1 = tree.appendChildNode("node1", "test1");
        DefaultTree<String, String> node2 = tree.appendChildNode("node2", "test2");
        DefaultTree<String, String> leaf1 = node1.appendChildNode("leaf1", "value1");
        DefaultTree<String, String> leaf2 = node2.appendChildNode("leaf2", "value3");
        DefaultTree<String, String> leaf3 = node2.appendChildNode("leaf3", "value3");

        SortedMap<Key, DefaultTree<String, String>> childNodes = tree.getChildNodes();
        assertThat(childNodes).hasSize(2);
        assertThat(childNodes).containsValue(node1);
        assertThat(childNodes).containsValue(node2);

        childNodes = node1.getChildNodes();
        assertThat(childNodes).hasSize(1);
        assertThat(childNodes).containsValue(leaf1);

        childNodes = node2.getChildNodes();
        assertThat(childNodes).hasSize(2);
        assertThat(childNodes).containsValue(leaf2);
        assertThat(childNodes).containsValue(leaf3);

        childNodes = leaf1.getChildNodes();
        assertThat(childNodes).isEmpty();
    }

    @Test
    public void testRemoveChildNodeID() {
        DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
        DefaultTree<String, String> node1 = tree.appendChildNode("node1", "test1");
        DefaultTree<String, String> node2 = tree.appendChildNode("node2", "test2");
        DefaultTree<String, String> leaf1 = node1.appendChildNode("leaf1", "value1");
        DefaultTree<String, String> leaf2 = node2.appendChildNode("leaf2", "value3");
        DefaultTree<String, String> leaf3 = node2.appendChildNode("leaf3", "value3");

        SortedMap<Key, DefaultTree<String, String>> childNodes = node1.getChildNodes();
        assertThat(tree.getAllNodes()).hasSize(6);
        assertThat(childNodes).hasSize(1);
        assertThat(childNodes).containsValue(leaf1);
        node1.removeChildNode("leaf1");
        assertThat(tree.getAllNodes()).hasSize(5);
        assertThat(childNodes).isEmpty();
        assertThat(childNodes).doesNotContainValue(leaf1);

        childNodes = tree.getChildNodes();
        assertThat(tree.getAllNodes()).hasSize(5);
        assertThat(childNodes).hasSize(2);
        assertThat(childNodes).containsValue(node2);
        tree.removeChildNode("node2");
        assertThat(tree.getAllNodes()).hasSize(2);
        assertThat(childNodes).hasSize(1);
        assertThat(childNodes).doesNotContainValue(node2);
        assertThat(tree.getAllNodes()).doesNotContainValue(leaf2);
        assertThat(tree.getAllNodes()).doesNotContainValue(leaf3);
    }

    @Test
    public void testIsRoot() {
        DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
        DefaultTree<String, String> node1 = tree.appendChildNode("node1", "test1");
        DefaultTree<String, String> node2 = tree.appendChildNode("node2", "test2");
        DefaultTree<String, String> leaf1 = node1.appendChildNode("leaf1", "value1");
        DefaultTree<String, String> leaf2 = node2.appendChildNode("leaf2", "value3");
        DefaultTree<String, String> leaf3 = node2.appendChildNode("leaf3", "value3");

        assertThat(tree.isRoot()).isTrue();
        assertThat(node1.isRoot()).isFalse();
        assertThat(node2.isRoot()).isFalse();
        assertThat(leaf1.isRoot()).isFalse();
        assertThat(leaf2.isRoot()).isFalse();
        assertThat(leaf3.isRoot()).isFalse();
    }

    @Test
    public void testIsLeaf() {
        DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
        DefaultTree<String, String> node1 = tree.appendChildNode("node1", "test1");
        DefaultTree<String, String> node2 = tree.appendChildNode("node2", "test2");
        DefaultTree<String, String> leaf1 = node1.appendChildNode("leaf1", "value1");
        DefaultTree<String, String> leaf2 = node2.appendChildNode("leaf2", "value3");
        DefaultTree<String, String> leaf3 = node2.appendChildNode("leaf3", "value3");

        assertThat(tree.isLeaf()).isFalse();
        assertThat(node1.isLeaf()).isFalse();
        assertThat(node2.isLeaf()).isFalse();
        assertThat(leaf1.isLeaf()).isTrue();
        assertThat(leaf2.isLeaf()).isTrue();
        assertThat(leaf3.isLeaf()).isTrue();
    }

    private static class TestSource
        implements TreeSource<String, String> {
        public TestSource() {
            // 提升可见性，优化访问速度
        }

        @Override
        public String getRootId() {
            return "root";
        }

        @Override
        public List<String> listChildrenId(String parentId) {
            List<String> ids = new ArrayList<>();
            if ("root".equals(parentId)) {
                ids.add("node1");
                ids.add("node2");
            } else if ("node1".equals(parentId)) {
                ids.add("leaf1");
            } else if ("node2".equals(parentId)) {
                ids.add("leaf2");
                ids.add("leaf3");
            }
            return ids;
        }

        @Override
        public String getItem(String id) {
            return id;
        }
    }

    @Test
    public void testGenerate() {
        DefaultTree<String, String> tree = DefaultTree.generate(new TestSource());

        SortedMap<Key, DefaultTree<String, String>> allNodes = tree.getAllNodes();
        assertThat(allNodes).hasSize(6);
        assertThat(allNodes).containsValue(tree);
        assertThat(tree.getNode("node1").getId()).isEqualTo("node1");
        assertThat(tree.getNode("node2").getId()).isEqualTo("node2");
        assertThat(tree.getNode("leaf1").getId()).isEqualTo("leaf1");
        assertThat(tree.getNode("leaf2").getId()).isEqualTo("leaf2");
        assertThat(tree.getNode("leaf3").getId()).isEqualTo("leaf3");
    }
}
