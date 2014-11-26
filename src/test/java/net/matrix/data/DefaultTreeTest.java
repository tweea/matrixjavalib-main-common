/*
 * Copyright(C) 2011 matrix
 * All right reserved.
 */
package net.matrix.data;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.matrix.data.Tree.Key;

public class DefaultTreeTest {
	@Test
	public void testDefaultTreeIDDATA() {
		Tree<String, String> tree = new DefaultTree<>("root", "test");
		Assertions.assertThat(tree.getKey()).isEqualTo(new DefaultTree.DefaultKey());
		Assertions.assertThat(tree.getId()).isEqualTo("root");
		Assertions.assertThat(tree.getData()).isEqualTo("test");
	}

	@Test
	public void testDefaultTreeDefaultTreeOfIDDATAIDDATA() {
		DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
		DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
		DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test2");
		Tree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
		Tree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
		Tree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

		Assertions.assertThat(node1.getKey()).isEqualTo(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(), 0));
		Assertions.assertThat(node1.getId()).isEqualTo("node1");
		Assertions.assertThat(node1.getData()).isEqualTo("test1");
		Assertions.assertThat(node2.getKey()).isEqualTo(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(), 1));
		Assertions.assertThat(node2.getId()).isEqualTo("node2");
		Assertions.assertThat(node2.getData()).isEqualTo("test2");
		Assertions.assertThat(leaf1.getKey()).isEqualTo(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(), 0), 0));
		Assertions.assertThat(leaf1.getId()).isEqualTo("leaf1");
		Assertions.assertThat(leaf1.getData()).isEqualTo("value1");
		Assertions.assertThat(leaf2.getKey()).isEqualTo(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(), 1), 0));
		Assertions.assertThat(leaf2.getId()).isEqualTo("leaf2");
		Assertions.assertThat(leaf2.getData()).isEqualTo("value2");
		Assertions.assertThat(leaf3.getKey()).isEqualTo(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(), 1), 1));
		Assertions.assertThat(leaf3.getId()).isEqualTo("leaf3");
		Assertions.assertThat(leaf3.getData()).isEqualTo("value3");
	}

	@Test
	public void testFindKey() {
		DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
		DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
		DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test1");
		Tree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
		Tree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
		Tree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

		Assertions.assertThat(tree.findKey("root")).isEqualTo(tree.getKey());
		Assertions.assertThat(tree.findKey("node1")).isEqualTo(node1.getKey());
		Assertions.assertThat(tree.findKey("node2")).isEqualTo(node2.getKey());
		Assertions.assertThat(tree.findKey("leaf1")).isEqualTo(leaf1.getKey());
		Assertions.assertThat(tree.findKey("leaf2")).isEqualTo(leaf2.getKey());
		Assertions.assertThat(tree.findKey("leaf3")).isEqualTo(leaf3.getKey());
	}

	@Test
	public void testGetParent() {
		DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
		DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
		DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test1");
		Tree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
		Tree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
		Tree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

		Assertions.assertThat(tree.getParent()).isNull();
		Assertions.assertThat(node1.getParent()).isEqualTo(tree);
		Assertions.assertThat(node2.getParent()).isEqualTo(tree);
		Assertions.assertThat(leaf1.getParent()).isEqualTo(node1);
		Assertions.assertThat(leaf2.getParent()).isEqualTo(node2);
		Assertions.assertThat(leaf3.getParent()).isEqualTo(node2);
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
		Assertions.assertThat(allNodes).hasSize(6);
		Assertions.assertThat(allNodes).containsValue(tree);
		Assertions.assertThat(allNodes).containsValue(node1);
		Assertions.assertThat(allNodes).containsValue(node2);
		Assertions.assertThat(allNodes).containsValue(leaf1);
		Assertions.assertThat(allNodes).containsValue(leaf2);
		Assertions.assertThat(allNodes).containsValue(leaf3);
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
		Assertions.assertThat(childNodes).hasSize(2);
		Assertions.assertThat(childNodes).containsValue(node1);
		Assertions.assertThat(childNodes).containsValue(node2);

		childNodes = node1.getChildNodes();
		Assertions.assertThat(childNodes).hasSize(1);
		Assertions.assertThat(childNodes).containsValue(leaf1);

		childNodes = node2.getChildNodes();
		Assertions.assertThat(childNodes).hasSize(2);
		Assertions.assertThat(childNodes).containsValue(leaf2);
		Assertions.assertThat(childNodes).containsValue(leaf3);

		childNodes = leaf1.getChildNodes();
		Assertions.assertThat(childNodes).isEmpty();
	}

	@Test
	public void testGetNodeID() {
		DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
		DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
		DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test1");
		DefaultTree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
		DefaultTree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
		DefaultTree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

		Assertions.assertThat(tree.getNode("root")).isEqualTo(tree);
		Assertions.assertThat(tree.getNode("node1")).isEqualTo(node1);
		Assertions.assertThat(tree.getNode("node2")).isEqualTo(node2);
		Assertions.assertThat(tree.getNode("leaf1")).isEqualTo(leaf1);
		Assertions.assertThat(tree.getNode("leaf2")).isEqualTo(leaf2);
		Assertions.assertThat(tree.getNode("leaf3")).isEqualTo(leaf3);
	}

	@Test
	public void testGetChildNodeID() {
		DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
		DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
		DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test1");
		DefaultTree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
		DefaultTree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
		DefaultTree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

		Assertions.assertThat(tree.getChildNode("root")).isNull();
		Assertions.assertThat(tree.getChildNode("node1")).isEqualTo(node1);
		Assertions.assertThat(tree.getChildNode("node2")).isEqualTo(node2);
		Assertions.assertThat(tree.getChildNode("leaf1")).isNull();
		Assertions.assertThat(tree.getChildNode("leaf2")).isNull();

		Assertions.assertThat(node1.getChildNode("root")).isNull();
		Assertions.assertThat(node1.getChildNode("leaf2")).isNull();
		Assertions.assertThat(node1.getChildNode("leaf1")).isEqualTo(leaf1);

		Assertions.assertThat(node2.getChildNode("root")).isNull();
		Assertions.assertThat(node2.getChildNode("leaf1")).isNull();
		Assertions.assertThat(node2.getChildNode("leaf2")).isEqualTo(leaf2);
		Assertions.assertThat(node2.getChildNode("leaf3")).isEqualTo(leaf3);

		Assertions.assertThat(leaf1.getChildNode("root")).isNull();
		Assertions.assertThat(leaf1.getChildNode("node1")).isNull();
		Assertions.assertThat(leaf1.getChildNode("leaf2")).isNull();
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
		Assertions.assertThat(childNodes).hasSize(2);
		Assertions.assertThat(childNodes).containsValue(node1);
		Assertions.assertThat(childNodes).containsValue(node2);

		childNodes = node1.getChildNodes();
		Assertions.assertThat(childNodes).hasSize(1);
		Assertions.assertThat(childNodes).containsValue(leaf1);

		childNodes = node2.getChildNodes();
		Assertions.assertThat(childNodes).hasSize(2);
		Assertions.assertThat(childNodes).containsValue(leaf2);
		Assertions.assertThat(childNodes).containsValue(leaf3);

		childNodes = leaf1.getChildNodes();
		Assertions.assertThat(childNodes).isEmpty();
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
		Assertions.assertThat(tree.getAllNodes()).hasSize(6);
		Assertions.assertThat(childNodes).hasSize(1);
		Assertions.assertThat(childNodes).containsValue(leaf1);
		node1.removeChildNode("leaf1");
		Assertions.assertThat(tree.getAllNodes()).hasSize(5);
		Assertions.assertThat(childNodes).isEmpty();
		Assertions.assertThat(childNodes).doesNotContainValue(leaf1);

		childNodes = tree.getChildNodes();
		Assertions.assertThat(tree.getAllNodes()).hasSize(5);
		Assertions.assertThat(childNodes).hasSize(2);
		Assertions.assertThat(childNodes).containsValue(node2);
		tree.removeChildNode("node2");
		Assertions.assertThat(tree.getAllNodes()).hasSize(2);
		Assertions.assertThat(childNodes).hasSize(1);
		Assertions.assertThat(childNodes).doesNotContainValue(node2);
		Assertions.assertThat(tree.getAllNodes()).doesNotContainValue(leaf2);
		Assertions.assertThat(tree.getAllNodes()).doesNotContainValue(leaf3);
	}

	@Test
	public void testIsRoot() {
		DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
		DefaultTree<String, String> node1 = tree.appendChildNode("node1", "test1");
		DefaultTree<String, String> node2 = tree.appendChildNode("node2", "test2");
		DefaultTree<String, String> leaf1 = node1.appendChildNode("leaf1", "value1");
		DefaultTree<String, String> leaf2 = node2.appendChildNode("leaf2", "value3");
		DefaultTree<String, String> leaf3 = node2.appendChildNode("leaf3", "value3");

		Assertions.assertThat(tree.isRoot()).isTrue();
		Assertions.assertThat(node1.isRoot()).isFalse();
		Assertions.assertThat(node2.isRoot()).isFalse();
		Assertions.assertThat(leaf1.isRoot()).isFalse();
		Assertions.assertThat(leaf2.isRoot()).isFalse();
		Assertions.assertThat(leaf3.isRoot()).isFalse();
	}

	@Test
	public void testIsLeaf() {
		DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
		DefaultTree<String, String> node1 = tree.appendChildNode("node1", "test1");
		DefaultTree<String, String> node2 = tree.appendChildNode("node2", "test2");
		DefaultTree<String, String> leaf1 = node1.appendChildNode("leaf1", "value1");
		DefaultTree<String, String> leaf2 = node2.appendChildNode("leaf2", "value3");
		DefaultTree<String, String> leaf3 = node2.appendChildNode("leaf3", "value3");

		Assertions.assertThat(tree.isLeaf()).isFalse();
		Assertions.assertThat(node1.isLeaf()).isFalse();
		Assertions.assertThat(node2.isLeaf()).isFalse();
		Assertions.assertThat(leaf1.isLeaf()).isTrue();
		Assertions.assertThat(leaf2.isLeaf()).isTrue();
		Assertions.assertThat(leaf3.isLeaf()).isTrue();
	}

	private static class TestSource
		implements TreeSource<String, String> {
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
		Assertions.assertThat(allNodes).hasSize(6);
		Assertions.assertThat(allNodes).containsValue(tree);
		Assertions.assertThat(tree.getNode("node1").getId()).isEqualTo("node1");
		Assertions.assertThat(tree.getNode("node2").getId()).isEqualTo("node2");
		Assertions.assertThat(tree.getNode("leaf1").getId()).isEqualTo("leaf1");
		Assertions.assertThat(tree.getNode("leaf2").getId()).isEqualTo("leaf2");
		Assertions.assertThat(tree.getNode("leaf3").getId()).isEqualTo("leaf3");
	}
}
