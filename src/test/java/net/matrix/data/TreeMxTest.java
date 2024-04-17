/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.data;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TreeMxTest {
    public static class TestData
        implements TreeData<String, TestData> {
        private String id;

        private String parentId;

        private List<TestData> children;

        public TestData(String id, String parentId) {
            this.id = id;
            this.parentId = parentId;
            this.children = new ArrayList<>();
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getParentId() {
            return parentId;
        }

        @Override
        public List<TestData> getChildren() {
            return children;
        }
    }

    @Test
    public void testBuildTree() {
        List<TestData> datas = new ArrayList<>();
        datas.add(new TestData("1", null));
        datas.add(new TestData("2", "1"));
        datas.add(new TestData("3", "1"));

        List<TestData> dataTrees = TreeMx.buildTree(datas);
        assertThat(dataTrees).hasSize(1);
        assertThat(dataTrees.get(0).getId()).isEqualTo("1");
        List<TestData> children = dataTrees.get(0).getChildren();
        assertThat(children).hasSize(2);
        assertThat(children.get(0).getId()).isEqualTo("2");
        assertThat(children.get(1).getId()).isEqualTo("3");
    }

    public static class TestSource
        implements TreeSource<TestData> {
        @Override
        public List<TestData> getRoots() {
            List<TestData> roots = new ArrayList<>();
            roots.add(new TestData("1", null));
            return roots;
        }

        @Override
        public List<TestData> getChildren(TestData data) {
            List<TestData> children = new ArrayList<>();
            if ("1".equals(data.getId())) {
                children.add(new TestData("2", "1"));
                children.add(new TestData("3", "1"));
            }
            return children;
        }
    }

    @Test
    public void testGenerateTree() {
        List<TestData> dataTrees = TreeMx.generateTree(new TestSource());
        assertThat(dataTrees).hasSize(1);
        assertThat(dataTrees.get(0).getId()).isEqualTo("1");
        List<TestData> children = dataTrees.get(0).getChildren();
        assertThat(children).hasSize(2);
        assertThat(children.get(0).getId()).isEqualTo("2");
        assertThat(children.get(1).getId()).isEqualTo("3");
    }
}
