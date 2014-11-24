/*
 * Copyright(C) 2011 matrix
 * All right reserved.
 */
package net.matrix.data;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import org.junit.Assert;
import org.junit.Test;

import net.matrix.data.Tree.Key;

public class DefaultTreeTest {
	@Test
	public void testDefaultTreeIDDATA() {
		Tree<String, String> tree = new DefaultTree<>("root", "test");
		Assert.assertEquals(new DefaultTree.DefaultKey(), tree.getKey());
		Assert.assertEquals("root", tree.getId());
		Assert.assertEquals("test", tree.getData());
	}

	@Test
	public void testDefaultTreeDefaultTreeOfIDDATAIDDATA() {
		DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
		DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
		DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test2");
		Tree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
		Tree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
		Tree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

		Assert.assertEquals(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(), 0), node1.getKey());
		Assert.assertEquals("node1", node1.getId());
		Assert.assertEquals("test1", node1.getData());
		Assert.assertEquals(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(), 1), node2.getKey());
		Assert.assertEquals("node2", node2.getId());
		Assert.assertEquals("test2", node2.getData());
		Assert.assertEquals(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(), 0), 0), leaf1.getKey());
		Assert.assertEquals("leaf1", leaf1.getId());
		Assert.assertEquals("value1", leaf1.getData());
		Assert.assertEquals(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(), 1), 0), leaf2.getKey());
		Assert.assertEquals("leaf2", leaf2.getId());
		Assert.assertEquals("value2", leaf2.getData());
		Assert.assertEquals(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(new DefaultTree.DefaultKey(), 1), 1), leaf3.getKey());
		Assert.assertEquals("leaf3", leaf3.getId());
		Assert.assertEquals("value3", leaf3.getData());
	}

	@Test
	public void testFindKey() {
		DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
		DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
		DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test1");
		Tree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
		Tree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
		Tree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

		Assert.assertEquals(tree.getKey(), tree.findKey("root"));
		Assert.assertEquals(node1.getKey(), tree.findKey("node1"));
		Assert.assertEquals(node2.getKey(), tree.findKey("node2"));
		Assert.assertEquals(leaf1.getKey(), tree.findKey("leaf1"));
		Assert.assertEquals(leaf2.getKey(), tree.findKey("leaf2"));
		Assert.assertEquals(leaf3.getKey(), tree.findKey("leaf3"));
	}

	@Test
	public void testGetParent() {
		DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
		DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
		DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test1");
		Tree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
		Tree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
		Tree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

		Assert.assertNull(tree.getParent());
		Assert.assertEquals(tree, node1.getParent());
		Assert.assertEquals(tree, node2.getParent());
		Assert.assertEquals(node1, leaf1.getParent());
		Assert.assertEquals(node2, leaf2.getParent());
		Assert.assertEquals(node2, leaf3.getParent());
	}

	@Test
	public void testGetAllNodes() {
		DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
		DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
		DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test1");
		Tree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
		Tree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
		Tree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

		SortedMap<Key, DefaultTree<String, String>> allNodes = tree.getAllNodes();
		Assert.assertEquals(6, allNodes.size());
		Assert.assertTrue(allNodes.containsValue(tree));
		Assert.assertTrue(allNodes.containsValue(node1));
		Assert.assertTrue(allNodes.containsValue(node2));
		Assert.assertTrue(allNodes.containsValue(leaf1));
		Assert.assertTrue(allNodes.containsValue(leaf2));
		Assert.assertTrue(allNodes.containsValue(leaf3));
	}

	@Test
	public void testGetChildNodes() {
		DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
		DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
		DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test1");
		Tree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
		Tree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
		Tree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

		SortedMap<Key, ? extends Tree<String, String>> childNodes = tree.getChildNodes();
		Assert.assertEquals(2, childNodes.size());
		Assert.assertTrue(childNodes.containsValue(node1));
		Assert.assertTrue(childNodes.containsValue(node2));

		childNodes = node1.getChildNodes();
		Assert.assertEquals(1, childNodes.size());
		Assert.assertTrue(childNodes.containsValue(leaf1));

		childNodes = node2.getChildNodes();
		Assert.assertEquals(2, childNodes.size());
		Assert.assertTrue(childNodes.containsValue(leaf2));
		Assert.assertTrue(childNodes.containsValue(leaf3));

		childNodes = leaf1.getChildNodes();
		Assert.assertEquals(0, childNodes.size());
	}

	@Test
	public void testGetNodeID() {
		DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
		DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
		DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test1");
		Tree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
		Tree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
		Tree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

		Assert.assertEquals(tree, tree.getNode("root"));
		Assert.assertEquals(node1, tree.getNode("node1"));
		Assert.assertEquals(node2, tree.getNode("node2"));
		Assert.assertEquals(leaf1, tree.getNode("leaf1"));
		Assert.assertEquals(leaf2, tree.getNode("leaf2"));
		Assert.assertEquals(leaf3, tree.getNode("leaf3"));
	}

	@Test
	public void testGetChildNodeID() {
		DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
		DefaultTree<String, String> node1 = new DefaultTree<>(tree, "node1", "test1");
		DefaultTree<String, String> node2 = new DefaultTree<>(tree, "node2", "test1");
		Tree<String, String> leaf1 = new DefaultTree<>(node1, "leaf1", "value1");
		Tree<String, String> leaf2 = new DefaultTree<>(node2, "leaf2", "value2");
		Tree<String, String> leaf3 = new DefaultTree<>(node2, "leaf3", "value3");

		Assert.assertNull(tree.getChildNode("root"));
		Assert.assertEquals(node1, tree.getChildNode("node1"));
		Assert.assertEquals(node2, tree.getChildNode("node2"));
		Assert.assertNull(tree.getChildNode("leaf1"));
		Assert.assertNull(tree.getChildNode("leaf2"));

		Assert.assertNull(node1.getChildNode("root"));
		Assert.assertNull(node1.getChildNode("leaf2"));
		Assert.assertEquals(leaf1, node1.getChildNode("leaf1"));

		Assert.assertNull(node2.getChildNode("root"));
		Assert.assertNull(node2.getChildNode("leaf1"));
		Assert.assertEquals(leaf2, node2.getChildNode("leaf2"));
		Assert.assertEquals(leaf3, node2.getChildNode("leaf3"));

		Assert.assertNull(leaf1.getChildNode("root"));
		Assert.assertNull(leaf1.getChildNode("node1"));
		Assert.assertNull(leaf1.getChildNode("leaf2"));
	}

	@Test
	public void testAppendChildNode() {
		DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
		DefaultTree<String, String> node1 = tree.appendChildNode("node1", "test1");
		DefaultTree<String, String> node2 = tree.appendChildNode("node2", "test2");
		DefaultTree<String, String> leaf1 = node1.appendChildNode("leaf1", "value1");
		DefaultTree<String, String> leaf2 = node2.appendChildNode("leaf2", "value3");
		DefaultTree<String, String> leaf3 = node2.appendChildNode("leaf3", "value3");

		SortedMap<Key, ? extends Tree<String, String>> childNodes = tree.getChildNodes();
		Assert.assertEquals(2, childNodes.size());
		Assert.assertTrue(childNodes.containsValue(node1));
		Assert.assertTrue(childNodes.containsValue(node2));

		childNodes = node1.getChildNodes();
		Assert.assertEquals(1, childNodes.size());
		Assert.assertTrue(childNodes.containsValue(leaf1));

		childNodes = node2.getChildNodes();
		Assert.assertEquals(2, childNodes.size());
		Assert.assertTrue(childNodes.containsValue(leaf2));
		Assert.assertTrue(childNodes.containsValue(leaf3));

		childNodes = leaf1.getChildNodes();
		Assert.assertEquals(0, childNodes.size());
	}

	@Test
	public void testRemoveChildNodeID() {
		DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
		DefaultTree<String, String> node1 = tree.appendChildNode("node1", "test1");
		DefaultTree<String, String> node2 = tree.appendChildNode("node2", "test2");
		DefaultTree<String, String> leaf1 = node1.appendChildNode("leaf1", "value1");
		DefaultTree<String, String> leaf2 = node2.appendChildNode("leaf2", "value3");
		DefaultTree<String, String> leaf3 = node2.appendChildNode("leaf3", "value3");

		SortedMap<Key, ? extends Tree<String, String>> childNodes = node1.getChildNodes();
		Assert.assertEquals(6, tree.getAllNodes().size());
		Assert.assertEquals(1, childNodes.size());
		Assert.assertTrue(childNodes.containsValue(leaf1));
		node1.removeChildNode("leaf1");
		Assert.assertEquals(5, tree.getAllNodes().size());
		Assert.assertEquals(0, childNodes.size());
		Assert.assertFalse(childNodes.containsValue(leaf1));

		childNodes = tree.getChildNodes();
		Assert.assertEquals(5, tree.getAllNodes().size());
		Assert.assertEquals(2, childNodes.size());
		Assert.assertTrue(childNodes.containsValue(node2));
		tree.removeChildNode("node2");
		Assert.assertEquals(2, tree.getAllNodes().size());
		Assert.assertEquals(1, childNodes.size());
		Assert.assertFalse(childNodes.containsValue(node2));
		Assert.assertFalse(tree.getAllNodes().containsValue(leaf2));
		Assert.assertFalse(tree.getAllNodes().containsValue(leaf3));
	}

	@Test
	public void testIsRoot() {
		DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
		DefaultTree<String, String> node1 = tree.appendChildNode("node1", "test1");
		DefaultTree<String, String> node2 = tree.appendChildNode("node2", "test2");
		DefaultTree<String, String> leaf1 = node1.appendChildNode("leaf1", "value1");
		DefaultTree<String, String> leaf2 = node2.appendChildNode("leaf2", "value3");
		DefaultTree<String, String> leaf3 = node2.appendChildNode("leaf3", "value3");

		Assert.assertTrue(tree.isRoot());
		Assert.assertFalse(node1.isRoot());
		Assert.assertFalse(node2.isRoot());
		Assert.assertFalse(leaf1.isRoot());
		Assert.assertFalse(leaf2.isRoot());
		Assert.assertFalse(leaf3.isRoot());
	}

	@Test
	public void testIsLeaf() {
		DefaultTree<String, String> tree = new DefaultTree<>("root", "test");
		DefaultTree<String, String> node1 = tree.appendChildNode("node1", "test1");
		DefaultTree<String, String> node2 = tree.appendChildNode("node2", "test2");
		DefaultTree<String, String> leaf1 = node1.appendChildNode("leaf1", "value1");
		DefaultTree<String, String> leaf2 = node2.appendChildNode("leaf2", "value3");
		DefaultTree<String, String> leaf3 = node2.appendChildNode("leaf3", "value3");

		Assert.assertFalse(tree.isLeaf());
		Assert.assertFalse(node1.isLeaf());
		Assert.assertFalse(node2.isLeaf());
		Assert.assertTrue(leaf1.isLeaf());
		Assert.assertTrue(leaf2.isLeaf());
		Assert.assertTrue(leaf3.isLeaf());
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
		Assert.assertEquals(6, allNodes.size());
		Assert.assertTrue(allNodes.containsValue(tree));
		Assert.assertEquals("node1", tree.getNode("node1").getId());
		Assert.assertEquals("node2", tree.getNode("node2").getId());
		Assert.assertEquals("leaf1", tree.getNode("leaf1").getId());
		Assert.assertEquals("leaf2", tree.getNode("leaf2").getId());
		Assert.assertEquals("leaf3", tree.getNode("leaf3").getId());
	}
}
