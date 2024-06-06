/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class CollectionMxTest {
    @Test
    void testBuildList() {
        List<TestBean3> items = new ArrayList<>();
        items.add(new TestBean3("a", "x"));
        items.add(new TestBean3("b", "y"));

        List<String> list = CollectionMx.buildList(items, TestBean3::getX);
        assertThat(list).hasSize(2);
        assertThat(list).containsExactly("a", "b");
    }

    @Test
    void testBuildSet() {
        List<TestBean3> items = new ArrayList<>();
        items.add(new TestBean3("a", "x"));
        items.add(new TestBean3("b", "y"));

        Set<String> set = CollectionMx.buildSet(items, TestBean3::getX);
        assertThat(set).hasSize(2);
        assertThat(set).containsExactly("a", "b");
    }

    @Test
    void testBuildMap() {
        List<TestBean3> items = new ArrayList<>();
        items.add(new TestBean3("a", "x"));
        items.add(new TestBean3("b", "y"));

        Map<String, TestBean3> map = CollectionMx.buildMap(items, TestBean3::getX);
        assertThat(map).hasSize(2);
        assertThat(map).containsOnly(entry("a", items.get(0)), entry("b", items.get(1)));
    }

    @Test
    void testBuildMap_value() {
        List<TestBean3> items = new ArrayList<>();
        items.add(new TestBean3("a", "x"));
        items.add(new TestBean3("b", "y"));

        Map<String, String> map = CollectionMx.buildMap(items, TestBean3::getX, TestBean3::getY);
        assertThat(map).hasSize(2);
        assertThat(map).containsOnly(entry("a", "x"), entry("b", "y"));
    }

    @Test
    void testBuildListMap() {
        List<TestBean3> items = new ArrayList<>();
        items.add(new TestBean3("a", "x"));
        items.add(new TestBean3("a", "y"));

        Map<String, List<TestBean3>> map = CollectionMx.buildListMap(items, TestBean3::getX);
        assertThat(map).hasSize(1);
        assertThat(map).containsOnlyKeys("a");
        assertThat(map.get("a")).hasSameElementsAs(items);
    }

    @Test
    void testBuildListMap_value() {
        List<TestBean3> items = new ArrayList<>();
        items.add(new TestBean3("a", "x"));
        items.add(new TestBean3("a", "y"));

        Map<String, List<String>> map = CollectionMx.buildListMap(items, TestBean3::getX, TestBean3::getY);
        assertThat(map).hasSize(1);
        assertThat(map).containsOnlyKeys("a");
        assertThat(map.get("a")).containsOnly("x", "y");
    }

    @Test
    void testBuildSetMap() {
        List<TestBean3> items = new ArrayList<>();
        items.add(new TestBean3("a", "x"));
        items.add(new TestBean3("a", "y"));

        Map<String, Set<TestBean3>> map = CollectionMx.buildSetMap(items, TestBean3::getX);
        assertThat(map).hasSize(1);
        assertThat(map).containsOnlyKeys("a");
        assertThat(map.get("a")).hasSameElementsAs(items);
    }

    @Test
    void testBuildSetMap_value() {
        List<TestBean3> items = new ArrayList<>();
        items.add(new TestBean3("a", "x"));
        items.add(new TestBean3("a", "y"));

        Map<String, Set<String>> map = CollectionMx.buildSetMap(items, TestBean3::getX, TestBean3::getY);
        assertThat(map).hasSize(1);
        assertThat(map).containsOnlyKeys("a");
        assertThat(map.get("a")).containsOnly("x", "y");
    }

    static class TestBean3 {
        private String x;

        private String y;

        public TestBean3(String x, String y) {
            this.x = x;
            this.y = y;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TestBean3)) {
                return false;
            }
            TestBean3 other = (TestBean3) obj;
            return Objects.equals(x, other.x) && Objects.equals(y, other.y);
        }
    }
}
